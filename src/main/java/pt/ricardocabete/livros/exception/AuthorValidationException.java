package pt.ricardocabete.livros.exception;

public class AuthorValidationException extends RuntimeException {
    public AuthorValidationException (String message) {
        super(message);
    }

}
