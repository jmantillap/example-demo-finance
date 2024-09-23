package work.javiermantilla.finance.cross.validators;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({FIELD,PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = OneOfValidator.class)
public @interface OneOf {

    String message() default "El valor dado no es valido.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    /** The array of allowed values. */
    String[] allowedValues();
}