package com.springboot3.sb3hxh.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name="recompensados")
public class Recompensado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @OneToOne
    @NotNull(message = "Hunter é requerido")
    @JoinColumn(name="hunter_id", referencedColumnName="id")
    private Hunter hunterId;

    @OneToOne
    @NotNull(message = "Recompensa é requerida")
    @JoinColumn(name="recompensa_id", referencedColumnName="id")
    private Recompensa recompensaId;

    @AssertTrue(message = "O status do recompensado é requerido")
    @Column(name="status")
    private Boolean status;

    @Column(name = "deleted_at")
    private LocalDateTime deleted_at;

    public Recompensado() {

    }

    public Recompensado(int id, Hunter hunter_id, Recompensa recompensa_id, Boolean status) {
        this.id = id;
        this.hunterId = hunter_id;
        this.recompensaId = recompensa_id;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Hunter getHunterId() {
        return hunterId;
    }

    public void setHunterId(Hunter hunter_id) {
        this.hunterId = hunter_id;
    }

    public Recompensa getRecompensaId() {
        return recompensaId;
    }

    public void setRecompensaId(Recompensa recompensa_id) {
        this.recompensaId = recompensa_id;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public LocalDateTime getDeletedAt() {
        return deleted_at;
    }

    public void setDeletedAt(LocalDateTime deleted_at) {
        this.deleted_at = deleted_at;
    }

    public String verificarStatus() {
        return status ? "Concluído" : "Não concluído";
    }
}
