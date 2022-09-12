package com.kodillalibrary.repository;

import com.kodillalibrary.domain.book_title.BookTitle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryBookTitleRepository extends CrudRepository<BookTitle,Long> {

}
