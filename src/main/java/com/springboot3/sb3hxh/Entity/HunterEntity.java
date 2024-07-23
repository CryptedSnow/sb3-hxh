package com.springboot3.sb3hxh.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import com.springboot3.sb3hxh.Validation.*;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@Entity @Table(name="hunters") @Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class HunterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name="id")
    private int id;

    @NotBlank(message = "O nome do Hunter é requerido")
    @Column(name="nome_hunter")
    private String nome_hunter;

    @NotNull(message = "A idade do Hunter é requerida")
    @Min(value = 13, message = "A idade do Hunter deve ser no mínimo de 13 anos")
    @Column(name="idade_hunter")
    private Integer idade_hunter;

    @NotNull(message = "A altura do Hunter é requerida")
    @DecimalMin(value = "1.50", message = "A altura deve ser no mínimo 1.50m")
    @DecimalMax(value = "2.50", message = "A altura deve ser no máximo 2.50m")
    @Column(name="altura_hunter")
    private Float altura_hunter;

    @NotNull(message = "O peso do Hunter é requerido")
    @DecimalMin(value = "50.00", message = "O peso do Hunter deve ser no mínimo 50.00kg")
    @DecimalMax(value = "150.00", message = "O peso do Hunter deve ser no máximo 150.00kg")
    @Column(name="peso_hunter")
    private Float peso_hunter;

    @OneToOne
    @NotNull(message = "O tipo de Hunter é requerido")
    @JoinColumn(name="tipo_hunter_id", referencedColumnName="id")
    private TipoHunterEntity tipo_hunter_id;

    @OneToOne
    @NotNull(message = "O tipo de Nen é requerido")
    @JoinColumn(name="tipo_nen_id", referencedColumnName="id")
    private TipoNenEntity tipo_nen_id;

    @OneToOne
    @NotNull(message = "O tipo sanguíneo é requerido")
    @JoinColumn(name="tipo_sanguineo_id", referencedColumnName="id")
    private TipoSanguineoEntity tipo_sanguineo_id;

    @NotNull(message = "A data de início é requerida")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date inicio;

    @NotNull(message = "A data de término é requerida")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date termino;

    @Column(name = "deleted_at")
    @Setter(AccessLevel.NONE)
    private LocalDateTime deleted_at;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getNomeHunter() { return nome_hunter; }

    public void setDeletedAt(LocalDateTime deletedAt) { this.deleted_at = deletedAt; }

    public String alturaFormatada() {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(this.altura_hunter) + " m";
    }

    public String pesoFormatado() {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(this.peso_hunter) + " kg";
    }

    public String inicioFormatado() {
        if (this.inicio != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return sdf.format(this.inicio);
        } else {
            return "";
        }
    }

    public String terminoFormatado() {
        if (this.termino != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return sdf.format(this.termino);
        } else {
            return "";
        }
    }

}