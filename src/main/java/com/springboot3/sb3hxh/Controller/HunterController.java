package com.springboot3.sb3hxh.Controller;

import com.springboot3.sb3hxh.Entity.HunterEntity;
import com.springboot3.sb3hxh.Entity.TipoHunterEntity;
import com.springboot3.sb3hxh.Entity.TipoNenEntity;
import com.springboot3.sb3hxh.Entity.TipoSanguineoEntity;
import com.springboot3.sb3hxh.Service.HunterService;
import com.springboot3.sb3hxh.Service.TipoHunterService;
import com.springboot3.sb3hxh.Service.TipoNenService;
import com.springboot3.sb3hxh.Service.TipoSanguineoService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/hunters")
public class HunterController {

    private static final Logger log = LoggerFactory.getLogger(HunterController.class);
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
    public String listarHunters(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        Page<HunterEntity> hunterPage = hunterService.indexPagination(page, size);
        model.addAttribute("hunters", hunterPage.getContent());
        model.addAttribute("currentPage", hunterPage.getNumber());
        model.addAttribute("totalPages", hunterPage.getTotalPages());
        return "/hunter/list-hunters";
    }

    @GetMapping("/filtrar-hunter")
    public String filtrarHunter(@RequestParam(name = "search", required = false) String search, Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        Page<HunterEntity> hunterPage = (search != null && !search.isEmpty()) ? hunterService.searchHunter(search, page, size) : hunterService.indexPagination(page, size);
        model.addAttribute("hunters", hunterPage.getContent());
        model.addAttribute("currentPage", hunterPage.getNumber());
        model.addAttribute("totalPages", hunterPage.getTotalPages());
        model.addAttribute("search", search);
        model.addAttribute("size", size);
        return "/hunter/list-hunters";
    }

    @GetMapping("/form-create-hunter")
    public String formCreateHunter(Model model){
        HunterEntity hunterEntity = new HunterEntity();
        List<TipoHunterEntity> tipoHunterEntity = tipoHunterService.index();
        List<TipoNenEntity> tipoNenEntity = tipoNenService.index();
        List<TipoSanguineoEntity> tipoSanguineoEntity = tipoSanguineoService.index();
        model.addAttribute("hunter", hunterEntity);
        model.addAttribute("tipo_hunter", tipoHunterEntity);
        model.addAttribute("tipo_nen", tipoNenEntity);
        model.addAttribute("tipo_sanguineo", tipoSanguineoEntity);
        return "/hunter/create-hunter";
    }

    @PostMapping("/create-hunter")
    public String createHunter(@ModelAttribute("hunter") @Valid HunterEntity hunterEntity, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        List<TipoHunterEntity> tipoHunterEntity = tipoHunterService.index();
        List<TipoNenEntity> tipoNenEntity = tipoNenService.index();
        List<TipoSanguineoEntity> tipoSanguineoEntity = tipoSanguineoService.index();
        model.addAttribute("tipo_hunter", tipoHunterEntity);
        model.addAttribute("tipo_nen", tipoNenEntity);
        model.addAttribute("tipo_sanguineo", tipoSanguineoEntity);
        if (bindingResult.hasErrors()) {
            log.warn("Erros de validações encontrados no formulário: {}", bindingResult.getAllErrors());
            return "/hunter/create-hunter";
        } else {
            hunterService.create(hunterEntity);
            String nome = hunterEntity.getNomeHunter();
            log.info("Hunter {} está presente no sistema.", nome);
            redirectAttributes.addFlashAttribute("success_store", "Hunter " + nome + " está presente no sistema.");
            return "redirect:/hunters/list?page=0&size=5";
        }
    }

    @GetMapping("/form-update-hunter/{id}")
    public String formUpdateHunter(@PathVariable("id") int id, Model model) {
        HunterEntity hunterEntity = hunterService.read(id);
        List<TipoHunterEntity> tipoHunterEntity = tipoHunterService.index();
        List<TipoNenEntity> tipoNenEntity = tipoNenService.index();
        List<TipoSanguineoEntity> tipoSanguineoEntity = tipoSanguineoService.index();
        model.addAttribute("hunter", hunterEntity);
        model.addAttribute("tipo_hunter", tipoHunterEntity);
        model.addAttribute("tipo_nen", tipoNenEntity);
        model.addAttribute("tipo_sanguineo", tipoSanguineoEntity);
        return hunterEntity != null ? "/hunter/update-hunter" : "redirect:/hunters/list?page=0&size=5";
    }

    @PostMapping("/update-hunter/{id}")
    public String updateHunter(@PathVariable("id") int id, @ModelAttribute("hunter") @Valid HunterEntity hunterEntity, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        List<TipoHunterEntity> tipoHunterEntity = tipoHunterService.index();
        List<TipoNenEntity> tipoNenEntity = tipoNenService.index();
        List<TipoSanguineoEntity> tipoSanguineoEntity = tipoSanguineoService.index();
        model.addAttribute("tipo_hunter", tipoHunterEntity);
        model.addAttribute("tipo_nen", tipoNenEntity);
        model.addAttribute("tipo_sanguineo", tipoSanguineoEntity);
        if (bindingResult.hasErrors()) {
            log.warn("Erros de validações encontrados no formulário: {}", bindingResult.getAllErrors());
            return "/hunter/update-hunter";
        } else {
            hunterEntity.setId(id);
            hunterService.update(hunterEntity);
            String nome = hunterEntity.getNomeHunter();
            log.info("Hunter {} obteve atualizações em suas informações.", nome);
            redirectAttributes.addFlashAttribute("success_update", "Hunter" + nome + " foi atualizado no sistema.");
            return "redirect:/hunters/list?page=0&size=5";
        }
    }

    @GetMapping("/trash-hunter/{id}")
    public String trashHunter(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        HunterEntity hunterEntity = hunterService.read(id);
        String nome = hunterEntity.getNomeHunter();
        hunterService.trash(id);
        log.info("Hunter {} foi enviado(a) para a lixeira.", nome);
        redirectAttributes.addFlashAttribute("success_delete", "Hunter " + nome + " está na lixeira.");
        return "redirect:/hunters/list?page=0&size=5";
    }

    @GetMapping("/trash-list-hunter")
    public String listarTrashHunters(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        Page<HunterEntity> hunterPage = hunterService.indexTrash(page, size);
        model.addAttribute("hunters", hunterPage.getContent());
        model.addAttribute("currentPage", hunterPage.getNumber());
        model.addAttribute("totalPages", hunterPage.getTotalPages());
        return "/hunter/trash-hunter";
    }

    @GetMapping("/filtrar-hunter-trash")
    public String filtrarHunterTrash(@RequestParam(name = "search", required = false) String search, Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        Page<HunterEntity> hunterPage = (search != null && !search.isEmpty()) ? hunterService.searchHunterTrash(search, page, size) : hunterService.indexTrash(page, size);
        model.addAttribute("hunters", hunterPage.getContent());
        model.addAttribute("currentPage", hunterPage.getNumber());
        model.addAttribute("totalPages", hunterPage.getTotalPages());
        model.addAttribute("search", search);
        model.addAttribute("size", size);
        return "/hunter/trash-hunter";
    }

    @GetMapping("/restore-hunter/{id}")
    public String restoreHunter(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        HunterEntity hunterEntity = hunterService.read(id);
        String nome = hunterEntity.getNomeHunter();
        hunterService.restore(id);
        log.info("Hunter {} foi restaurado(a) para a listagem principal.", nome);
        redirectAttributes.addFlashAttribute("success_store", "Hunter " + nome + " foi restaurado para a listagem principal.");
        return "redirect:/hunters/trash-list-hunter?page=0&size=5";
    }

    @GetMapping("/delete-hunter/{id}")
    public String deleteHunter(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        HunterEntity hunterEntity = hunterService.read(id);
        String nome = hunterEntity.getNomeHunter();
        hunterService.delete(id);
        log.info("Hunter {} foi excluído(a) permanentemente.", nome);
        redirectAttributes.addFlashAttribute("success_delete", "Hunter " + nome + " foi excluído do sistema.");
        return "redirect:/hunters/trash-list-hunter?page=0&size=5";
    }

}