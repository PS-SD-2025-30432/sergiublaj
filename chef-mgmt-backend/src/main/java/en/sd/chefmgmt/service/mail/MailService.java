package en.sd.chefmgmt.service.mail;

import en.sd.chefmgmt.model.dto.mail.MailRequestDTO;
import en.sd.chefmgmt.model.dto.mail.MailResponseDTO;

public interface MailService {

    MailResponseDTO sendMail(MailRequestDTO mailRequestDTO);
}
