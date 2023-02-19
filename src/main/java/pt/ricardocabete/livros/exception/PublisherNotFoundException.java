package pt.ricardocabete.livros.exception;

public class PublisherNotFoundException extends RuntimeException {
    public PublisherNotFoundException(Long id) {
        super("Não foi possível encontrar a editora com id " + id);
    }
}
