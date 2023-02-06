package guru.springframework.spring5webapp.domain; // há quem chame model à package ao invés de domain

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor // Este construtor é exigido pelo JPA7
@Getter
@Setter
@ToString
// Os campos com relações bidirecionais @ManyToMany, @OneToMany, and @ManyToOne têm que ser excluidos para evitar recursividade infinita
// A exclusão pode ser feita aqui ou por cima da propriedade com @EqualsAndHashCode.Exclude
@EqualsAndHashCode(exclude = {"books"})
@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;
    private String lastName;

    // @EqualsAndHashCode.Exclude // a exclusão tanto pode ser feita aqui como no topo do ficheiro
    @ManyToMany(mappedBy = "authors")
    private Set<Book> books = new HashSet<>();

    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

}
