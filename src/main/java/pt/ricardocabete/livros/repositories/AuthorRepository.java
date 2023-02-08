package pt.ricardocabete.livros.repositories;

import pt.ricardocabete.livros.domain.Author;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AuthorRepository extends CrudRepository<Author, Long> {

}
