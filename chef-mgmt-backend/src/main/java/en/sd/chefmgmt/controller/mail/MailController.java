package en.sd.chefmgmt.controller.mail;

import en.sd.chefmgmt.exception.model.ExceptionBody;
import en.sd.chefmgmt.model.dto.mail.MailRequestDTO;
import en.sd.chefmgmt.model.dto.mail.MailResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("/v1/mail")
@Tag(name = "Mail Management", description = "Operations for managing mails")
public interface MailController {

    @PostMapping("/sync")
    @Operation(summary = "Send sync mail", description = "Sends synchronous mail")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mail successfully sent",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MailResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExceptionBody.class)))
    })
    @ResponseStatus(HttpStatus.OK)
    MailResponseDTO sendSyncMail(@Validated @RequestBody MailRequestDTO mailRequestDTO);

    @PostMapping("/async")
    @Operation(summary = "Send async mail", description = "Sends asynchronous mail")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mail successfully sent",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MailResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExceptionBody.class)))
    })
    @ResponseStatus(HttpStatus.OK)
    MailResponseDTO sendAsyncMail(@Validated @RequestBody MailRequestDTO mailRequestDTO);
}