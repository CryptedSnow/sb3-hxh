package com.springboot3.sb3hxh.Controller;

import com.springboot3.sb3hxh.Entity.HunterEntity;
import com.springboot3.sb3hxh.Entity.RecompensaEntity;
import com.springboot3.sb3hxh.Entity.RecompensadoEntity;
import com.springboot3.sb3hxh.Service.HunterService;
import com.springboot3.sb3hxh.Service.RecompensaService;
import com.springboot3.sb3hxh.Service.RecompensadoService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/recompensados")
public class RecompensadoController {

    private static final Logger log = LoggerFactory.getLogger(RecompensadoController.class);
    private RecompensadoService recompensadoService;
    private HunterService hunterService;
    private RecompensaService recompensaService;

    public RecompensadoController(RecompensadoService theRecompensadoService, HunterService theHunterService, RecompensaService theRecompensaService){
        recompensadoService = theRecompensadoService;
        hunterService = theHunterService;
        recompensaService = theRecompensaService;
    }

    @GetMapping("/list")
    public String listarRecompensados(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        Page<RecompensadoEntity> recompensadoPage = recompensadoService.indexPagination(page, size);
        model.addAttribute("recompensados", recompensadoPage.getContent());
        model.addAttribute("currentPage", recompensadoPage.getNumber());
        model.addAttribute("totalPages", recompensadoPage.getTotalPages());
        return "/recompensado/list-recompensados";
    }

    @GetMapping("/filtrar-recompensado")
    public String filtrarRecompensado(@RequestParam(name = "search", required = false) String search, Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        Page<RecompensadoEntity> recompensadoPage = (search != null && !search.isEmpty()) ? recompensadoService.searchRecompensado(search, page, size) : recompensadoService.indexPagination(page, size);
        model.addAttribute("recompensados", recompensadoPage.getContent());
        model.addAttribute("currentPage", recompensadoPage.getNumber());
        model.addAttribute("totalPages", recompensadoPage.getTotalPages());
        model.addAttribute("search", search);
        model.addAttribute("size", size);
        return "/recompensado/list-recompensados";
    }

    @GetMapping("/form-create-recompensado")
    public String formCreateRecompensado(Model model){
        RecompensadoEntity recompesadoModel = new RecompensadoEntity();
        List<HunterEntity> hunterEntity = hunterService.index();
        List<RecompensaEntity> recompensaEntity = recompensaService.index();
        List<RecompensaEntity> recompensasDisponiveis = new ArrayList<>();
        for (RecompensaEntity recompensa : recompensaEntity) {
            boolean jaAssociado = false;
            for (RecompensadoEntity recompensado : recompensadoService.index()) {
                if (recompensado.getRecompensa_id().equals(recompensa)) {
                    jaAssociado = true;
                    break;
                }
            }
            if (!jaAssociado) {
                recompensasDisponiveis.add(recompensa);
            }
        }
        model.addAttribute("recompensado", recompesadoModel);
        model.addAttribute("hunter", hunterEntity);
        model.addAttribute("recompensa", recompensasDisponiveis);
        return "/recompensado/create-recompensado";
    }

    @PostMapping("/create-recompensado")
    public String createRecompensado(@ModelAttribute("recompensado") @Valid RecompensadoEntity recompensadoEntity, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        List<HunterEntity> hunterEntity = hunterService.index();
        List<RecompensaEntity> recompensaEntity = recompensaService.index();
        List<RecompensaEntity> recompensasDisponiveis = new ArrayList<>();
        for (RecompensaEntity recompensa : recompensaEntity) {
            boolean jaAssociado = false;
            for (RecompensadoEntity recompensado : recompensadoService.index()) {
                if (recompensado.getRecompensa_id().equals(recompensa)) {
                    jaAssociado = true;
                    break;
                }
            }
            if (!jaAssociado) {
                recompensasDisponiveis.add(recompensa);
            }
        }
        model.addAttribute("hunter", hunterEntity);
        model.addAttribute("recompensa", recompensasDisponiveis);
        if (bindingResult.hasErrors()) {
            log.warn("Erros de validações encontrados no formulário: {}", bindingResult.getAllErrors());
            return "/recompensado/create-recompensado";
        } else {
            recompensadoService.create(recompensadoEntity);
            log.info("Recompensado(a) está presente no sistema.");
            redirectAttributes.addFlashAttribute("success_store", "Recompensado está presente no sistema.");
            return "redirect:/recompensados/list?page=0&size=5";
        }
    }

    @GetMapping("/form-update-recompensado/{id}")
    public String formUpdateRecompensado(@PathVariable("id") int id, Model model) {
        RecompensadoEntity recompensadoEntity = recompensadoService.read(id);
        List<HunterEntity> hunterEntity = hunterService.index();
        List<RecompensaEntity> recompensaEntity = recompensaService.index();
        List<RecompensaEntity> recompensasDisponiveis = new ArrayList<>();
        for (RecompensaEntity recompensa : recompensaEntity) {
            boolean jaAssociado = false;
            for (RecompensadoEntity recompensado : recompensadoService.index()) {
                if (recompensado.getRecompensa_id().equals(recompensa)) {
                    jaAssociado = true;
                    break;
                }
            }
            if (!jaAssociado) {
                recompensasDisponiveis.add(recompensa);
            }
        }
        model.addAttribute("recompensado", recompensadoEntity);
        model.addAttribute("hunter", hunterEntity);
        model.addAttribute("recompensa", recompensasDisponiveis);
        return recompensadoEntity != null ? "/recompensado/update-recompensado" : "redirect:/recompensados/list?page=0&size=5";
    }

    @PostMapping("/update-recompensados/{id}")
    public String updateRecompensado(@PathVariable("id") int id, @ModelAttribute("recompensado") @Valid RecompensadoEntity recompensadoEntity, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        List<HunterEntity> hunterEntity = hunterService.index();
        List<RecompensaEntity> recompensaEntity = recompensaService.index();
        List<RecompensaEntity> recompensasDisponiveis = new ArrayList<>();
        for (RecompensaEntity recompensa : recompensaEntity) {
            boolean jaAssociado = false;
            for (RecompensadoEntity recompensado : recompensadoService.index()) {
                if (recompensado.getRecompensa_id().equals(recompensa)) {
                    jaAssociado = true;
                    break;
                }
            }
            if (!jaAssociado) {
                recompensasDisponiveis.add(recompensa);
            }
        }
        model.addAttribute("hunter", hunterEntity);
        model.addAttribute("recompensa", recompensasDisponiveis);
        if (bindingResult.hasErrors()) {
            log.warn("Erros de validações encontrados no formulário: {}", bindingResult.getAllErrors());
            return "/recompensado/update-recompensado";
        } else {
            recompensadoEntity.setId(id);
            recompensadoService.update(recompensadoEntity);
            String recompensado = recompensadoService.showRecompensado(id);
            log.info("Recompensado(a) {} obteve atualizações em suas informações.", recompensadoEntity);
            redirectAttributes.addFlashAttribute("success_update", "Recompensado foi atualizado no sistema.");
            return "redirect:/recompensados/list?page=0&size=5";
        }
    }

    @GetMapping("/trash-recompensado/{id}")
    public String trashRecompensado(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        String recompensado = recompensadoService.showRecompensado(id);
        recompensadoService.trash(id);
        log.info("Recompensado(a) {} foi enviado(a) para a lixeira.", recompensado);
        redirectAttributes.addFlashAttribute("success_delete", "Recompensado " + recompensado + " está na lixeira.");
        return "redirect:/recompensados/list?page=0&size=5";
    }

    @GetMapping("/trash-list-recompensado")
    public String listarTrashRecompensados(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        Page<RecompensadoEntity> recompensadoPage = recompensadoService.indexTrash(page, size);
        model.addAttribute("recompensados", recompensadoPage.getContent());
        model.addAttribute("currentPage", recompensadoPage.getNumber());
        model.addAttribute("totalPages", recompensadoPage.getTotalPages());
        return "/recompensado/trash-recompensado";
    }

    @GetMapping("/filtrar-recompensado-trash")
    public String filtrarRecompensadoTrash(@RequestParam(name = "search", required = false) String search, Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        Page<RecompensadoEntity> recompensadoPage = (search != null && !search.isEmpty()) ? recompensadoService.searchRecompensadoTrash(search, page, size) : recompensadoService.indexTrash(page, size);
        model.addAttribute("recompensados", recompensadoPage.getContent());
        model.addAttribute("currentPage", recompensadoPage.getNumber());
        model.addAttribute("totalPages", recompensadoPage.getTotalPages());
        model.addAttribute("search", search);
        model.addAttribute("size", size);
        return "/recompensado/trash-recompensado";
    }

    @GetMapping("/restore-recompensado/{id}")
    public String restoreRecompensado(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        String recompensado = recompensadoService.showRecompensadoTrash(id);
        recompensadoService.restore(id);
        log.info("Recompensado(a) {} foi restaurado(a) para a listagem principal.", recompensado);
        redirectAttributes.addFlashAttribute("success_store", "Recompensado " + recompensado + " foi restaurado para a listagem principal.");
        return "redirect:/recompensados/trash-list-recompensado?page=0&size=5";
    }

    @GetMapping("/delete-recompensado/{id}")
    public String deleteRecompensado(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        String recompensado = recompensadoService.showRecompensadoTrash(id);
        recompensadoService.delete(id);
        log.info("Recompensado(a) ID Nº{} foi excluído(a) permanentemente.", recompensado);
        redirectAttributes.addFlashAttribute("success_delete", "Recompensado " + recompensado + " foi excluído do sistema.");
        return "redirect:/recompensados/trash-list-recompensado?page=0&size=5";
    }

}