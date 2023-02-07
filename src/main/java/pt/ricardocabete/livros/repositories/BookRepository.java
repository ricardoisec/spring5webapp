package pt.ricardocabete.livros.repositories;

import pt.ricardocabete.livros.domain.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {
}
