package com.springboot3.sb3hxh.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.Locale;

@Entity
@Table(name="recompensas")
public class Recompensa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @NotBlank(message = "A descrição da recompensa é requerida")
    @Column(name="descricao_recompensa")
    private String descricaoRecompensa;

    @NotNull(message = "O valor da recompensa é requerida")
    @DecimalMin(value = "0.00", message = "O valor deve ser no mínimo R$ 0,00")
    @DecimalMax(value = "1000000.00", message = "O valor deve ser no máximo R$ 1.000.000,00")
    @Column(name="valor_recompensa")
    private Float valorRecompensa;

    @Column(name = "deleted_at")
    private LocalDateTime deleted_at;

    public Recompensa() {

    }

    public Recompensa(int id, String descricao_recompensa, Float valor_recompensa, LocalDateTime deleted_at) {
        this.id = id;
        this.descricaoRecompensa = descricao_recompensa;
        this.valorRecompensa = valor_recompensa;
        this.deleted_at = deleted_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricaoRecompensa() {
        return descricaoRecompensa;
    }

    public void setDescricaoRecompensa(String descricao_recompensa) {
        this.descricaoRecompensa = descricao_recompensa;
    }

    public Float getValorRecompensa() {
        return valorRecompensa;
    }

    public void setValorRecompensa(Float valor_recompensa) {
        this.valorRecompensa = valor_recompensa;
    }

    public LocalDateTime getDeletedAt() {
        return deleted_at;
    }

    public void setDeletedAt(LocalDateTime deleted_at) {
        this.deleted_at = deleted_at;
    }

    public String valorRecompensaFormatado() {
        NumberFormat format = DecimalFormat.getCurrencyInstance(new Locale("pt", "BR"));
        return format.format(this.valorRecompensa);
    }

}