package com.springboot3.sb3hxh.Converter;

import com.springboot3.sb3hxh.Entity.TipoNenEntity;
import com.springboot3.sb3hxh.Service.TipoNenService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TipoNenConverter implements Converter<String, TipoNenEntity>  {

    private final TipoNenService tipoNenService;

    public TipoNenConverter(TipoNenService tipoNenService) {
        this.tipoNenService = tipoNenService;
    }

    @Override
    public TipoNenEntity convert(String source) {
        if (source.isEmpty()) {
            return null;
        }
        int id = Integer.parseInt(source);
        return tipoNenService.read(id);
    }

}