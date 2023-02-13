package pt.ricardocabete.livros.exception;

public class BookValidationException extends RuntimeException {

    public BookValidationException(String message) {
        super(message);
    }
}
