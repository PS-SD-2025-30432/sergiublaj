package en.sd.chefmgmt.model.dto.mail;

import lombok.Builder;

@Builder
public record MailResponseDTO(String from, String to, SendingStatusDTO status) { }