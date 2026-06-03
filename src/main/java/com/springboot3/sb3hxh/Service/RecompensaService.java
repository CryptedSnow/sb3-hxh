package com.springboot3.sb3hxh.Service;

import com.springboot3.sb3hxh.Repository.RecompensaRepository;
import com.springboot3.sb3hxh.Entity.Recompensa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RecompensaService {

    private final RecompensaRepository recompensaRepository;

    public RecompensaService(RecompensaRepository recompensaRepository) {
        this.recompensaRepository = recompensaRepository;
    }

    public List<Recompensa> indexRecompensas() {
        return recompensaRepository.findByDeletedAtIsNullOrderByIdAsc();
    }

    public Page<Recompensa> indexRecompensasPagination(int page, int size) {
        return recompensaRepository.findByDeletedAtIsNullOrderByIdAsc(PageRequest.of(page, size));
    }

    public Page<Recompensa> buscarRecompensa(String search, int page, int size) {
        return recompensaRepository.searchAtivos(search, PageRequest.of(page, size));
    }

    public Recompensa createRecompensa(Recompensa recompensa) {
        return recompensaRepository.save(recompensa);
    }

    public Recompensa findRecompensaId(int id) {
        return recompensaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Recompensa ID " + id + " não encontrada."));
    }

    public Recompensa updateRecompensa(Recompensa recompensa) {
        return recompensaRepository.save(recompensa);
    }

    public void deleteRecompensaToTrash(int id) {
        Recompensa recompensa = findRecompensaId(id);
        recompensa.setDeletedAt(LocalDateTime.now());
        recompensaRepository.save(recompensa);
    }

    public Page<Recompensa> indexRecompensasTrash(int page, int size) {
        return recompensaRepository.findByDeletedAtIsNotNullOrderByIdAsc(PageRequest.of(page, size));
    }

    public Page<Recompensa> buscarRecompensaTrash(String search, int page, int size) {
        return recompensaRepository.searchLixeira(search, PageRequest.of(page, size));
    }

    public Recompensa restoreRecompensa(int id) {
        Recompensa recompensa = findRecompensaId(id);
        recompensa.setDeletedAt(null);
        return recompensaRepository.save(recompensa);
    }

    public boolean existsId(String id) {
        try {
            return recompensaRepository.existsById(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            return false;
        }
    }
}