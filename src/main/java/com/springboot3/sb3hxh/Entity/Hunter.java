package com.springboot3.sb3hxh.Entity;

import com.springboot3.sb3hxh.Converter.TipoHunterConverter;
import com.springboot3.sb3hxh.Converter.TipoNenConverter;
import com.springboot3.sb3hxh.Converter.TipoSanguineoConverter;
import com.springboot3.sb3hxh.Enum.TipoHunterEnum;
import com.springboot3.sb3hxh.Enum.TipoNenEnum;
import com.springboot3.sb3hxh.Enum.TipoSanguineoEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name="hunters")
public class Hunter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @NotBlank(message = "O nome do Hunter é requerido")
    @Column(name="nome_hunter")
    private String nomeHunter;

    @NotNull(message = "A idade do Hunter é requerida")
    @Min(value = 13, message = "A idade do Hunter deve ser no mínimo de 13 anos")
    @Max(value = 969, message = "A idade do Hunter deve ser no máximo de 969 anos")
    @Column(name="idade_hunter")
    private Integer idadeHunter;

    @NotNull(message = "A altura do Hunter é requerida")
    @DecimalMin(value = "1.50", message = "A altura deve ser no mínimo 1.50m")
    @DecimalMax(value = "2.50", message = "A altura deve ser no máximo 2.50m")
    @Column(name="altura_hunter")
    private Float alturaHunter;

    @NotNull(message = "O peso do Hunter é requerido")
    @DecimalMin(value = "50.00", message = "O peso do Hunter deve ser no mínimo 50.00kg")
    @DecimalMax(value = "150.00", message = "O peso do Hunter deve ser no máximo 150.00kg")
    @Column(name="peso_hunter")
    private Float pesoHunter;

    @NotNull(message = "O tipo de Hunter é requerido")
    @Convert(converter = TipoHunterConverter.class)
    @Column(name = "tipo_hunter")
    private TipoHunterEnum tipoHunter;

    @NotNull(message = "O tipo de Nen do Hunter é requerido")
    @Convert(converter = TipoNenConverter.class)
    @Column(name = "tipo_nen")
    private TipoNenEnum tipoNen;

    @NotNull(message = "O tipo sanguíneo do Hunter é requerido")
    @Convert(converter = TipoSanguineoConverter.class)
    @Column(name = "tipo_sanguineo")
    private TipoSanguineoEnum tipoSanguineo;

    @NotNull(message = "A data de início é requerida")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date inicio;

    @NotNull(message = "A data de término é requerida")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date termino;

    @Column(name = "deleted_at")
    private LocalDateTime deleted_at;

    public Hunter() {

    }

    public Hunter(int id, String nome_hunter, Integer idade_hunter, Float altura_hunter, Float peso_hunter, TipoHunterEnum tipo_hunter, TipoNenEnum tipo_nen, TipoSanguineoEnum tipo_sanguineo, Date inicio, Date termino, LocalDateTime deleted_at) {
        this.id = id;
        this.nomeHunter = nome_hunter;
        this.idadeHunter = idade_hunter;
        this.alturaHunter = altura_hunter;
        this.pesoHunter = peso_hunter;
        this.tipoHunter = tipo_hunter;
        this.tipoNen = tipo_nen;
        this.tipoSanguineo = tipo_sanguineo;
        this.inicio = inicio;
        this.termino = termino;
        this.deleted_at = deleted_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeHunter() {
        return nomeHunter;
    }

    public void setNomeHunter(String nome_hunter) {
        this.nomeHunter = nome_hunter;
    }

    public Integer getIdadeHunter() {
        return idadeHunter;
    }

    public void setIdadeHunter(Integer idade_hunter) {
        this.idadeHunter = idade_hunter;
    }

    public Float getAlturaHunter() {
        return alturaHunter;
    }

    public void setAlturaHunter(Float altura_hunter) {
        this.alturaHunter = altura_hunter;
    }

    public Float getPesoHunter() {
        return pesoHunter;
    }

    public void setPesoHunter(Float peso_hunter) {
        this.pesoHunter = peso_hunter;
    }

    public TipoHunterEnum getTipoHunter() {
        return tipoHunter;
    }

    public void setTipoHunter(TipoHunterEnum tipo_hunter) {
        this.tipoHunter = tipo_hunter;
    }

    public TipoNenEnum getTipoNen() {
        return tipoNen;
    }

    public void setTipoNen(TipoNenEnum tipo_nen) {
        this.tipoNen = tipo_nen;
    }

    public TipoSanguineoEnum getTipoSanguineo() {
        return tipoSanguineo;
    }

    public void setTipoSanguineo(TipoSanguineoEnum tipo_sanguineo) {
        this.tipoSanguineo = tipo_sanguineo;
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getTermino() {
        return termino;
    }

    public void setTermino(Date termino) {
        this.termino = termino;
    }

    public LocalDateTime getDeletedAt() {
        return deleted_at;
    }

    public void setDeletedAt(LocalDateTime deleted_at) {
        this.deleted_at = deleted_at;
    }

    public String alturaFormatada() {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return decimalFormat.format(this.alturaHunter) + " m";
    }

    public String pesoFormatado() {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return decimalFormat.format(this.pesoHunter) + " kg";
    }

    public String inicioFormatado() {
        if (this.inicio != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            return simpleDateFormat.format(this.inicio);
        }
        return "";
    }

    public String terminoFormatado() {
        if (this.termino != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            return simpleDateFormat.format(this.termino);
        }
        return "";
    }
}
