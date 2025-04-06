package en.sd.chefmgmt.controller.user;

import en.sd.chefmgmt.model.dto.user.UserResponseDTO;
import en.sd.chefmgmt.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserControllerBean implements UserController {

    private final UserService userService;

    @Override
    public UserResponseDTO getUserInfo() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        log.info("[USER] Getting user info for {}", email);

        return userService.getUserInfo(email);
    }
}
