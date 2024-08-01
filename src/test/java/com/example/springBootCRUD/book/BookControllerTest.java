package com.example.springBootCRUD.book;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = BookController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;
    private static Book book;

    @BeforeAll
    static void createMockBook(){
        String book_isbn = "123-15122-1213";
        String book_name = "Head First Java";
        String book_author = "Nisho";
        int book_publication_year = 2013;
        double book_price_gbp = 45.23;
        book = new Book(book_isbn,
                book_name,
                book_author,
                book_publication_year,
                book_price_gbp);

    }

    @Test
    void testGetBooks() throws Exception {

        List<Book> bookList = new ArrayList<>();
        book.setBookId(1L);
        bookList.add(book);
        when(bookService.getBooks()).thenReturn(bookList);
        this.mockMvc.perform(get("/api/v1/book"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].bookId").value(book.getBookId()))
                .andExpect(jsonPath("$[0].name").value(book.getName()))
                .andExpect(jsonPath("$[0].author").value(book.getAuthor()))
                .andExpect(jsonPath("$[0].publicationYear").value(book.getPublicationYear()))
                .andExpect(jsonPath("$[0].priceGbp").value(book.getPriceGbp()));
    }

    @Test
    void testGetBookById() throws Exception {
        book.setBookId(1L);
        when(bookService.getBookById(book.getBookId())).thenReturn(book);
        this.mockMvc.perform(get("/api/v1/book/"+ book.getBookId().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookId").value(book.getBookId()))
                .andExpect(jsonPath("$.name").value(book.getName()))
                .andExpect(jsonPath("$.author").value(book.getAuthor()))
                .andExpect(jsonPath("$.publicationYear").value(book.getPublicationYear()))
                .andExpect(jsonPath("$.priceGbp").value(book.getPriceGbp()));
    }
}