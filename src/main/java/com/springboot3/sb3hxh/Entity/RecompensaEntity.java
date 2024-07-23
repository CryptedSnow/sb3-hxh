package com.springboot3.sb3hxh.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.Locale;

@Entity @Table(name="recompensas") @Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class RecompensaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name="id")
    private int id;

    @NotBlank(message = "A descrição da recompensa é requerida")
    @Column(name="descricao_recompensa")
    private String descricao_recompensa;

    @NotNull(message = "O valor da recompensa é requerida")
    @DecimalMin(value = "0.00", message = "O valor deve ser no mínimo R$ 0,00")
    @DecimalMax(value = "1000000.00", message = "O valor deve ser no máximo R$ 1.000.000,00")
    @Column(name="valor_recompensa")
    private Float valor_recompensa;

    @Column(name = "deleted_at")
    @Setter(AccessLevel.NONE)
    private LocalDateTime deleted_at;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getDescricaoRecompensa() { return descricao_recompensa; }

    public void setDeletedAt(LocalDateTime deletedAt) { this.deleted_at = deletedAt; }

    public String valorRecompensaFormatado() {
        NumberFormat format = DecimalFormat.getCurrencyInstance(new Locale("pt", "BR"));
        return format.format(this.valor_recompensa);
    }

}