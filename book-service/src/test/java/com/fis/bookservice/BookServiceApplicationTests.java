package com.fis.bookservice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.BDDMockito.Then;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fis.bookservice.Entity.Book;
import com.fis.bookservice.Repository.BookRepository;
import com.fis.bookservice.controller.BookController;
import com.fis.bookservice.service.BookService;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookServiceApplicationTests {
	
	@Autowired
	private BookController bookController;
	
	@Mock
	private BookRepository bookRepository;
	
	@InjectMocks
	private BookService bookService;
	
	

	@Test
	@Order(1)
	void contextLoads() {
		assertThat(this.bookController).isNotNull();
	}
	
	@Test
	@Order(2)
	void getAllBooks() {
		when(bookRepository.findAll()).thenReturn(populateBooks());
		List<Book> books = bookService.getAllBooks();
		assertThat(books).hasSize(2);
		assertThat(books.get(0).getBookID()).isEqualTo("B1212");
		assertThat(books.get(1).getBookID()).isEqualTo("B4232");
		
	}
	
	@Test
	@Order(3)
	void getAvailableCopies() {
		Book book = new Book("B1212", "History of Amazon Valley", "Ross Suarez", 10, 10);
		Optional<Book> optional = Optional.of(book);
		when(bookRepository.findById("B1212")).thenReturn(optional);
		Long availableCopies = bookService.getAvailableCopies("B1212");
		assertThat(availableCopies).isEqualTo(10);
	}
	
	@Test
	@Order(4)
	void updateForSubscription() {
		Book book = new Book("B1212", "History of Amazon Valley", "Ross Suarez", 10, 10);
		Optional<Book> optional = Optional.of(book);
		when(bookRepository.findById("B1212")).thenReturn(optional);
		int response =  bookService.updateForSubscription("B1212", -1);
		assertThat(response).isEqualTo(200);
	}
	
	@Test
	@Order(5)
	void updateForUnsubscription() {
		Book book = new Book("B1212", "History of Amazon Valley", "Ross Suarez", 10, 10);
		Optional<Book> optional = Optional.of(book);
		when(bookRepository.findById("B1212")).thenReturn(optional);
		int response =  bookService.updateForSubscription("B1212", -1);
		assertThat(response).isEqualTo(200);
	}
	
	private List<Book> populateBooks() {
		List<Book> books = new ArrayList<Book>();
		books.add(new Book("B1212", "History of Amazon Valley", "Ross Suarez", 10, 10));
		books.add(new Book("B4232", "Language Fundamentals", "H S Parkmay", 10, 10));
		return books;
	}
	

}
