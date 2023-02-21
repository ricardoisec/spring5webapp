package pt.ricardocabete.livros.services;

import org.springframework.stereotype.Service;
import pt.ricardocabete.livros.domain.Author;
import pt.ricardocabete.livros.domain.Publisher;
import pt.ricardocabete.livros.exception.BookNotFoundException;
import pt.ricardocabete.livros.exception.PublisherNotFoundException;
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

   public Iterable <Publisher> getAllPublishers() {
        return publisherRepository.findAll();
   }
    public Publisher adicionarPublisher (Publisher publisher) throws PublisherValidationException {
        validatePublisherName(publisher.getName());
        validatePublisherAddress(publisher.getAddress());
        validatePublisherCity(publisher.getCity());
        validatePublisherDistrict(publisher.getDistrict());
        validatePublisherZipcode(publisher.getZip());

        return publisherRepository.save(publisher);
    }


    public Publisher createPublisher(Publisher publisher) throws PublisherValidationException {
        validatePublisherName(publisher.getName());
        validatePublisherAddress(publisher.getAddress());
        validatePublisherCity(publisher.getCity());
        validatePublisherDistrict(publisher.getDistrict());
        validatePublisherZipcode(publisher.getZip());

        return publisherRepository.save(publisher);
    }

    public void updatePublisher(Publisher publisher, Long id) throws PublisherValidationException {
        validatePublisherName(publisher.getName());
        validatePublisherAddress(publisher.getAddress());
        validatePublisherCity(publisher.getCity());
        validatePublisherDistrict(publisher.getDistrict());
        validatePublisherZipcode(publisher.getZip());

        var existingPublisher = publisherRepository.findById(id).orElseThrow(() -> new PublisherNotFoundException(id));
        existingPublisher.setName(publisher.getName());
        existingPublisher.setAddress(publisher.getAddress());
        existingPublisher.setCity(publisher.getCity());
        existingPublisher.setDistrict(publisher.getDistrict());
        existingPublisher.setZip(publisher.getZip());

        publisherRepository.save(existingPublisher);
    }

    public void deleteById(long id) {
        publisherRepository.deleteById(id);
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

    public boolean validatePublisherZipcode (String zip) {
        if (zip == null) {
            throw new PublisherValidationException("Publisher's zipcode can't be null");
        }

//        if (zip.length() < 7 || zip.length() > 8) {
//            throw new PublisherValidationException("Invalid zipcode");
//        }

        if (zip.length() == 8 && !zip.contains("-")) {
            zip = zip.substring(0, 4) + "-" + zip.substring(4);
        }



        String zipCode = ("^\\d{4}-?\\d{3}$");
        Pattern pattern = Pattern.compile(zipCode);
        Matcher matcher = pattern.matcher(zip);

        if (!matcher.matches()){
           throw new PublisherValidationException("Zipcode is not valid");
        }

        return true;

    }

}


