package com.kodillalibrary.repository;

import com.kodillalibrary.domain.book_title.BookTitle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibraryBookTitleRepository extends CrudRepository<BookTitle,Long> {
    @Override
    List<BookTitle> findAll();
    @Override
    Optional<BookTitle> findById(Long id);



}
