package com.springboot3.sb3hxh.Enum;

public enum TipoHunterEnum {

    GOURMET("Hunter Gourmet"),
    ARQUEOLOGO("Hunter Arqueólogo"),
    CIENTISTA("Hunter Cientista ou Hunter Técnico"),
    SELVAGEM("Hunter Selvagem ou Hunter Ambientalista"),
    MUSICAL("Hunter Musical"),
    TREASURE("Hunter Treasure"),
    LISTA_NEGRA("Hunter Lista Negra"),
    MERCENARIO("Hunter Mercenário"),
    MEDICINAL("Hunter Medicinal"),
    HACKER("Hunter Hacker"),
    CABECA("Hunter Cabeça"),
    INFORMACAO("Hunter de Informação"),
    JACKSPOT("Hunter Jackspot"),
    VIRUS("Hunter Vírus"),
    JUVENTUDE_BELEZA("Hunter da Juventude e Beleza"),
    TERRORISTA("Hunter Terrorista"),
    VENENOS("Hunter de Venenos"),
    CACADOR("Hunter Caçador"),
    PALEOGRAFO("Hunter Paleógrafo"),
    PERDIDO("Hunter Perdido"),
    PROVISORIO("Hunter Provisório"),
    TEMPORARIO("Hunter Temporário");

    private final String descricao;

    TipoHunterEnum(String descricao) {
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
