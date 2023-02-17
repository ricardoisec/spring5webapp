package pt.ricardocabete.livros.services;

import org.springframework.stereotype.Service;
import pt.ricardocabete.livros.domain.Publisher;
import pt.ricardocabete.livros.exception.PublisherValidationException;
import pt.ricardocabete.livros.repositories.BookRepository;
import pt.ricardocabete.livros.repositories.PublisherRepository;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PublisherService {

    private final PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public Publisher adicionarPublisher (Publisher publisher) throws PublisherValidationException {
        validatePublisherName(publisher.getName());
        validatePublisherAddress(publisher.getAddress());
        validatePublisherCity(publisher.getCity());
        validatePublisherDistrict(publisher.getDistrict());
        validatePublisherZipcode(publisher.getZip());

        return publisherRepository.save(publisher);
    }


    //Validacoes

    public void validatePublisherName (String name) {
        if (name == null) {
            throw new PublisherValidationException("Publisher's name can't be null");
        }

        if(name.length() < 3) {
            throw new PublisherValidationException("Publisher's name can't be less than 3 characters");
        }
    }

    public void validatePublisherAddress (String address) {
        if (address == null) {
            throw new PublisherValidationException("Publisher's address can't be null");
        }

        if(address.length() < 5) {
            throw new PublisherValidationException("Publisher's address can't be less than 5 characters");
        }
    }

    public void validatePublisherCity (String city) {
        if (city== null) {
            throw new PublisherValidationException("Publisher's city can't be null");
        }

        if(city.length() < 3) {
            throw new PublisherValidationException("Publisher's city can't be less than 3 characters");
        }
    }

    public void validatePublisherDistrict (String district) {
        if (district == null) {
            throw new PublisherValidationException("Publisher's district can't be null");
        }

        if(district.length() < 5) {
            throw new PublisherValidationException("Publisher's district can't be less than 5 characters");
        }
    }

    public void validatePublisherZipcode (String zip) {
        if (zip == null) {
            throw new PublisherValidationException("Publisher's district can't be null");
        }

        if (zip.length() < 7 || zip.length() > 8) {
            throw new PublisherValidationException("Invalid zipcode");
        }

        String zipCode = ("^\\d{4}-\\d{3}$");
        Pattern pattern = Pattern.compile(zipCode);
        Matcher matcher = pattern.matcher(zipCode);

        if (zip.length() == 7) { //TODO: corrigir isto
            zipCode = zipCode.substring(0, 3) + "-" + zipCode.substring(3);
        }

        if (!matcher.matches()){
           throw new PublisherValidationException("Zipcode is not valid");
        }

    }


}
