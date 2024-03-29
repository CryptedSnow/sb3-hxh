package com.springboot3.sb3hxh.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.*;

@Entity
@Table(name="tipos_nens")
public class TipoNenModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @NotBlank(message = "A descrição do tipo de nen é requerida.")
    @Column(name="descricao")
    private String descricao;

    @Column(name = "deleted_at")
    private LocalDateTime deleted_at;

    public TipoNenModel() {
    }

    public TipoNenModel(int id, String descricao, LocalDateTime deleted_at) {
        this.id = id;
        this.descricao = descricao;
        this.deleted_at = deleted_at;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getDescricao() { return descricao; }

    public void setDescricao(String descricao) { this.descricao = descricao; }

    public LocalDateTime getDeleted_at() { return deleted_at; }

    public void setDeleted_at(LocalDateTime deleted_at) { this.deleted_at = deleted_at; }

}