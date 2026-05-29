package com.springboot3.sb3hxh.Converter;

import com.springboot3.sb3hxh.Enum.TipoSanguineoEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class TipoSanguineoConverter implements AttributeConverter<TipoSanguineoEnum, String> {

    @Override
    public String convertToDatabaseColumn(TipoSanguineoEnum attribute) {
        if (attribute == null) return null;
        return attribute.getDescricao();
    }

    @Override
    public TipoSanguineoEnum convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        for (TipoSanguineoEnum tipoSanguineo : TipoSanguineoEnum.values()) {
            if (tipoSanguineo.getDescricao().equals(dbData)) return tipoSanguineo;
        }
        throw new IllegalArgumentException("Tipo sanguíneo desconhecido: " + dbData);
    }
}
