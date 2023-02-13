package pt.ricardocabete.livros.controllers.web;

import org.springframework.web.bind.annotation.*;
import pt.ricardocabete.livros.domain.Publisher;
import pt.ricardocabete.livros.repositories.PublisherRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class PublisherController {
    private final PublisherRepository publisherRepository;

    public PublisherController(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    @RequestMapping ("/publishers")
    public String getPublishers (Model model) {
        var publishers = publisherRepository.findAll();
        model.addAttribute("publishers", publishers);

        return "publishers/lista_de_publishers";
    }

    //Adicionar publisher
    @GetMapping("/publishers/show_form")
    public String formPublisher() {
        return "publishers/form_adicionar_editora";
    }

    @PostMapping("/publishers")
    public String adicionarPublisher(@ModelAttribute("publisher") Publisher publisher, Model model) {
        if (publisher.getName() == null || publisher.getAddress() == null || publisher.getCity() == null || publisher.getCity() == null || publisher.getZip() == null) {
            model.addAttribute("errorMessage", "None of the publisher fields can be empty or null");
            return "publishers/errors/erro_criacao_editora";
        }

        if (publisher.getName().length() < 3) {
            model.addAttribute("errorMessage",  "Publisher name must have at least 3 characters");
            return "publishers/errors/erro_criacao_editora";
        }

        if (publisher.getAddress().length() < 5){
            model.addAttribute("errorMessage",  "Adress must have at least 5 characters");
            return "publishers/errors/erro_criacao_editora";
        }

        if (publisher.getCity().length() < 3) {
            model.addAttribute("errorMessage",  "City must have at least 3 characters");
            return "publishers/errors/erro_criacao_editora";
        }

        if (publisher.getDistrict().length() < 5){
            model.addAttribute("errorMessage",  "District must have at least 5 characters");
            return "publishers/errors/erro_criacao_editora";
        }

        //validar zip-code
        String zipCode = ("^\\d{4}-\\d{3}$");
        Pattern pattern = Pattern.compile(zipCode);
        Matcher matcher = pattern.matcher(zipCode);

        if (publisher.getZip().length() == 7) {
            zipCode = zipCode.substring(0, 3) + "-" + zipCode.substring(3);
        }

        if (!matcher.matches()){
            model.addAttribute("errorMessage", "Invalid Zip-code");
            return "publishers/errors/erro_criacao_editora";
        }

        var editora = publisherRepository.save(publisher);
        model.addAttribute("publisher", publisher);

        return "publishers/publisher_adicionada_com_sucesso";
    }

    //editar publisher
    @GetMapping("/publishers/form_update_editora/{id}")
    public String formEdit (@PathVariable Long id, Model model) {
        var publisher = publisherRepository.findById(id).orElse(null);
        model.addAttribute("publisher", publisher);
        return "publishers/form_update_editora";
    }

    @PostMapping("publishers/{id}")
    public String editarPublisher(@ModelAttribute("publisher") Publisher publisher, @PathVariable Long id, Model model) {
        var existingPublisher = publisherRepository.findById(id).orElseThrow();
        existingPublisher.setName(publisher.getName());
        existingPublisher.setAddress(publisher.getAddress());
        existingPublisher.setCity(publisher.getCity());
        existingPublisher.setDistrict(publisher.getDistrict());
        existingPublisher.setZip(publisher.getZip());

        Publisher updatePublisher = publisherRepository.save(existingPublisher);

        return "redirect:/publishers";
    }

    //Apagar
    @GetMapping("/apagar_editora/{id}")
    public String deletePublisher(@PathVariable("id") long id, Model model) {
        publisherRepository.deleteById(id);
        return "redirect:/publishers";
    }

}
