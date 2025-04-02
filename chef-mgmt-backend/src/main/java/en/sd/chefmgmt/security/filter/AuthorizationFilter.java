package en.sd.chefmgmt.security.filter;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import javax.crypto.SecretKey;

import com.fasterxml.jackson.databind.ObjectMapper;
import en.sd.chefmgmt.exception.model.ExceptionBody;
import en.sd.chefmgmt.exception.model.ExceptionCode;
import en.sd.chefmgmt.security.util.SecurityProperties;
import en.sd.chefmgmt.security.util.SecurityUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {

    private final SecurityProperties securityProperties;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException, SignatureException {
        Optional<String> jwtToken = SecurityUtil.getTokenFromRequest(request);
        if (jwtToken.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            Claims claims = (Claims) Jwts.parser()
                    .verifyWith((SecretKey) SecurityUtil.getSigningKey(securityProperties.getSecretKey()))
                    .build()
                    .parse(jwtToken.get())
                    .getPayload();
            String email = claims.getSubject();
            Collection<SimpleGrantedAuthority> authorities = Arrays.stream(
                            ((String) claims.get(SecurityUtil.ROLE_CLAIM)).split(SecurityUtil.ROLE_DELIMITER)
                    )
                    .map(SimpleGrantedAuthority::new)
                    .toList();
            Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            filterChain.doFilter(request, response);
//            this.onUnsuccessfulAuthorization(response, exception.getMessage());
        }
    }

    private void onUnsuccessfulAuthorization(HttpServletResponse response, String message) {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.FORBIDDEN.value());

        try {
            objectMapper.writeValue(response.getWriter(), getExceptionBody(message));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private ExceptionBody getExceptionBody(String message) {
        return ExceptionBody.builder()
                .timestamp(ZonedDateTime.now())
                .message(message)
                .code(ExceptionCode.FORBIDDEN_ACCESS.getCode())
                .build();
    }
}