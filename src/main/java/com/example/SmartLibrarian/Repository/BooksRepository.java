package com.example.SmartLibrarian.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.SmartLibrarian.Model.Books;

@Repository
public interface BooksRepository extends CrudRepository<Books, Integer> {

}
