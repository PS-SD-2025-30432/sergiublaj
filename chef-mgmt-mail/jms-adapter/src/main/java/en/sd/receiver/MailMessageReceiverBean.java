package en.sd.receiver;

import com.fasterxml.jackson.databind.ObjectMapper;
import en.sd.model.mail.MailRequest;
import en.sd.model.mail.MailRequestDTO;
import en.sd.model.mapper.MailRequestDTOMapper;
import en.sd.service.mail.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MailMessageReceiverBean implements MessageReceiver {

    private final MailService mailService;
    private final MailRequestDTOMapper mailRequestDTOMapper;
    private final ObjectMapper objectMapper;

    @Override
    @JmsListener(destination = "${queues.mail-request-queue}")
    public void receiveMessage(String message) {
        log.info("Message received: {}", message);

        try {
            MailRequestDTO mailRequestDTO = objectMapper.readValue(message, MailRequestDTO.class);
            MailRequest mailRequest = mailRequestDTOMapper.convertDtoToCore(mailRequestDTO);

            mailService.sendMail(mailRequest);
        } catch (Exception e) {
            log.error("Message error: {}", e.getMessage());
        }
    }
}