package en.sd.chefmgmt.model.dto.mail;

public record MailResponseDTO(String from, String to, SendingStatusDTO status) { }