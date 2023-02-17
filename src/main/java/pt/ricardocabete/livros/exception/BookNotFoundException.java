package pt.ricardocabete.livros.exception;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(Long id) {
        super("Não foi possível encontrar o livro com id " + id);
    }
}
