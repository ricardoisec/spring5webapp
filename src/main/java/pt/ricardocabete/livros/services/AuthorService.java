package pt.ricardocabete.livros.services;

import org.springframework.stereotype.Service;
import pt.ricardocabete.livros.domain.Author;
import pt.ricardocabete.livros.exception.AuthorNotFoundException;
import pt.ricardocabete.livros.exception.AuthorValidationException;
import pt.ricardocabete.livros.repositories.AuthorRepository;

import java.util.List;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Iterable<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Author createAuthor(Author author) throws AuthorValidationException {
        validateAuthorFirstName(author.getFirstName());
        validateAuthorLastName(author.getLastName());

        return authorRepository.save(author);
    }

    public void updateAutor(Author author, Long id) throws AuthorValidationException {
        validateAuthorFirstName(author.getFirstName());
        validateAuthorLastName(author.getLastName());

        var existingAuthor = authorRepository.findById(id).orElseThrow(() -> new AuthorNotFoundException(id));
        existingAuthor.setFirstName(author.getFirstName());
        existingAuthor.setLastName(author.getLastName());

        authorRepository.save(existingAuthor);
    }

    public void deleteById (Long id) {
        authorRepository.deleteById(id);
    }

















    public void validateAuthorFirstName(String firstName) {
        if (firstName.isEmpty()) {
            throw new AuthorValidationException("First Name can't be empty");
        }

        if(firstName.length() < 2) {
            throw new AuthorValidationException("First Name must have more than 2 characters");
        }
    }

    public void validateAuthorLastName(String lastName) {
        if (lastName.isEmpty()) {
            throw new AuthorValidationException("Last Name can't be empty");
        }

        if(lastName.length() < 2) {
            throw new AuthorValidationException("Last Name must have more than 2 characters");
        }
    }
}
