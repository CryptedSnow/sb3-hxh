package com.springboot3.sb3hxh.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity @Table(name="recompensados") @Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class RecompensadoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name="id")
    private int id;

    @OneToOne
    @NotNull(message = "Hunter é requerido")
    @JoinColumn(name="hunter_id", referencedColumnName="id")
    private HunterEntity hunter_id;

    @OneToOne
    @NotNull(message = "Recompensa é requerida")
    @JoinColumn(name="recompensa_id", referencedColumnName="id")
    private RecompensaEntity recompensa_id;

    @AssertTrue(message = "O status do recompensado é requerido")
    @Column(name="status")
    private Boolean status;

    @Column(name = "deleted_at")
    @Setter(AccessLevel.NONE)
    private LocalDateTime deleted_at;

    public void setId(int id) { this.id = id; }

    public HunterEntity getHunter_id() { return hunter_id; }

    public RecompensaEntity getRecompensa_id() { return recompensa_id; }

    public void setDeletedAt(LocalDateTime deletedAt) { this.deleted_at = deletedAt; }

    public String verificarStatus() { return status ? "Concluído" : "Não concluído"; }

}