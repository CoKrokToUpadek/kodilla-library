package com.kodillalibrary.repository;

import com.kodillalibrary.domain.book_rental.BookRental;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibraryBookRentalRepository extends CrudRepository<BookRental,Long> {
    @Override
    Optional<BookRental> findById(Long id);
    @Override
    List<BookRental> findAll();
}
