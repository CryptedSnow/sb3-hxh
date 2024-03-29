package com.springboot3.sb3hxh.Controller;

import com.springboot3.sb3hxh.Model.*;
import com.springboot3.sb3hxh.Service.*;
import jakarta.validation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/recompensas")
public class RecompensaController {

    private RecompensaService recompensaService;

    public RecompensaController(RecompensaService theRecompensaService){
        recompensaService = theRecompensaService;
    }

    @GetMapping("/list")
    public String listarRecompensas(Model model){
        List<RecompensaModel> recompensaModel = recompensaService.index();
        model.addAttribute("recompensas", recompensaModel);
        return "/recompensa/list-recompensas";
    }

    @GetMapping("/form-create-recompensa")
    public String formCreateRecompensa(Model model){
        RecompensaModel recompensaModel = new RecompensaModel();
        model.addAttribute("recompensa", recompensaModel);
        return "/recompensa/create-recompensa";
    }

    @PostMapping("/create-recompensa")
    public String createRecompensa(@ModelAttribute("recompensa") @Valid RecompensaModel recompensaModel, BindingResult bindingResult) {
        recompensaModel.setDescricao_recompensa(recompensaModel.getDescricao_recompensa().trim());
        System.out.println(recompensaModel);
        if (bindingResult.hasErrors()) {
            return "/recompensa/create-recompensa";
        } else {
            recompensaService.create(recompensaModel);
            return "redirect:/recompensas/list";
        }
    }

    @GetMapping("/form-update-recompensa/{id}")
    public String formUpdateRecompensa(@PathVariable("id") int id, Model model) {
        RecompensaModel recompensaModel = recompensaService.read(id);
        if (recompensaModel != null) {
            model.addAttribute("recompensa", recompensaModel);
            return "/recompensa/update-recompensa";
        } else {
            return "redirect:/recompensas/list";
        }
    }

    @PostMapping("/update-recompensa/{id}")
    public String updateRecompensa(@PathVariable("id") int id, @ModelAttribute("recompensa") @Valid RecompensaModel recompensaModel, BindingResult bindingResult) {
        recompensaModel.setDescricao_recompensa(recompensaModel.getDescricao_recompensa().trim());
        if (bindingResult.hasErrors()) {
            return "/recompensa/update-recompensa";
        } else {
            recompensaModel.setId(id);
            recompensaService.update(recompensaModel);
            return "redirect:/recompensas/list";
        }
    }

    @GetMapping("/trash-recompensa/{id}")
    public String trashRecompensa(@PathVariable("id") int id) {
        recompensaService.trash(id);
        return "redirect:/recompensas/list";
    }

    @GetMapping("/trash-list-recompensa")
    public String listarTrashRecompensas(Model model){
        List<RecompensaModel> recompensaModel = recompensaService.indexTrash();
        model.addAttribute("recompensas", recompensaModel);
        return "/recompensa/trash-recompensa";
    }

    @GetMapping("/restore-recompensa/{id}")
    public String restoreRecompensa(@PathVariable("id") int id, Model model) {
        recompensaService.restore(id);
        return "redirect:/recompensas/trash-list-recompensa";
    }

    @GetMapping("/delete-recompensa/{id}")
    public String deleteRecompensa(@PathVariable("id") int id) {
        recompensaService.delete(id);
        return "redirect:/recompensas/trash-list-recompensa";
    }

}