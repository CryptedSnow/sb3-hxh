package com.springboot3.sb3hxh.Converter;

import com.springboot3.sb3hxh.Entity.TipoSanguineoEntity;
import com.springboot3.sb3hxh.Service.TipoSanguineoService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TipoSanguineoConverter implements Converter<String, TipoSanguineoEntity> {

    private final TipoSanguineoService tipoSanguineoService;

    public TipoSanguineoConverter(TipoSanguineoService tipoSanguineoService) {
        this.tipoSanguineoService = tipoSanguineoService;
    }

    @Override
    public TipoSanguineoEntity convert(String source) {
        if (source.isEmpty()) {
            return null;
        }
        int id = Integer.parseInt(source);
        return tipoSanguineoService.read(id);
    }

}