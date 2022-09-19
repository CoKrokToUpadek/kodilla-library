package com.kodillalibrary.repository;

import com.kodillalibrary.domain.book_copy.BookCopy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibraryBookCopyRepository extends CrudRepository<BookCopy,Long> {
    @Override
    Optional<BookCopy> findById(Long id);
    @Override
    List<BookCopy> findAll();
}
