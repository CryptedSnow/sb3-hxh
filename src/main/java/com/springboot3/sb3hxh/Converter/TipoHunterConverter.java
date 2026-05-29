package com.springboot3.sb3hxh.Converter;

import com.springboot3.sb3hxh.Enum.TipoHunterEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class TipoHunterConverter implements AttributeConverter<TipoHunterEnum, String> {

    @Override
    public String convertToDatabaseColumn(TipoHunterEnum attribute) {
        if (attribute == null) return null;
        return attribute.getDescricao();
    }

    @Override
    public TipoHunterEnum convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        for (TipoHunterEnum tipoHunter : TipoHunterEnum.values()) {
            if (tipoHunter.getDescricao().equals(dbData)) return tipoHunter;
        }
        throw new IllegalArgumentException("Tipo de Hunter desconhecido: " + dbData);
    }
}
