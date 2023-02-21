package pt.ricardocabete.livros.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pt.ricardocabete.livros.exception.PublisherValidationException;

import static org.junit.jupiter.api.Assertions.*;

class PublisherServiceTest {

    PublisherService publisherService = new PublisherService(null);


    //zip code correto: 3030-338
    //zipcode correto sem traço: 3030338
    //zip code invalido: 303-0338
    //zipcode invalido grande : 30303385
    //zipcode invalido pequeno : 3030-3
    //zipcode null
    @Test
    void validatePublisherZipcodeValido() {
        var resultado = publisherService.validatePublisherZipcode("3030-338");
        assertTrue(resultado);
    }

    @Test
    void validatePublisherZipcodeValidoSemTraço() {
        var resultado = publisherService.validatePublisherZipcode("3030338");
        assertTrue(resultado);
    }

    @Test
    void validatePublisherZipcodeInvalid() {
        PublisherValidationException exception = Assertions.assertThrows(
                PublisherValidationException.class,
                () -> publisherService.validatePublisherZipcode("303-0338")
        );
        Assertions.assertEquals("Zipcode is not valid", exception.getMessage());
    }

    @Test
    void validatePublisherZipcodeInvalidB() {
        PublisherValidationException exception = Assertions.assertThrows(
                PublisherValidationException.class,
                () -> publisherService.validatePublisherZipcode("30303385")
        );
        Assertions.assertEquals("Zipcode is not valid", exception.getMessage());
    }

    @Test
    void validatePublisherZipcodeInvalidC() {
        PublisherValidationException exception = Assertions.assertThrows(
                PublisherValidationException.class,
                () -> publisherService.validatePublisherZipcode("3030-3")
        );
        Assertions.assertEquals("Zipcode is not valid", exception.getMessage());
    }

    @Test
    void validatePublisherZipcodeInvalidNull() {
        PublisherValidationException exception = Assertions.assertThrows(
                PublisherValidationException.class,
                () -> publisherService.validatePublisherZipcode(null)
        );
        Assertions.assertEquals("Publisher's zipcode can't be null", exception.getMessage());
    }




}