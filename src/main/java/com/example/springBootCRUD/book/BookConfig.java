package com.example.springBootCRUD.book;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class BookConfig {

    /**
     * The CommandLineRunner bean to execute code on application startup.
     * @param bookRepository The bookRepository which handles all the data layer functionalities.
     * @return CommandLineRunner object.
     */
    @Bean
    public CommandLineRunner commandLineRunner(BookRepository bookRepository){
        return args -> {
            Book book1 = new Book(
                    "978-1-60309-467-2",
                    "Kodi",
                    "Jared Cullum",
                    2020,
                    11.19);
            Book book2 = new Book(
                    "978-1-60309-491-7",
                    "Monster on the Hill (Expanded Edition)",
                    "Rob Harrell",
                    2020,
                    11.99);

            bookRepository.saveAll(List.of(book1, book2));
        };
    }
}
