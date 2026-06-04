package com.springboot3.sb3hxh.Service;

import com.springboot3.sb3hxh.Repository.HunterRepository;
import com.springboot3.sb3hxh.Entity.Hunter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HunterService {

    private final HunterRepository hunterRepository;

    public HunterService(HunterRepository hunterRepository) {
        this.hunterRepository = hunterRepository;
    }

    public List<Hunter> indexHunters() {
        return hunterRepository.findByDeletedAtIsNullOrderByIdAsc();
    }

    public Page<Hunter> indexHuntersPagination(int page, int size) {
        return hunterRepository.findByDeletedAtIsNullOrderByIdAsc(PageRequest.of(page, size));
    }

    public Page<Hunter> searchHunters(String search, int page, int size) {
        return hunterRepository.searchAtivos(search, PageRequest.of(page, size));
    }

    public Hunter createHunter(Hunter hunter) {
        return hunterRepository.save(hunter);
    }

    public Hunter findIdHunter(int id) {
        return hunterRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Hunter ID " + id + " não encontrado."));
    }

    public Hunter updateHunter(Hunter hunter) {
        return hunterRepository.save(hunter);
    }

    public void deleteHunterToTrash(int id) {
        Hunter hunter = findIdHunter(id);
        hunter.setDeletedAt(LocalDateTime.now());
        hunterRepository.save(hunter);
    }

    public Page<Hunter> indexHuntersTrash(int page, int size) {
        return hunterRepository.findByDeletedAtIsNotNullOrderByIdAsc(PageRequest.of(page, size));
    }

    public Page<Hunter> searchHuntersTrash(String search, int page, int size) {
        return hunterRepository.searchLixeira(search, PageRequest.of(page, size));
    }

    public Hunter restoreHunter(int id) {
        Hunter hunter = findIdHunter(id);
        hunter.setDeletedAt(null);
        return hunterRepository.save(hunter);
    }

}