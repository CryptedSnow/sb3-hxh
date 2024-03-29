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
@RequestMapping("/recompensados")
public class RecompensadoController {

    private RecompensadoService recompensadoService;
    private HunterService hunterService;
    private RecompensaService recompensaService;

    public RecompensadoController(RecompensadoService theRecompensadoService, HunterService theHunterService, RecompensaService theRecompensaService){
        recompensadoService = theRecompensadoService;
        hunterService = theHunterService;
        recompensaService = theRecompensaService;
    }

    @GetMapping("/list")
    public String listarRecompensados(Model model){
        List<RecompensadoModel> recompensadoModel = recompensadoService.index();
        model.addAttribute("recompensados", recompensadoModel);
        return "/recompensado/list-recompensados";
    }

    @GetMapping("/form-create-recompensado")
    public String formCreateRecompensado(Model model){
        RecompensadoModel recompesadoModel = new RecompensadoModel();
        List<HunterModel> hunterModel = hunterService.index();
        List<RecompensaModel> recompensaModel = recompensaService.index();
        model.addAttribute("recompensado", recompesadoModel);
        model.addAttribute("hunter", hunterModel);
        model.addAttribute("recompensa", recompensaModel);
        return "/recompensado/create-recompensado";
    }

    @PostMapping("/create-recompensado")
    public String createRecompensado(@ModelAttribute("recompensado") @Valid RecompensadoModel recompensadoModel, BindingResult bindingResult) {
        System.out.println(recompensadoModel);
        if (bindingResult.hasErrors()) {
            return "/recompensado/create-recompensado";
        } else {
            recompensadoService.create(recompensadoModel);
            return "redirect:/recompensados/recompensado";
        }
    }

    @GetMapping("/form-update-recompensado/{id}")
    public String formUpdateRecompensado(@PathVariable("id") int id, Model model) {
        RecompensadoModel recompensadoModel = recompensadoService.read(id);
        List<HunterModel> hunterModel = hunterService.index();
        List<RecompensaModel> recompensaModel = recompensaService.index();
        if (recompensadoModel != null) {
            model.addAttribute("recompensado", recompensadoModel);
            model.addAttribute("hunter", hunterModel);
            model.addAttribute("recompensa", recompensaModel);
            return "/recompensado/update-recompensado";
        } else {
            return "redirect:/recompensados/list";
        }
    }

    @PostMapping("/update-recompensados/{id}")
    public String updateRecompensado(@PathVariable("id") int id, @ModelAttribute("recompensado") @Valid RecompensadoModel recompensadoModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/recompensado/update-recompensado";
        } else {
            recompensadoModel.setId(id);
            recompensadoService.update(recompensadoModel);
            return "redirect:/recompensados/list";
        }
    }

    @GetMapping("/trash-recompensados/{id}")
    public String trashRecompensado(@PathVariable("id") int id) {
        recompensadoService.trash(id);
        return "redirect:/recompensados/list";
    }

    @GetMapping("/trash-list-recompensado")
    public String listarTrashRecompensados(Model model){
        List<RecompensadoModel> recompensadoModel = recompensadoService.indexTrash();
        model.addAttribute("recompensados", recompensadoModel);
        return "/recompensado/trash-recompensado";
    }

    @GetMapping("/restore-recompensado/{id}")
    public String restoreRecompensado(@PathVariable("id") int id, Model model) {
        recompensadoService.restore(id);
        return "redirect:/recompensados/trash-list-recompensado";
    }

    @GetMapping("/delete-recompensado/{id}")
    public String deleteRecompensado(@PathVariable("id") int id) {
        recompensadoService.delete(id);
        return "redirect:/recompensados/trash-list-recompensado";
    }

}