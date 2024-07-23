package com.springboot3.sb3hxh.Validation.ConstraintValidation;

import com.springboot3.sb3hxh.Entity.RecompensaEntity;
import com.springboot3.sb3hxh.Service.RecompensaService;
import com.springboot3.sb3hxh.Validation.RecompensaValidation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RecompensaConstraintValidation implements ConstraintValidator<RecompensaValidation, RecompensaEntity> {

    @Autowired
    private RecompensaService recompensaService;

    @Override
    public void initialize(RecompensaValidation constraintAnnotation) {
    }

    @Override
    public boolean isValid(RecompensaEntity recompensaEntity, ConstraintValidatorContext context) {
        if (recompensaEntity == null) {
            return false;
        }
        String recompensaIdAsString = String.valueOf(recompensaEntity.getId());
        return recompensaService.existsId(recompensaIdAsString);
    }

}