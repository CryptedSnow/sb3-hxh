package com.springboot3.sb3hxh.Validation.ConstraintValidation;

import com.springboot3.sb3hxh.Entity.TipoSanguineoEntity;
import com.springboot3.sb3hxh.Service.TipoSanguineoService;
import com.springboot3.sb3hxh.Validation.TipoSanguineoValidation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TipoSanguineoConstraintValidation implements ConstraintValidator<TipoSanguineoValidation, TipoSanguineoEntity>  {

    @Autowired
    private TipoSanguineoService tipoSanguineoService;

    @Override
    public void initialize(TipoSanguineoValidation constraintAnnotation) {
    }

    @Override
    public boolean isValid(TipoSanguineoEntity tipoSanguineoEntity, ConstraintValidatorContext context) {
        if (tipoSanguineoEntity == null) {
            return false;
        }
        String tipoSanguineoIdAsString = String.valueOf(tipoSanguineoEntity.getId());
        return tipoSanguineoService.existsId(tipoSanguineoIdAsString);
    }

}
