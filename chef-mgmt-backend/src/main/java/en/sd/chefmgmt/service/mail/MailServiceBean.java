package en.sd.chefmgmt.service.mail;

import en.sd.chefmgmt.model.dto.mail.MailRequestDTO;
import en.sd.chefmgmt.model.dto.mail.MailResponseDTO;
import en.sd.chefmgmt.resttemplate.WebClientBase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class MailServiceBean extends WebClientBase<MailRequestDTO, MailResponseDTO> implements MailService {

    private final String url;

    public MailServiceBean(
            WebClient.Builder webClientBuilder,
            @Value("${chef-mgmt-mail.sync-mail-url}") String url
    ) {
        super(webClientBuilder.build());
        this.url = url;
    }

    @Override
    public MailResponseDTO sendMail(MailRequestDTO mailRequestDTO) {
        return postForEntity(url, mailRequestDTO);
    }

    @Override
    public Class<MailResponseDTO> getResponseType() {
        return MailResponseDTO.class;
    }

    @Override
    public String getExceptionMessage(MailRequestDTO mailRequestDTO) {
        return String.format("mail %s ---> %s (FAIL)", mailRequestDTO.from(), mailRequestDTO.to());
    }
}