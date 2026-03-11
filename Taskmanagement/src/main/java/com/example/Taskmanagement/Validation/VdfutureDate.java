package com.example.Taskmanagement.Validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = VDtfutureDate.class)
public @interface VdfutureDate {

    String message() default "Due date must be today or later";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
