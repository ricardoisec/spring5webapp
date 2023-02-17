package pt.ricardocabete.livros.exception;

public class AuthorNotFoundException extends RuntimeException {

    public AuthorNotFoundException (Long id) {
        super("Não foi possível encontrar o autor com id" + id);
    }
}
