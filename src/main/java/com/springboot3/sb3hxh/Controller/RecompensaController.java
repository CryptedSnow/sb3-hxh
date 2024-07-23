package com.springboot3.sb3hxh.Controller;

import com.springboot3.sb3hxh.Entity.RecompensaEntity;
import com.springboot3.sb3hxh.Service.RecompensaService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/recompensas")
public class RecompensaController {

    private static final Logger log = LoggerFactory.getLogger(RecompensaController.class);
    private RecompensaService recompensaService;

    public RecompensaController(RecompensaService theRecompensaService){
        recompensaService = theRecompensaService;
    }

    @GetMapping("/list")
    public String listarRecompensas(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        Page<RecompensaEntity> recompensaPage = recompensaService.indexPagination(page, size);
        model.addAttribute("recompensas", recompensaPage.getContent());
        model.addAttribute("currentPage", recompensaPage.getNumber());
        model.addAttribute("totalPages", recompensaPage.getTotalPages());
        return "/recompensa/list-recompensas";
    }

    @GetMapping("/filtrar-recompensa")
    public String filtrarRecompensa(@RequestParam(name = "search", required = false) String search, Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        Page<RecompensaEntity> recompensaPage = (search != null && !search.isEmpty()) ? recompensaService.searchRecompensa(search, page, size) : recompensaService.indexPagination(page, size);
        model.addAttribute("recompensas", recompensaPage.getContent());
        model.addAttribute("currentPage", recompensaPage.getNumber());
        model.addAttribute("totalPages", recompensaPage.getTotalPages());
        model.addAttribute("search", search);
        model.addAttribute("size", size);
        return "/recompensa/list-recompensas";
    }

    @GetMapping("/form-create-recompensa")
    public String formCreateRecompensa(Model model){
        RecompensaEntity recompensaEntity = new RecompensaEntity();
        model.addAttribute("recompensa", recompensaEntity);
        return "/recompensa/create-recompensa";
    }

    @PostMapping("/create-recompensa")
    public String createRecompensa(@ModelAttribute("recompensa") @Valid RecompensaEntity recompensaEntity, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        System.out.println(recompensaEntity);
        if (bindingResult.hasErrors()) {
            log.warn("Erros de validações encontrados no formulário: {}", bindingResult.getAllErrors());
            return "/recompensa/create-recompensa";
        } else {
            recompensaService.create(recompensaEntity);
            String descricao = recompensaEntity.getDescricaoRecompensa();
            log.info("Recompensa {} está presente no sistema", descricao);
            redirectAttributes.addFlashAttribute("success_store", "Recompensa " + descricao + " está presente no sistema.");
            return "redirect:/recompensas/list?page=0&size=5";
        }
    }

    @GetMapping("/form-update-recompensa/{id}")
    public String formUpdateRecompensa(@PathVariable("id") int id, Model model) {
        RecompensaEntity recompensaEntity = recompensaService.read(id);
        if (recompensaEntity != null) {
            model.addAttribute("recompensa", recompensaEntity);
            return "/recompensa/update-recompensa";
        } else {
            return "redirect:/recompensas/list?page=0&size=5";
        }
    }

    @PostMapping("/update-recompensa/{id}")
    public String updateRecompensa(@PathVariable("id") int id, @ModelAttribute("recompensa") @Valid RecompensaEntity recompensaEntity, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            log.warn("Erros de validações encontrados no formulário: {}", bindingResult.getAllErrors());
            return "/recompensa/update-recompensa";
        } else {
            recompensaEntity.setId(id);
            recompensaService.update(recompensaEntity);
            String descricao = recompensaEntity.getDescricaoRecompensa();
            log.info("Recompensa {} obteve atualizações em suas informações.", descricao);
            redirectAttributes.addFlashAttribute("success_update", "Recompensa " + descricao + " foi atualizada no sistema.");
            return "redirect:/recompensas/list?page=0&size=5";
        }
    }

    @GetMapping("/trash-recompensa/{id}")
    public String trashRecompensa(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        RecompensaEntity recompensaEntity = recompensaService.read(id);
        String descricao = recompensaEntity.getDescricaoRecompensa();
        recompensaService.trash(id);
        log.info("Recompensa {} foi enviada para a lixeira.", descricao);
        redirectAttributes.addFlashAttribute("success_delete", "Recompensa " + descricao + " está na lixeira.");
        return "redirect:/recompensas/list?page=0&size=5";
    }

    @GetMapping("/trash-list-recompensa")
    public String listarTrashRecompensas(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        Page<RecompensaEntity> recompensaPage = recompensaService.indexTrash(page, size);
        model.addAttribute("recompensas", recompensaPage.getContent());
        model.addAttribute("currentPage", recompensaPage.getNumber());
        model.addAttribute("totalPages", recompensaPage.getTotalPages());
        return "/recompensa/trash-recompensa";
    }

    @GetMapping("/filtrar-recompensa-trash")
    public String filtrarRecompensaTrash(@RequestParam(name = "search", required = false) String search, Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        Page<RecompensaEntity> recompensaPage = (search != null && !search.isEmpty()) ? recompensaService.searchRecompensaTrash(search, page, size) : recompensaService.indexTrash(page, size);
        model.addAttribute("recompensas", recompensaPage.getContent());
        model.addAttribute("currentPage", recompensaPage.getNumber());
        model.addAttribute("totalPages", recompensaPage.getTotalPages());
        model.addAttribute("search", search);
        model.addAttribute("size", size);
        return "/recompensa/trash-recompensa";
    }

    @GetMapping("/restore-recompensa/{id}")
    public String restoreRecompensa(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        RecompensaEntity recompensaEntity = recompensaService.read(id);
        String descricao = recompensaEntity.getDescricaoRecompensa();
        recompensaService.restore(id);
        log.info("Recompensa {} foi restaurada para a listagem principal.", descricao);
        redirectAttributes.addFlashAttribute("success_store", "Recompensa " + descricao + " foi restaurada para a listagem principal.");
        return "redirect:/recompensas/trash-list-recompensa?page=0&size=5";
    }

    @GetMapping("/delete-recompensa/{id}")
    public String deleteRecompensa(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        RecompensaEntity recompensaEntity = recompensaService.read(id);
        String descricao = recompensaEntity.getDescricaoRecompensa();
        recompensaService.delete(id);
        log.info("Recompensa {} foi excluída permanentemente.", descricao);
        redirectAttributes.addFlashAttribute("success_delete", "Recompensa " + descricao + " foi excluída do sistema.");
        return "redirect:/recompensas/trash-list-recompensa?page=0&size=5";
    }

}