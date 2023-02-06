package guru.springframework.spring5webapp.domain; // há quem chame model à package ao invés de domain

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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

    public Book(String title, String isbn) {
        this.title = title;
        this.isbn = isbn;
    }

}
