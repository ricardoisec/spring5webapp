package pt.ricardocabete.livros.controllers.web;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class MenuController {
    @GetMapping("/menu")
    public String menuPrincipal(Model model){
        model.addAttribute("title", "Main Menu");
        return "menu/menu";
    }
}
