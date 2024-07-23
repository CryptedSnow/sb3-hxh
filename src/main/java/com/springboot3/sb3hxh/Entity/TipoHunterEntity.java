package com.springboot3.sb3hxh.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Entity @Table(name="tipos_hunters") @Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class TipoHunterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name="id")
    private int id;

    @NotBlank(message = "A descrição do tipo de Hunter é requerida")
    @Column(name="descricao")
    private String descricao;

    @Column(name = "deleted_at")
    @Setter(AccessLevel.NONE)
    private LocalDateTime deleted_at;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public void setDeletedAt(LocalDateTime deletedAt) { this.deleted_at = deletedAt; }

}