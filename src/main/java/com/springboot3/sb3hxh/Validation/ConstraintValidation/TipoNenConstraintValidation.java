package com.springboot3.sb3hxh.Validation.ConstraintValidation;

import com.springboot3.sb3hxh.Entity.TipoNenEntity;
import com.springboot3.sb3hxh.Service.TipoNenService;
import com.springboot3.sb3hxh.Validation.TipoNenValidation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TipoNenConstraintValidation implements ConstraintValidator<TipoNenValidation, TipoNenEntity> {

    @Autowired
    private TipoNenService tipoNenService;

    @Override
    public void initialize(TipoNenValidation constraintAnnotation) {
    }

    @Override
    public boolean isValid(TipoNenEntity tipoNenEntity, ConstraintValidatorContext context) {
        if (tipoNenEntity == null) {
            return false;
        }
        String tipoNenIdAsString = String.valueOf(tipoNenEntity.getId());
        return tipoNenService.existsId(tipoNenIdAsString);
    }

}