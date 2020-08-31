package com.fis.bookservice.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.fis.bookservice.Entity.Book;

public interface BookRepository extends CrudRepository<Book, String> {
	
	@Override
    List<Book> findAll();
	
	@Override
	Optional<Book> findById(String id);
}
