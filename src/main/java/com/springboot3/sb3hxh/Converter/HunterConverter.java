package com.springboot3.sb3hxh.Converter;

import com.springboot3.sb3hxh.Entity.Hunter;
import com.springboot3.sb3hxh.Service.HunterService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class HunterConverter implements Converter<String, Hunter> {

    private final HunterService hunterService;

    public HunterConverter(HunterService hunterService) {
        this.hunterService = hunterService;
    }

    @Override
    public Hunter convert(String source) {
        if (source.isEmpty()) {
            return null;
        }
        int id = Integer.parseInt(source);
        return hunterService.read(id);
    }

}