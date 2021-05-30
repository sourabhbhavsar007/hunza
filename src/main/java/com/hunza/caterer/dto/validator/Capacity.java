package com.hunza.caterer.dto.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = { CapacityValidator.class })
public @interface Capacity
{
    String message() default "Should not be less than min number of guests";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
