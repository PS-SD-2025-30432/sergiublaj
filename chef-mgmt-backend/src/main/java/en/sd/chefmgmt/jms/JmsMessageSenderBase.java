package en.sd.chefmgmt.jms;

import com.fasterxml.jackson.databind.ObjectMapper;
import en.sd.chefmgmt.model.dto.mail.SendingStatusDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;

@Slf4j
public abstract class JmsMessageSenderBase<Request> implements MessageSender<Request> {

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;
    private final String destination;

    protected JmsMessageSenderBase(JmsTemplate jmsTemplate, ObjectMapper objectMapper, String destination) {
        this.jmsTemplate = jmsTemplate;
        this.objectMapper = objectMapper;
        this.destination = destination;
    }

    @Override
    public SendingStatusDTO sendMessage(Request request) {
        log.info("Sending message <{}> to queue <{}>", request, destination);

        try {
            String payload = objectMapper.writeValueAsString(request);
            jmsTemplate.convertAndSend(destination, payload);

            return SendingStatusDTO.SUCCESS;
        } catch (Exception e) {
            log.error("Failed to send message <{}> to queue <{}>", request, destination);

            return SendingStatusDTO.FAILURE;
        }
    }
}