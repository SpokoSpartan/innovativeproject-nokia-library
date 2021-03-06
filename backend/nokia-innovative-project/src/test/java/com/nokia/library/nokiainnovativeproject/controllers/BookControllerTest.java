package com.nokia.library.nokiainnovativeproject.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nokia.library.nokiainnovativeproject.DTOs.BookDTO;
import com.nokia.library.nokiainnovativeproject.entities.Author;
import com.nokia.library.nokiainnovativeproject.entities.Book;
import com.nokia.library.nokiainnovativeproject.entities.BookDetails;
import com.nokia.library.nokiainnovativeproject.services.BookService;
import com.nokia.library.nokiainnovativeproject.utils.Mappings;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("autotests")
class BookControllerTest {

    private static Book book;
    private static BookDTO bookDTO;
    private static ObjectMapper mapper;
    private MockMvc mockMvc;
    private static final String BASE_URL = Mappings.PORT_AUTOTESTS + Mappings.API_VERSION + Mappings.BOOKS;

    public static BookDetails genereteBookDetails() {
        Date date = new Date();
        BookDetails bookDetails = new BookDetails();
        bookDetails.setTitle("test title");
        bookDetails.setDescription("test description");
        bookDetails.setIsbn("test isbn123");
        bookDetails.setCoverPictureUrl("test cover picture url");
        bookDetails.setPublicationDate(date);
        Author author = new Author();
        author.setAuthorFullName("Wojtek");
        bookDetails.setAuthors(Arrays.asList(author));
        bookDetails.setCategories(new ArrayList<>());
        return bookDetails;
    }

    @Mock
    private BookService service;

    @InjectMocks
    private BookController controller;

    @BeforeAll
    public static void init() {
        mapper = new ObjectMapper();
        book = new Book();
        book.setComments("test comments");
        book.setSignature("test signature");
        book.setBookDetails(genereteBookDetails());
    }

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        bookDTO = new BookDTO();
        bookDTO.setComments("test comments");
        bookDTO.setSignature("test signature");
        bookDTO.setBookDetailsId(1L);
        bookDTO.setBookStatusId(1L);
    }

    @Test
    public void getBooksTest() throws Exception {
        when(service.getAllBooks()).thenReturn(Arrays.asList(book));
        mockMvc.perform(get(BASE_URL + Mappings.GET_ALL)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.object[0].comments", Matchers.is("test comments")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.object[0].bookDetails.description", Matchers.is("test description")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.object[0].bookDetails.isbn", Matchers.is("test isbn123")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.object[0].bookDetails.coverPictureUrl", Matchers.is("test cover picture url")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.object[0].bookDetails.title", Matchers.is("test title")));
    }
    @Test
    public void getBookByIdTest() throws Exception {
        when(service.getBookById(1L)).thenReturn(book);
        mockMvc.perform(get(BASE_URL + Mappings.GET_ONE, 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.object.comments", Matchers.is("test comments")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.object.bookDetails.description", Matchers.is("test description")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.object.bookDetails.isbn", Matchers.is("test isbn123")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.object.bookDetails.coverPictureUrl", Matchers.is("test cover picture url")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.object.bookDetails.title", Matchers.is("test title")));
    }

    @Test
    public void createBookTest() throws Exception {
        String jsonRequest = mapper.writeValueAsString(bookDTO);
        when(service.createBook(bookDTO)).thenReturn(book);
        mockMvc.perform(post(BASE_URL + Mappings.CREATE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.object.comments", Matchers.is("test comments")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.object.bookDetails.description", Matchers.is("test description")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.object.bookDetails.isbn", Matchers.is("test isbn123")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.object.bookDetails.coverPictureUrl", Matchers.is("test cover picture url")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.object.bookDetails.title", Matchers.is("test title")));
    }

    @Test
    public void updateBookTest() throws Exception {
        BookDTO updatedDTO = new BookDTO();
        updatedDTO.setComments("updated comments");
        updatedDTO.setSignature("test signature");
        updatedDTO.setBookDetailsId(1L);
        updatedDTO.setBookStatusId(1L);

        Book updatedBook = new Book();
        updatedBook.setComments("updated comments");
        updatedBook.setBookDetails(genereteBookDetails());
        updatedBook.setSignature("test signature");

        String jsonRequest = mapper.writeValueAsString(updatedDTO);

        when(service.updateBook(1L, updatedDTO)).thenReturn(updatedBook);
        mockMvc.perform(post(BASE_URL + Mappings.UPDATE, 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.object.comments", Matchers.is("updated comments")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.object.bookDetails.description", Matchers.is("test description")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.object.bookDetails.isbn", Matchers.is("test isbn123")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.object.bookDetails.coverPictureUrl", Matchers.is("test cover picture url")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.object.bookDetails.title", Matchers.is("test title")));
    }

    @Test
    public void deleteBookTest() throws Exception {
        mockMvc.perform(delete(BASE_URL + Mappings.REMOVE, 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        verify(service).deleteBook(1L);
    }
}