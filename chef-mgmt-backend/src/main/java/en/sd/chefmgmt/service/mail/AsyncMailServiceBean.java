package en.sd.chefmgmt.service.mail;

import com.fasterxml.jackson.databind.ObjectMapper;
import en.sd.chefmgmt.jms.JmsMessageSenderBase;
import en.sd.chefmgmt.model.dto.mail.MailRequestDTO;
import en.sd.chefmgmt.model.dto.mail.MailResponseDTO;
import en.sd.chefmgmt.model.dto.mail.SendingStatusDTO;
import en.sd.chefmgmt.util.MailUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service("asyncMailService")
public class AsyncMailServiceBean extends JmsMessageSenderBase<MailRequestDTO> implements MailService {

    public AsyncMailServiceBean(
            JmsTemplate jmsTemplate,
            ObjectMapper objectMapper,
            @Value("${chef-mgmt-mail.async-mail-request-queue}") String destination
    ) {
        super(jmsTemplate, objectMapper, destination);
    }

    @Override
    public MailResponseDTO sendMail(MailRequestDTO mailRequestDTO) {
        SendingStatusDTO status = sendMessage(mailRequestDTO);

        return MailUtils.getMailResponseDTO(mailRequestDTO, status);
    }
}
