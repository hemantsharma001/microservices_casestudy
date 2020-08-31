package com.fis.subscriptionservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name="book")
public class Book {

	@Id
	@GeneratedValue
	@Column(name="book_id")
	private String bookID;
	
	@Column(name="book_name")
	private String bookName;
	
	@Column(name="author")
	private String author;
	
	@Column(name="total_copies")
	private long totalCopies;
	
	@Column(name="available_copies")
	private long  availableCopies;

	public String getBookID() {
		return bookID;
	}

	public void setBookID(String bookID) {
		this.bookID = bookID;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public long getTotalCopies() {
		return totalCopies;
	}

	public void setTotalCopies(long totalCopies) {
		this.totalCopies = totalCopies;
	}

	public long getAvailableCopies() {
		return availableCopies;
	}

	public void setAvailableCopies(long availableCopies) {
		this.availableCopies = availableCopies;
	}
	
	
	
	

}
