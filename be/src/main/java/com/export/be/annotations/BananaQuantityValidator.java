package com.export.be.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BananaQuantityValidator implements ConstraintValidator<BananaQuantityConstraint, Integer> {

	@Override
	public void initialize(BananaQuantityConstraint bananaQuantity) {
	}

	@Override
	public boolean isValid(Integer bqField, ConstraintValidatorContext cxt) {

		return bqField != null && bqField % 25 == 0;
	}

}
