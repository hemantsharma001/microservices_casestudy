package com.fis.bookservice.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fis.bookservice.Entity.Book;
import com.fis.bookservice.service.BookService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
@RequestMapping("/book-service")
/**
 * Controller class for book-service APIs.
 * @author e5573743
 *
 */
public class BookController {
	@Autowired
	private Environment environment;
	
	@Autowired BookService bookService;
	
	@GetMapping("/books")
	@HystrixCommand(fallbackMethod = "getAllBooksFallback")
	/**
	 * This API returns details of all the books.
	 * @return
	 */
	public ResponseEntity<List<Book>> getAllBooks() {
		System.out.println("book-service/books invoked at port "+environment.getProperty("server.port"));
		List<Book> books = bookService.getAllBooks();
		return new ResponseEntity<List<Book>>(books,HttpStatus.OK);
	}
	
	
	/**
	 * Fallback for {@link BookController#getAllBooks} to return empty list of books with http status as {@link HttpStatus#INTERNAL_SERVER_ERROR}.
	 * @return
	 */
	public ResponseEntity<List<Book>> getAllBooksFallback() {
		Book book = new Book();
		List<Book> list = new ArrayList<Book>();
		list.add(book);
		return new ResponseEntity<List<Book>>(list,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	/**
	 * API to return available number of copies of the book id provided.
	 * @param bookID
	 * @return
	 */
	@GetMapping("/getAvailableCopies")
	@HystrixCommand(fallbackMethod = "getAvailableCopiesFallback")
	public ResponseEntity<Long> getAvailableCopies(@QueryParam("bookID") String bookID) {
		System.out.println("getAvailableCopies called with "+bookID+" at "+new Date());
		long availableCopies = bookService.getAvailableCopies(bookID);
		return new ResponseEntity<Long>(availableCopies,HttpStatus.OK);
	}
	
	/**
	 * Fallback for {@link BookController#getAvailableCopies}  to
	 * return zero available copies with http status as {@link HttpStatus#INTERNAL_SERVER_ERROR}}
	 * @param bookID
	 * @return
	 */
	public ResponseEntity<Long> getAvailableCopiesFallback(@QueryParam("bookID") String bookID) {
		return new ResponseEntity<Long>(new Long(0),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping("/updateForSubscription")
	public ResponseEntity<String> updateForSubscription(String bookID) {
		int response =  bookService.updateForSubscription(bookID, -1);
		return new ResponseEntity<String>(HttpStatus.resolve(response));
	}
	
	@PostMapping("/updateForUnsubscription")
	public  ResponseEntity<String> updateForUnsubscription(String bookID) {
		int response = bookService.updateForSubscription(bookID, 1);
		return new ResponseEntity<String>(HttpStatus.resolve(response));
	}
	

}
