package com.kodillalibrary.domain.book_title;

import com.kodillalibrary.domain.book_copy.BookCopy;
import com.kodillalibrary.domain.book_copy.BookCopyDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class BookTitleDto {
    private Long id;
    private String title;
    private String author;
    private LocalDate releaseDate;
    private List<BookCopyDto> bookCopies;
}
