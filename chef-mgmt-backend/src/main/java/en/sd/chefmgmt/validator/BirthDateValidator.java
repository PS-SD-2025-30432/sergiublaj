package en.sd.chefmgmt.validator;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class BirthDateValidator implements ConstraintValidator<ValidBirthDate, ZonedDateTime> {

    @Override
    public boolean isValid(ZonedDateTime birthDate, ConstraintValidatorContext context) {
        if (birthDate == null) {
            return false;
        }

        ZonedDateTime now = ZonedDateTime.now();
        long age = ChronoUnit.YEARS.between(birthDate, now);

        return birthDate.isBefore(now) && age >= 18;
    }
}