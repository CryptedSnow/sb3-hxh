package com.springboot3.sb3hxh.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name="recompensados")
public class RecompensadoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private LocalDateTime deleted_at;

    public RecompensadoEntity() {

    }

    public RecompensadoEntity(int id, HunterEntity hunter_id, RecompensaEntity recompensa_id, Boolean status) {
        this.id = id;
        this.hunter_id = hunter_id;
        this.recompensa_id = recompensa_id;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public HunterEntity getHunter_id() {
        return hunter_id;
    }

    public void setHunter_id(HunterEntity hunter_id) {
        this.hunter_id = hunter_id;
    }

    public RecompensaEntity getRecompensa_id() {
        return recompensa_id;
    }

    public void setRecompensa_id(RecompensaEntity recompensa_id) {
        this.recompensa_id = recompensa_id;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public LocalDateTime getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(LocalDateTime deleted_at) {
        this.deleted_at = deleted_at;
    }

    public String verificarStatus() {
        return status ? "Concluído" : "Não concluído";
    }
}
