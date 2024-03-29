package com.springboot3.sb3hxh.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.text.*;
import java.time.*;
import java.util.*;

@Entity
@Table(name="recompensas")
public class RecompensaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @NotBlank(message = "A descrição da recompensa é requerida.")
    @Column(name="descricao_recompensa")
    private String descricao_recompensa;

    @NotNull(message = "O valor da recompensa é requerida.")
    @DecimalMin(value = "0.00", message = "O valor deve ser no mínimo R$ 0,00")
    @DecimalMax(value = "1000000.00", message = "O valor deve ser no máximo R$ 1.000.000,00")
    @Column(name="valor_recompensa")
    private Float valor_recompensa;

    @Column(name = "deleted_at")
    private LocalDateTime deleted_at;

    public RecompensaModel(){

    }

    public RecompensaModel(int id, String descricao_recompensa, Float valor_recompensa, LocalDateTime deleted_at) {
        this.id = id;
        this.descricao_recompensa = descricao_recompensa;
        this.valor_recompensa = valor_recompensa;
        this.deleted_at = deleted_at;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getDescricao_recompensa() { return descricao_recompensa; }

    public void setDescricao_recompensa(String descricao_recompensa) { this.descricao_recompensa = descricao_recompensa; }

    public Float getValor_recompensa() { return valor_recompensa; }

    public void setValor_recompensa(Float valor_recompensa) { this.valor_recompensa = valor_recompensa; }

    public LocalDateTime getDeletedAt() { return deleted_at; }

    public void setDeletedAt(LocalDateTime deleted_at) { this.deleted_at = deleted_at; }

    public String getValorRecompensaFormatado() {
        NumberFormat format = DecimalFormat.getCurrencyInstance(new Locale("pt", "BR"));
        return format.format(this.getValor_recompensa());
    }

}