package com.kodillalibrary.repository;

import com.kodillalibrary.domain.book_copy.BookCopy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryBookCopyRepository extends CrudRepository<BookCopy,Long> {
}
