package en.sd.chefmgmt.controller.mail;

import en.sd.chefmgmt.model.dto.mail.MailRequestDTO;
import en.sd.chefmgmt.model.dto.mail.MailResponseDTO;
import en.sd.chefmgmt.security.service.auth.AuthService;
import en.sd.chefmgmt.service.mail.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MailControllerBean implements MailController {

    private final AuthService authService;
    private final MailService syncMailService;
    private final MailService asyncMailService;

    @Override
    public MailResponseDTO sendSyncMail(MailRequestDTO mailRequestDTO) {
        MailRequestDTO mailRequest = mailRequestDTO.withFrom(authService.getLoggedUser());
        log.info("Sync mail request from {} to {}", mailRequest.from(), mailRequest.to());

        return syncMailService.sendMail(mailRequest);
    }

    @Override
    public MailResponseDTO sendAsyncMail(MailRequestDTO mailRequestDTO) {
        MailRequestDTO mailRequest = mailRequestDTO.withFrom(authService.getLoggedUser());
        log.info("Async mail request from {} to {}", mailRequest.from(), mailRequest.to());

        return asyncMailService.sendMail(mailRequest);
    }
}

