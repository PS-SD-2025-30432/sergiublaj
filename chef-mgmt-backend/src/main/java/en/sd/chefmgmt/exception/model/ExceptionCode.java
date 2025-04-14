package en.sd.chefmgmt.exception.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionCode {
    // Validation & Constraint Violations
    VALIDATION_ERROR("Validation failed.", "ERR_1001"),
    CONSTRAINT_VIOLATION("Constraint violation.", "ERR_1002"),

    // Chef Errors
    CHEF_NOT_FOUND("Chef %s not found.", "ERR_2001"),
    CNP_TAKEN("CNP %s is already taken.", "ERR_2002"),

    // Auth Errors
    INVALID_CREDENTIALS("Invalid credentials.", "ERR_3001"),
    FORBIDDEN_ACCESS("Access is forbidden.", "ERR_3002"),

    // User Errors
    USER_NOT_FOUND("User %s not found.", "ERR_4001"),

    // RestTemplate Errors
    REST_TEMPLATE_ERROR("Error occurred while calling external service: %s", "ERR_5001"),

    // Server Errors
    SERVER_ERROR("Internal server error.", "ERR_9000");

    private final String message;
    private final String code;
}