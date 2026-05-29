package com.springboot3.sb3hxh.Converter;

import com.springboot3.sb3hxh.Enum.TipoNenEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class TipoNenConverter implements AttributeConverter<TipoNenEnum, String> {

    @Override
    public String convertToDatabaseColumn(TipoNenEnum attribute) {
        if (attribute == null) return null;
        return attribute.getDescricao();
    }

    @Override
    public TipoNenEnum convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        for (TipoNenEnum tipoNen : TipoNenEnum.values()) {
            if (tipoNen.getDescricao().equals(dbData)) return tipoNen;
        }
        throw new IllegalArgumentException("Tipo de Nen desconhecido: " + dbData);
    }
}