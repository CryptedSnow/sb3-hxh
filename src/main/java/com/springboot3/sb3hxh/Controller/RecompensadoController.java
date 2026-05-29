package com.springboot3.sb3hxh.Controller;

import com.springboot3.sb3hxh.Entity.Hunter;
import com.springboot3.sb3hxh.Entity.Recompensa;
import com.springboot3.sb3hxh.Entity.Recompensado;
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

    @GetMapping("/list-recompensados")
    public String listRecompensados(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        Page<Recompensado> recompensadoPage = recompensadoService.indexPagination(page, size);
        model.addAttribute("recompensados", recompensadoPage.getContent());
        model.addAttribute("currentPage", recompensadoPage.getNumber());
        model.addAttribute("totalPages", recompensadoPage.getTotalPages());
        return "/recompensado/list-recompensados";
    }

    @GetMapping("/search-recompensado")
    public String searchRecompensado(@RequestParam(name = "search", required = false) String search, Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        Page<Recompensado> recompensadoPage = (search != null && !search.isEmpty()) ? recompensadoService.searchRecompensado(search, page, size) : recompensadoService.indexPagination(page, size);
        model.addAttribute("recompensados", recompensadoPage.getContent());
        model.addAttribute("currentPage", recompensadoPage.getNumber());
        model.addAttribute("totalPages", recompensadoPage.getTotalPages());
        model.addAttribute("search", search);
        model.addAttribute("size", size);
        return "/recompensado/list-recompensados";
    }

    @GetMapping("/form-create-recompensado")
    public String formCreateRecompensado(Model model){
        Recompensado recompesadoModel = new Recompensado();
        List<Hunter> hunterEntity = hunterService.index();
        List<Recompensa> recompensaEntity = recompensaService.index();
        List<Recompensa> recompensasDisponiveis = new ArrayList<>();
        for (Recompensa recompensa : recompensaEntity) {
            boolean jaAssociado = false;
            for (Recompensado recompensado : recompensadoService.index()) {
                if (recompensado.getRecompensaId().equals(recompensa)) {
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
    public String createRecompensado(@ModelAttribute("recompensado") @Valid Recompensado recompensadoEntity, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        List<Hunter> hunterEntity = hunterService.index();
        List<Recompensa> recompensaEntity = recompensaService.index();
        List<Recompensa> recompensasDisponiveis = new ArrayList<>();
        for (Recompensa recompensa : recompensaEntity) {
            boolean jaAssociado = false;
            for (Recompensado recompensado : recompensadoService.index()) {
                if (recompensado.getRecompensaId().equals(recompensa)) {
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
            String nomeHunter = recompensadoEntity.getHunterId().getNomeHunter();
            log.info("Recompensado(a) {} foi registrado(a) no sistema com sucesso.", nomeHunter);
            redirectAttributes.addFlashAttribute("success_store", "Recompensado(a) " + nomeHunter + " foi registrado(a) com sucesso.");
            return "redirect:/recompensados/list-recompensados?page=0&size=5";
        }
    }

    @GetMapping("/trash-recompensado/{id}")
    public String trashRecompensado(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        Recompensado recompensado = recompensadoService.read(id); // ✅
        recompensadoService.trash(id);
        log.info("Recompensado(a) {} foi enviado(a) para a lixeira.", recompensado.getHunterId().getNomeHunter());
        redirectAttributes.addFlashAttribute("success_delete", "Recompensado(a) " + recompensado.getHunterId().getNomeHunter() + " está na lixeira.");
        return "redirect:/recompensados/list-recompensados?page=0&size=5";
    }

    @GetMapping("/trash-list-recompensado")
    public String listTrashRecompensados(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        Page<Recompensado> recompensadoPage = recompensadoService.indexTrash(page, size);
        model.addAttribute("recompensados", recompensadoPage.getContent());
        model.addAttribute("currentPage", recompensadoPage.getNumber());
        model.addAttribute("totalPages", recompensadoPage.getTotalPages());
        return "/recompensado/trash-recompensado";
    }

    @GetMapping("/search-recompensado-trash")
    public String searchRecompensadoTrash(@RequestParam(name = "search", required = false) String search, Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        Page<Recompensado> recompensadoPage = (search != null && !search.isEmpty()) ? recompensadoService.searchRecompensadoTrash(search, page, size) : recompensadoService.indexTrash(page, size);
        model.addAttribute("recompensados", recompensadoPage.getContent());
        model.addAttribute("currentPage", recompensadoPage.getNumber());
        model.addAttribute("totalPages", recompensadoPage.getTotalPages());
        model.addAttribute("search", search);
        model.addAttribute("size", size);
        return "/recompensado/trash-recompensado";
    }

    @GetMapping("/restore-recompensado/{id}")
    public String restoreRecompensado(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        Recompensado recompensado = recompensadoService.read(id); // ✅
        recompensadoService.restore(id);
        log.info("Recompensado(a) {} foi restaurado(a) para a listagem principal.", recompensado.getHunterId().getNomeHunter());
        redirectAttributes.addFlashAttribute("success_store", "Recompensado(a) " + recompensado.getHunterId().getNomeHunter() + " foi restaurado(a) para a listagem principal.");
        return "redirect:/recompensados/trash-list-recompensado?page=0&size=5";
    }

}