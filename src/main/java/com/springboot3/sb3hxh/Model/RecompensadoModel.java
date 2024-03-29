package com.springboot3.sb3hxh.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.*;

@Entity
@Table(name="recompensados")
public class RecompensadoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @OneToOne
    @NotNull(message = "Hunter é requerido.")
    @JoinColumn(name="hunter_id", referencedColumnName="id")
    private HunterModel hunter_id;

    @OneToOne
    @NotNull(message = "Recompensa é requerido.")
    @JoinColumn(name="recompensa_id", referencedColumnName="id")
    private RecompensaModel recompensa_id;

    @NotNull(message = "Status do recompensado é requerido")
    @Column(name="status")
    private Boolean status;

    @Column(name = "deleted_at")
    private LocalDateTime deleted_at;

    public RecompensadoModel() {

    }

    public RecompensadoModel(int id, Boolean status, LocalDateTime deleted_at) {
        this.id = id;
        this.status = status;
        this.deleted_at = deleted_at;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public HunterModel getHunter_id() { return hunter_id; }

    public void setHunter_id(HunterModel hunter_id) { this.hunter_id = hunter_id; }

    public RecompensaModel getRecompensa_id() { return recompensa_id; }

    public void setRecompensa_id(RecompensaModel recompensa_id) { this.recompensa_id = recompensa_id; }

    public Boolean getStatus() { return status; }

    public void setStatus(Boolean status) { this.status = status; }

    public LocalDateTime getDeleted_at() { return deleted_at; }

    public void setDeleted_at(LocalDateTime deleted_at) { this.deleted_at = deleted_at; }

}