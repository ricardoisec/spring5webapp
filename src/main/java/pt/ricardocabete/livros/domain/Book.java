package pt.ricardocabete.livros.domain; // há quem chame model à package ao invés de domain

import lombok.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import org.springframework.web.bind.annotation.GetMapping; // CABETE: Um GetMapping numa Entidade? :-O

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor // Este construtor é exigido pelo JPA
@Getter
@Setter
@ToString
// Os campos com relações bidirecionais @ManyToMany, @OneToMany, and @ManyToOne têm que ser excluidos para evitar recursividade infinita
// A exclusão pode ser feita aqui ou por cima da propriedade com @EqualsAndHashCode.Exclude
@EqualsAndHashCode(exclude = {"authors"})
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private String isbn;

    @ManyToOne
    private Publisher publisher;

    // @EqualsAndHashCode.Exclude // a exclusão tanto pode ser feita aqui como no topo do ficheiro
    @ManyToMany
    @JoinTable(name = "author_book",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<Author> authors = new HashSet<>();


    // CABETE: Estas linhas extra estão a reservar espaço para alguma coisa?


    public Book(String title, String isbn) {
        this.title = title;
        this.isbn = isbn;
    }

}
