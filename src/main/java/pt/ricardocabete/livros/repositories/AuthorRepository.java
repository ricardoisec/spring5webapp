package pt.ricardocabete.livros.repositories;

import pt.ricardocabete.livros.domain.Author;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional; // CABETE: Porque é que este repositório tem que conhecer um Optional?

public interface AuthorRepository extends CrudRepository<Author, Long> {

}
