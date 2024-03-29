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
@RequestMapping("/hunters")
public class HunterController {

    private HunterService hunterService;
    private TipoHunterService tipoHunterService;
    private TipoSanguineoService tipoSanguineoService;
    private TipoNenService tipoNenService;

    public HunterController(HunterService theHunterService, TipoHunterService theTipoHunterService, TipoNenService theTipoNenService, TipoSanguineoService theTipoSanguineoService){
        hunterService = theHunterService;
        tipoHunterService = theTipoHunterService;
        tipoNenService = theTipoNenService;
        tipoSanguineoService = theTipoSanguineoService;
    }

    @GetMapping("/list")
    public String listarHunters(Model model){
        List<HunterModel> hunterModel = hunterService.index();
        model.addAttribute("hunters", hunterModel);
        return "/hunter/list-hunters";
    }

    @GetMapping("/form-create-hunter")
    public String formCreateHunter(Model model){
        HunterModel hunterModel = new HunterModel();
        List<TipoHunterModel> tipoHunterModel = tipoHunterService.index();
        List<TipoNenModel> tipoNenModel = tipoNenService.index();
        List<TipoSanguineoModel> tipoSanguineoModel = tipoSanguineoService.index();
        model.addAttribute("hunter", hunterModel);
        model.addAttribute("tipo_hunter", tipoHunterModel);
        model.addAttribute("tipo_nen", tipoNenModel);
        model.addAttribute("tipo_sanguineo", tipoSanguineoModel);
        return "/hunter/create-hunter";
    }

    @PostMapping("/create-hunter")
    public String createHunter(@ModelAttribute("hunter") @Valid HunterModel hunterModel, BindingResult bindingResult) {
        System.out.println(hunterModel);
        if (bindingResult.hasErrors()) {
            return "/hunter/create-hunter";
        } else {
            hunterService.create(hunterModel);
            return "redirect:/hunters/list";
        }
    }

    @GetMapping("/form-update-hunter/{id}")
    public String formUpdateHunter(@PathVariable("id") int id, Model model) {
        HunterModel hunterModel = hunterService.read(id);
        List<TipoHunterModel> tipoHunterModel = tipoHunterService.index();
        List<TipoNenModel> tipoNenModel = tipoNenService.index();
        List<TipoSanguineoModel> tipoSanguineoModel = tipoSanguineoService.index();
        if (hunterModel != null) {
            model.addAttribute("hunter", hunterModel);
            model.addAttribute("tipo_hunter", tipoHunterModel);
            model.addAttribute("tipo_nen", tipoNenModel);
            model.addAttribute("tipo_sanguineo", tipoSanguineoModel);
            return "/hunter/update-hunter";
        } else {
            return "redirect:/hunters/list";
        }
    }

    @PostMapping("/update-hunter/{id}")
    public String updateHunter(@PathVariable("id") int id, @ModelAttribute("hunter") @Valid HunterModel hunterModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/hunter/update-hunter";
        } else {
            hunterModel.setId(id);
            hunterService.update(hunterModel);
            return "redirect:/hunters/list";
        }
    }

    @GetMapping("/trash-hunter/{id}")
    public String trashHunter(@PathVariable("id") int id) {
        hunterService.trash(id);
        return "redirect:/hunters/list";
    }

    @GetMapping("/trash-list-hunter")
    public String listarTrashHunters(Model model){
        List<HunterModel> hunterModel = hunterService.indexTrash();
        model.addAttribute("hunters", hunterModel);
        return "/hunter/trash-hunter";
    }

    @GetMapping("/restore-hunter/{id}")
    public String restoreHunter(@PathVariable("id") int id, Model model) {
        hunterService.restore(id);
        return "redirect:/hunters/trash-list-hunter";
    }

    @GetMapping("/delete-hunter/{id}")
    public String deleteHunter(@PathVariable("id") int id) {
        hunterService.delete(id);
        return "redirect:/hunters/trash-list-hunter";
    }

}