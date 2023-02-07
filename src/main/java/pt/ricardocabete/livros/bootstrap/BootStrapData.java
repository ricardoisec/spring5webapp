package pt.ricardocabete.livros.bootstrap;

import pt.ricardocabete.livros.domain.Author;
import pt.ricardocabete.livros.domain.Book;
import pt.ricardocabete.livros.domain.Publisher;
import pt.ricardocabete.livros.repositories.AuthorRepository;
import pt.ricardocabete.livros.repositories.BookRepository;
import pt.ricardocabete.livros.repositories.PublisherRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootStrapData implements CommandLineRunner {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;

    public BootStrapData(AuthorRepository authorRepository, BookRepository bookRepository, PublisherRepository publisherRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
    }

    /**
     * Preenche a base de dados com dados iniciais, equivalente a correr migrations
     * Como a relação entre autores e livros é n-n a tabela de relação é preenchida automaticamente pelo Hibernate
     */
    @Override
    public void run(String... args) throws Exception {
        System.out.println("Started in Bootstrap");

        Publisher sfgPublishing = new Publisher();
        sfgPublishing.setName("SFG Publishing");
        sfgPublishing.setCity("St Peterbourgh");
        sfgPublishing.setState("FL");

        publisherRepository.save(sfgPublishing);

        Author ericEvans = new Author("Eric", "Evans");
        Book domainDriverDesign = new Book("Domain Driven Design", "123123");
        ericEvans.getBooks().add(domainDriverDesign);
        domainDriverDesign.getAuthors().add(ericEvans);
        domainDriverDesign.setPublisher(sfgPublishing);
        sfgPublishing.getBooks().add(domainDriverDesign);

        authorRepository.save(ericEvans);
        bookRepository.save(domainDriverDesign);
        publisherRepository.save(sfgPublishing);

        Author rodJohnson = new Author("Rod", "Johnson");
        Book noEJB = new Book("J2EE Development without EJB", "456456");
        rodJohnson.getBooks().add(noEJB);
        noEJB.getAuthors().add(rodJohnson);
        noEJB.setPublisher(sfgPublishing);
        sfgPublishing.getBooks().add(noEJB);

        authorRepository.save(rodJohnson);
        bookRepository.save(noEJB);
        publisherRepository.save(sfgPublishing);


        System.out.println("Number of authors: " + authorRepository.count());
        System.out.println("Number of books: " + bookRepository.count());
        System.out.println("Number of publishers: " + publisherRepository.count());
        System.out.println("Publisher number of books: " + sfgPublishing.getBooks().size());
    }
}
