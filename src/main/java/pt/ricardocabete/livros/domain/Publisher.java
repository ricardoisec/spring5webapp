package pt.ricardocabete.livros.domain; // há quem chame model à package ao invés de domain

import lombok.*;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

// CABETE: Porque desligaste este construtor do lombok? Depois foste acrescentá-lo lá em baixo
//@NoArgsConstructor // Este construtor é exigido pelo JPA

@Getter
@Setter
@ToString
// Os campos com relações bidirecionais @ManyToMany, @OneToMany, and @ManyToOne têm que ser excluidos para evitar recursividade infinita
// A exclusão pode ser feita aqui ou por cima da propriedade com @EqualsAndHashCode.Exclude
@EqualsAndHashCode(exclude = {"books"}) // @ManyToMany, @OneToMany, and @ManyToOne
@Entity
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String address;
    private String city;
    private String state;
    private String zip;

    // @EqualsAndHashCode.Exclude // a exclusão tanto pode ser feita aqui como no topo do ficheiro
    @OneToMany
    @JoinColumn(name = "publisher_id")
    private Set<Book> books = new HashSet<>();

    public Publisher(String name, String address, String city, String state, String zip) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }
    public Publisher() { // CABETE: Para que criaste este construtor se o lombok o cria por ti?

    }
}
