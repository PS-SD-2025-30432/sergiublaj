package en.sd.chefmgmt.util;

import en.sd.chefmgmt.model.dto.mail.MailRequestDTO;
import en.sd.chefmgmt.model.dto.mail.MailResponseDTO;
import en.sd.chefmgmt.model.dto.mail.SendingStatusDTO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MailUtils {

    public MailResponseDTO getMailResponseDTO(MailRequestDTO mailRequestDTO, SendingStatusDTO status) {
        return MailResponseDTO.builder()
                .from(mailRequestDTO.from())
                .to(mailRequestDTO.to())
                .status(status)
                .build();
    }
}