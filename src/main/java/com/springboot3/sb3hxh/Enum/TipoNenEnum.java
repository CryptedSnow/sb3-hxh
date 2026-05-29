package com.springboot3.sb3hxh.Enum;

public enum TipoNenEnum {

    REFORCO("Reforço"),
    EMISSAO("Emissão"),
    TRANSFORMACAO("Transformação"),
    MANIPULACAO("Manipulação"),
    MATERIALIZACAO("Materialização"),
    ESPECIALIZACAO("Especialização");

    private final String descricao;

    TipoNenEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
