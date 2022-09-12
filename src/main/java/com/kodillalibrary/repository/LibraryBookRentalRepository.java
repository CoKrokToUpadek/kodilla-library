package com.kodillalibrary.repository;

import com.kodillalibrary.domain.book_rental.BookRental;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryBookRentalRepository extends CrudRepository<BookRental,Long> {
}
