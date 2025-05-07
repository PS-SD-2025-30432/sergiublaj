package en.sd.model.mail;

public record MailRequestDTO(String from, String to, String subject, String body) { }
