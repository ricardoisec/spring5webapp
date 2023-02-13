package pt.ricardocabete.livros.controllers.web;

import org.springframework.web.bind.annotation.*;
import pt.ricardocabete.livros.domain.Author; // CABETE: Porque diabo o livro tem que conhecer autores?
import pt.ricardocabete.livros.domain.Book;
import pt.ricardocabete.livros.repositories.BookRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class BookController {

    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/books") // CABETE: @RequestMapping é muito antigo, usa @GetMapping, @PostMapping, etc
    public String getBooks(Model model) {
        var books = bookRepository.findAll();
        model.addAttribute("books", books);
        model.addAttribute("nome", "Inês");

        return "books/lista_de_livros";
    }

    //Formulario para Adicionar livro
    @GetMapping("/books/add_form")
    public String formAddBooks(){
        return "books/form_adicionar_livro";
    }

    //Criar livro
    @PostMapping("/books")
    public String addBook(@ModelAttribute("book") Book book, Model model) {
        var livro = bookRepository.save(book);
        model.addAttribute("book", book);

        //erros
        if (book.getTitle().isEmpty() || book.getTitle() == null) {
            model.addAttribute("errorMessage", "Book title can't be empty or null");
            return "books/errors/erro_criacao_livro";
        }

        if (book.getTitle().length() < 3) {
            model.addAttribute("errorMessage", "Book title must have at least 3 characters");
            return "books/errors/erro_criacao_livro";
        }

        if(book.getIsbn().isEmpty() || book.getIsbn() == null) {
            model.addAttribute("errorMessage", "Isbn can't be empty or null");
            return "books/errors/erro_criacao_livro";
        }

        if(book.getIsbn().length() < 10){
            model.addAttribute("errorMessage", "Isbn invalid");
            return "books/errors/erro_criacao_livro";
        }

        //validacao isbn-10
        if (book.getIsbn().length() == 10) {
            int somaDigitos = 0;
            for (int i = 0; i < book.getIsbn().length()- 1; i++) { //lenght menos 1 porque nao conta o ultimo digito
                int digitosISBN = Character.getNumericValue(book.getIsbn().charAt(i)); //vai buscar cada numero no isbn
                int calculo = digitosISBN * (10 - i); //(10 - i) porque tem de descer de 10 para 1
                somaDigitos += calculo;
                int digitoValidacao = somaDigitos % 11;

                if (digitoValidacao != Character.getNumericValue(book.getIsbn().charAt(10))){
                    model.addAttribute("errorMessage", "Isbn invalid");
                    return "books/errors/erro_criacao_livro";
                }
            }
        }

        //validacao isbn-13
        if (book.getIsbn().length() == 13) {
            int somaDigitos = 0;
            for (int i = 0; i < book.getIsbn().length() - 1; i++) {
                int digitosISBN = Character.getNumericValue(book.getIsbn().charAt(i));

                if ( i % 2 == 0 ){
                    somaDigitos += digitosISBN * 1; //se for par multiplica 1
                } else { //se for impar multiplica por 3
                    somaDigitos += somaDigitos * 3;
                }

                int digitoValidacao = 10 - (somaDigitos % 10);

                if (digitoValidacao != Character.getNumericValue(book.getIsbn().charAt(13))){
                    model.addAttribute("errorMessage", "Isbn invalid");
                    return "books/errors/erro_criacao_livro";
                }

            }
        }

        return "books/livro_adicionado_com_sucesso";
    }

    //Editar o livro

    //mostrar formulario
    @GetMapping("/books/update_form/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        var book = bookRepository.findById(id).orElse(null);
        model.addAttribute("book", book);
        return "books/update_form";
    }

    //update do livro
    @PostMapping("/books/{id}")
    public String updateLivro(@ModelAttribute("book") Book book, @PathVariable Long id, Model model) {
        var existingBook = bookRepository.findById(id).orElseThrow();
        existingBook.setTitle(book.getTitle());
        existingBook.setIsbn(book.getIsbn());

        Book updatedBook = bookRepository.save(existingBook);

        return "redirect:/books";
    }


    //apagar o livro
    @GetMapping("/apagar_livro/{id}")
    public String deletePublisher(@PathVariable("id") long id, Model model) {
        bookRepository.deleteById(id);
        return "redirect:/books";
    }
}
