package com.springboot3.sb3hxh.Service;

import com.springboot3.sb3hxh.Repository.RecompensadoRepository;
import com.springboot3.sb3hxh.Entity.Recompensado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RecompensadoService {

    private final RecompensadoRepository recompensadoRepository;

    public RecompensadoService(RecompensadoRepository recompensadoRepository) {
        this.recompensadoRepository = recompensadoRepository;
    }

    public List<Recompensado> indexRecompensados() {
        return recompensadoRepository.findAllAtivos();
    }

    public Page<Recompensado> indexRecompensadosPagination(int page, int size) {
        return recompensadoRepository.findAllAtivosPaginated(PageRequest.of(page, size));
    }

    public Page<Recompensado> searchRecompensados(String search, int page, int size) {
        return recompensadoRepository.searchAtivos(search, PageRequest.of(page, size));
    }

    public Recompensado createRecompensado(Recompensado recompensado) {
        return recompensadoRepository.save(recompensado);
    }

    public Recompensado findIdRecompensado(int id) {
        return recompensadoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Recompensado ID " + id + " não encontrado."));
    }

    public void deleteRecompensadoToTrash(int id) {
        Recompensado recompensado = findIdRecompensado(id);
        recompensado.setDeletedAt(LocalDateTime.now());
        recompensadoRepository.save(recompensado);
    }

    public Page<Recompensado> indexRecompensadosTrash(int page, int size) {
        return recompensadoRepository.findAllLixeiraPaginated(PageRequest.of(page, size));
    }

    public Page<Recompensado> searchRecompensadosTrash(String search, int page, int size) {
        return recompensadoRepository.searchLixeira(search, PageRequest.of(page, size));
    }

    public Recompensado restoreRecompensado(int id) {
        Recompensado recompensado = findIdRecompensado(id);
        recompensado.setDeletedAt(null);
        return recompensadoRepository.save(recompensado);
    }

}