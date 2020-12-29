package guru.springframework.spring5webapp.bootstrap;

import guru.springframework.spring5webapp.model.Author;
import guru.springframework.spring5webapp.model.Book;
import guru.springframework.spring5webapp.model.Publisher;
import guru.springframework.spring5webapp.repository.AuthorRepository;
import guru.springframework.spring5webapp.repository.BookRepository;
import guru.springframework.spring5webapp.repository.PublisherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class BootStrapData implements CommandLineRunner {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(BootStrapData.class);

    public BootStrapData(AuthorRepository authorRepository, BookRepository bookRepository, PublisherRepository publisherRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        LOGGER.info("Saving authors and books...");

        Publisher penguin = new Publisher("Penguin");
        publisherRepository.save(penguin);

        Author eric = new Author("Eric", "Evans");
        Book ddd = new Book("Domain Driven Design", "123456");

        eric.getBooks().add(ddd);
        ddd.getAuthors().add(eric);
        ddd.setPublisher(penguin);

        Author rod = new Author("Rod", "Johnson");
        Book noEJB = new Book("J2EE Development without EJS", "242344234");

        rod.getBooks().add(noEJB);
        noEJB.getAuthors().add(rod);
        noEJB.setPublisher(penguin);

        penguin.getBooks().addAll(Arrays.asList(ddd, noEJB));

        authorRepository.saveAll(Arrays.asList(eric, rod));
        bookRepository.saveAll(Arrays.asList(ddd, noEJB));
        publisherRepository.save(penguin);

        LOGGER.info("{} publishers, {} authors and {} books are saved.", publisherRepository.count(), authorRepository.count(), bookRepository.count());
    }
}
