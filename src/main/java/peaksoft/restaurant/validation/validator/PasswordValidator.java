package peaksoft.restaurant.validation.validator;

import peaksoft.restaurant.validation.PasswordValidation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordValidation,String > {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s.length()>=4;
    }
}
