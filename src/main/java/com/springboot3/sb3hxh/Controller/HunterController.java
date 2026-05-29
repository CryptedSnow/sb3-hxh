package com.springboot3.sb3hxh.Controller;

import com.springboot3.sb3hxh.Entity.Hunter;
import com.springboot3.sb3hxh.Enum.TipoHunterEnum;
import com.springboot3.sb3hxh.Enum.TipoNenEnum;
import com.springboot3.sb3hxh.Enum.TipoSanguineoEnum;
import com.springboot3.sb3hxh.Service.HunterService;
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
@RequestMapping("/hunters")
public class HunterController {

    private static final Logger log = LoggerFactory.getLogger(HunterController.class);
    private HunterService hunterService;

    public HunterController(HunterService theHunterService){
        hunterService = theHunterService;
    }

    @GetMapping("/list-hunters")
    public String listHunters(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        Page<Hunter> hunterPage = hunterService.indexPagination(page, size);
        model.addAttribute("hunters", hunterPage.getContent());
        model.addAttribute("currentPage", hunterPage.getNumber());
        model.addAttribute("totalPages", hunterPage.getTotalPages());
        return "/hunter/list-hunters";
    }

    @GetMapping("/search-hunter")
    public String filtrarHunter(@RequestParam(name = "search", required = false) String search, Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        Page<Hunter> hunterPage = (search != null && !search.isEmpty()) ? hunterService.searchHunter(search, page, size) : hunterService.indexPagination(page, size);
        model.addAttribute("hunters", hunterPage.getContent());
        model.addAttribute("currentPage", hunterPage.getNumber());
        model.addAttribute("totalPages", hunterPage.getTotalPages());
        model.addAttribute("search", search);
        model.addAttribute("size", size);
        return "/hunter/list-hunters";
    }

    @GetMapping("/form-create-hunter")
    public String formCreateHunter(Model model){
        Hunter hunterEntity = new Hunter();
        model.addAttribute("hunter", hunterEntity);
        model.addAttribute("tipo_hunter", TipoHunterEnum.values());
        model.addAttribute("tipo_nen", TipoNenEnum.values());
        model.addAttribute("tipo_sanguineo", TipoSanguineoEnum.values());
        return "/hunter/create-hunter";
    }

    @PostMapping("/create-hunter")
    public String createHunter(@ModelAttribute("hunter") @Valid Hunter hunterEntity, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        model.addAttribute("tipo_hunter", TipoHunterEnum.values());
        model.addAttribute("tipo_nen", TipoNenEnum.values());
        model.addAttribute("tipo_sanguineo", TipoSanguineoEnum.values());
        if (bindingResult.hasErrors()) {
            log.warn("Erros de validações encontrados no formulário: {}", bindingResult.getAllErrors());
            return "/hunter/create-hunter";
        } else {
            hunterService.create(hunterEntity);
            String nome = hunterEntity.getNomeHunter();
            log.info("Hunter {} está presente no sistema.", nome);
            redirectAttributes.addFlashAttribute("success_store", "Hunter " + nome + " está presente no sistema.");
            return "redirect:/hunters/list-hunters?page=0&size=5";
        }
    }

    @GetMapping("/form-update-hunter/{id}")
    public String formUpdateHunter(@PathVariable("id") int id, Model model) {
        Hunter hunterEntity = hunterService.read(id);
        model.addAttribute("hunter", hunterEntity);
        model.addAttribute("tipo_hunter", TipoHunterEnum.values());
        model.addAttribute("tipo_nen", TipoNenEnum.values());
        model.addAttribute("tipo_sanguineo", TipoSanguineoEnum.values());
        return hunterEntity != null ? "/hunter/update-hunter" : "redirect:/hunters/list-hunters?page=0&size=5";
    }

    @PostMapping("/update-hunter/{id}")
    public String updateHunter(@PathVariable("id") int id, @ModelAttribute("hunter") @Valid Hunter hunterEntity, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            log.warn("Erros de validações encontrados no formulário: {}", bindingResult.getAllErrors());
            return "/hunter/update-hunter";
        } else {
            hunterEntity.setId(id);
            hunterService.update(hunterEntity);
            String nome = hunterEntity.getNomeHunter();
            log.info("Hunter {} obteve atualizações em suas informações.", nome);
            redirectAttributes.addFlashAttribute("success_update", "Hunter " + nome + " foi atualizado no sistema.");
            return "redirect:/hunters/list-hunters?page=0&size=5";
        }
    }

    @GetMapping("/trash-hunter/{id}")
    public String trashHunter(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        Hunter hunterEntity = hunterService.read(id);
        String nome = hunterEntity.getNomeHunter();
        hunterService.trash(id);
        log.info("Hunter {} foi enviado(a) para a lixeira.", nome);
        redirectAttributes.addFlashAttribute("success_delete", "Hunter " + nome + " está na lixeira.");
        return "redirect:/hunters/list-hunters?page=0&size=5";
    }

    @GetMapping("/trash-list-hunter")
    public String listTrashHunters(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        Page<Hunter> hunterPage = hunterService.indexTrash(page, size);
        model.addAttribute("hunters", hunterPage.getContent());
        model.addAttribute("currentPage", hunterPage.getNumber());
        model.addAttribute("totalPages", hunterPage.getTotalPages());
        return "/hunter/trash-hunter";
    }

    @GetMapping("/search-hunter-trash")
    public String searchHunterTrash(@RequestParam(name = "search", required = false) String search, Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        Page<Hunter> hunterPage = (search != null && !search.isEmpty()) ? hunterService.searchHunterTrash(search, page, size) : hunterService.indexTrash(page, size);
        model.addAttribute("hunters", hunterPage.getContent());
        model.addAttribute("currentPage", hunterPage.getNumber());
        model.addAttribute("totalPages", hunterPage.getTotalPages());
        model.addAttribute("search", search);
        model.addAttribute("size", size);
        return "/hunter/trash-hunter";
    }

    @GetMapping("/restore-hunter/{id}")
    public String restoreHunter(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        Hunter hunterEntity = hunterService.read(id);
        String nome = hunterEntity.getNomeHunter();
        hunterService.restore(id);
        log.info("Hunter {} foi restaurado(a) para a listagem principal.", nome);
        redirectAttributes.addFlashAttribute("success_store", "Hunter " + nome + " foi restaurado para a listagem principal.");
        return "redirect:/hunters/trash-list-hunter?page=0&size=5";
    }

}