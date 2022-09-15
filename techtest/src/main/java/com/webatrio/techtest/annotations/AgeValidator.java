package com.webatrio.techtest.annotations;

import java.time.LocalDate;
import java.time.Period;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AgeValidator implements ConstraintValidator<AgeConstraint, LocalDate> {

	@Override
	public void initialize(AgeConstraint ageConstraint) {
	}

	@Override
	public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
		LocalDate curDate = LocalDate.now();

		return (((value != null) && (curDate != null)) && (Period.between(value, curDate).getYears()) < 150);

	}

}
