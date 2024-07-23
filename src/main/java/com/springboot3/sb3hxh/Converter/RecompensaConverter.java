package com.springboot3.sb3hxh.Converter;

import com.springboot3.sb3hxh.Entity.RecompensaEntity;
import com.springboot3.sb3hxh.Service.RecompensaService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RecompensaConverter implements Converter<String, RecompensaEntity> {

    private final RecompensaService recompensaService;

    public RecompensaConverter(RecompensaService recompensaService) {
        this.recompensaService = recompensaService;
    }

    @Override
    public RecompensaEntity convert(String source) {
        if (source.isEmpty()) {
            return null;
        }
        int id = Integer.parseInt(source);
        return recompensaService.read(id);
    }

}
