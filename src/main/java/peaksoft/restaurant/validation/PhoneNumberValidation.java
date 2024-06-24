package peaksoft.restaurant.validation;

import peaksoft.restaurant.validation.validator.PhoneNumberValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneNumberValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
public @interface PhoneNumberValidation {

    String message() default "PhoneNumber must length < 13 and start with '+996' ";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}