package en.sd.chefmgmt.model.dto.mail;

import jakarta.validation.constraints.NotBlank;

public record MailRequestDTO(
        String from,

        @NotBlank(message = "Receiver is required and cannot be empty.")
        String to,

        @NotBlank(message = "Subject is required and cannot be empty.")
        String subject,

        @NotBlank(message = "Body is required and cannot be empty.")
        String body
) {

    public MailRequestDTO withFrom(String from) {
        return new MailRequestDTO(from, to(), subject(), body());
    }
}
