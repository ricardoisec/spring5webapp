package pt.ricardocabete.livros.controllers.web;

import org.springframework.web.bind.annotation.*;
import pt.ricardocabete.livros.domain.Publisher;
import pt.ricardocabete.livros.exception.BookValidationException;
import pt.ricardocabete.livros.exception.PublisherValidationException;
import pt.ricardocabete.livros.repositories.PublisherRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import pt.ricardocabete.livros.services.PublisherService;

import java.util.NoSuchElementException;

@Controller
public class PublisherController {
    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping ("/publishers")
    public String getPublishers (Model model) {
//        var publishers = publisherRepository.findAll();
//        model.addAttribute("publishers", publishers);

        return "publishers/lista_de_publishers";
    }

    //Adicionar publisher
    @GetMapping("/publishers/show_form")
    public String formPublisher() {
        return "publishers/form_adicionar_editora";
    }

    @PostMapping("/publishers")
    public String adicionarPublisher(@ModelAttribute("publisher") Publisher publisher, Model model) {

        try {
            publisherService.createPublisher(publisher);
            model.addAttribute("publisher", publisher);

            return "publishers/publisher_adicionada_com_sucesso";
        } catch (PublisherValidationException exception) {
            model.addAttribute("errorMessage", exception.getMessage());
            return "publishers/errors/erro_criacao_editora";
        }


    }

    //editar publisher
    @GetMapping("/publishers/form_update_editora/{id}")
    public String formEdit (@PathVariable Long id, Model model) {
//        var publisher = publisherRepository.findById(id).orElse(null);
//        model.addAttribute("publisher", publisher);
        return "publishers/form_update_editora";
    }

    @PostMapping("publishers/{id}")
    public String editarPublisher(@ModelAttribute("publisher") Publisher publisher, @PathVariable Long id, Model model) {
        try {
            publisherService.updatePublisher(publisher, id);

            return "redirect:/publishers";
        } catch (PublisherValidationException | NoSuchElementException exception) {
            model.addAttribute("errorMessage", exception.getMessage());
            return "publishers/errors/erro_edicao_editora";
        }


    }

    //Apagar
    @GetMapping("/apagar_editora/{id}")
    public String deletePublisher(@PathVariable("id") long id, Model model) {
//        publisherRepository.deleteById(id);
        return "redirect:/publishers";
    }

}
