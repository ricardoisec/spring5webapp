package pt.ricardocabete.livros.repositories;

import pt.ricardocabete.livros.domain.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author, Long> {
}
