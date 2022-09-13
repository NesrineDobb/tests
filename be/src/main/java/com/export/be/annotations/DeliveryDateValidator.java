package com.export.be.annotations;

import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DeliveryDateValidator implements ConstraintValidator<DeliveryDateConstraint, Date> {

	@Override
	public void initialize(DeliveryDateConstraint deliveryDate) {
	}

	@Override
	public boolean isValid(Date ddField, ConstraintValidatorContext cxt) {
		Date dt = new Date(new Date().getTime() + 604800000);
		return ddField != null && ((ddField.after(dt)) || (ddField.equals(dt)));
	}

}
