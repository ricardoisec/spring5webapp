package pt.ricardocabete.livros.services;

import org.springframework.stereotype.Service;
import pt.ricardocabete.livros.domain.Author;
import pt.ricardocabete.livros.exception.AuthorNotFoundException;
import pt.ricardocabete.livros.exception.AuthorValidationException;
import pt.ricardocabete.livros.repositories.AuthorRepository;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author createAuthor(Author author) throws AuthorValidationException {
        validateAuthorFirstName(author.getFirstName());

        return authorRepository.save(author);
    }

    public void validateAuthorFirstName(String firstName) {
        if (firstName.isEmpty()) {
            throw new AuthorValidationException("First Name can't be empty");
        }

        if(firstName.length() < 2) {
            throw new AuthorValidationException("First Name must have more than 2 characters");
        }
    }

    public void updateAutor(Author author, Long id) throws AuthorValidationException {
        validateAuthorFirstName(author.getFirstName());

        var existingAuthor = authorRepository.findById(id).orElseThrow(() -> new AuthorNotFoundException(id));
        existingAuthor.setFirstName(author.getFirstName());
        existingAuthor.setLastName(author.getLastName());

        authorRepository.save(existingAuthor);

    }
}
