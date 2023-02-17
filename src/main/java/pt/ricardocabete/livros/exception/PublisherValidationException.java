package pt.ricardocabete.livros.exception;

public class PublisherValidationException extends RuntimeException{
    public PublisherValidationException (String message) {
        super(message);
    }
}
