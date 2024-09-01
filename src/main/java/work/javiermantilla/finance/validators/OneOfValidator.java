package work.javiermantilla.finance.validators;

import java.util.Arrays;
import java.util.List;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class OneOfValidator implements ConstraintValidator<OneOf, String> {

    private List<String> allowedValues;

    @Override
    public void initialize(OneOf constraintAnnotation) {
        allowedValues = Arrays.asList(constraintAnnotation.allowedValues());
    }

	@Override
	public boolean isValid(String valor, ConstraintValidatorContext arg1) {
		return allowedValues.contains(valor);
	}
}