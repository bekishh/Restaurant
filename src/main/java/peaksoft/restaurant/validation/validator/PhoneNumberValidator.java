package peaksoft.restaurant.validation.validator;

import peaksoft.restaurant.validation.PhoneNumberValidation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumberValidation,String > {

    @Override
    public boolean isValid(String p, ConstraintValidatorContext constraintValidatorContext) {
        return p.startsWith("+996") && p.length()==13 ;
    }
}
