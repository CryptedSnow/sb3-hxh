package com.springboot3.sb3hxh.Validation.ConstraintValidation;

import com.springboot3.sb3hxh.Entity.TipoHunterEntity;
import com.springboot3.sb3hxh.Service.TipoHunterService;
import com.springboot3.sb3hxh.Validation.TipoHunterValidation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TipoHunterConstraintValidation implements ConstraintValidator<TipoHunterValidation, TipoHunterEntity> {

    @Autowired
    private TipoHunterService tipoHunterService;

    @Override
    public void initialize(TipoHunterValidation constraintAnnotation) {
    }

    @Override
    public boolean isValid(TipoHunterEntity tipoHunterEntity, ConstraintValidatorContext context) {
        if (tipoHunterEntity == null) {
            return false;
        }
        String tipoHunterIdAsString = String.valueOf(tipoHunterEntity.getId());
        return tipoHunterService.existsId(tipoHunterIdAsString);
    }

}