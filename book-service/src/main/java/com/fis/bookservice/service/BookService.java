package com.fis.bookservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fis.bookservice.Entity.Book;
import com.fis.bookservice.Repository.BookRepository;

@Service
public class BookService {
	
	@Autowired
	private BookRepository bookRepository;
	
	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}
	
	public long getAvailableCopies(String bookID) {
		Optional<Book> book = bookRepository.findById(bookID);
		if (book.isPresent()) {
			return book.get().getAvailableCopies();
		}
		return 0;
	}
	
	public int updateForSubscription(String bookID, int difference) {
		Optional<Book> book = bookRepository.findById(bookID);
		if (book.isPresent()) {
			book.get().setAvailableCopies(book.get().getAvailableCopies() + difference);
			bookRepository.save(book.get());
			return HttpStatus.OK.value();
		}
		return HttpStatus.BAD_REQUEST.value();
	}
	

}
