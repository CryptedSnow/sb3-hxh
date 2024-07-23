package com.springboot3.sb3hxh.Validation.ConstraintValidation;

import com.springboot3.sb3hxh.Entity.HunterEntity;
import com.springboot3.sb3hxh.Service.HunterService;
import com.springboot3.sb3hxh.Validation.HunterValidation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HunterConstraintValidation implements ConstraintValidator<HunterValidation, HunterEntity> {

    @Autowired
    private HunterService hunterService;

    @Override
    public void initialize(HunterValidation constraintAnnotation) {
    }

    @Override
    public boolean isValid(HunterEntity hunterEntity, ConstraintValidatorContext context) {
        if (hunterEntity == null) {
            return false;
        }
        String hunterIdAsString = String.valueOf(hunterEntity.getId());
        return hunterService.existsId(hunterIdAsString);
    }

}
