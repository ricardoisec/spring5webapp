package pt.ricardocabete.livros.repositories;

import pt.ricardocabete.livros.domain.Publisher;
import org.springframework.data.repository.CrudRepository;

public interface PublisherRepository extends CrudRepository<Publisher, Long> {
}
