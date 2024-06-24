package peaksoft.restaurant.validation.validator;

import peaksoft.restaurant.validation.EmailValidation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<EmailValidation,String > {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s.endsWith("@gmail.com") ;
    }
}
