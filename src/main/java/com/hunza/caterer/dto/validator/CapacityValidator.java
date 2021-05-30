package com.hunza.caterer.dto.validator;

import com.hunza.caterer.dto.CapacityDTO;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

// Validator on Guests Capacity
@Component
public class CapacityValidator implements ConstraintValidator<Capacity, CapacityDTO>
{
    @Override
    public boolean isValid(CapacityDTO capacityDTO, ConstraintValidatorContext constraintValidatorContext)
    {
        return (capacityDTO.getMax() >= capacityDTO.getMin());
    }
}
