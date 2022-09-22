package com.kodillalibrary.mapper;


import com.kodillalibrary.domain.book_copy.BookCopy;
import com.kodillalibrary.domain.book_title.BookTitle;
import com.kodillalibrary.domain.book_title.BookTitleDto;
import com.kodillalibrary.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookTitleMapper {
    @Autowired
    BookCopyMapper bookCopyMapper;


    public BookTitle mapToBookTitle(final BookTitleDto bookTitleDto){
        return new BookTitle(bookTitleDto.getId(), bookTitleDto.getTitle(), bookTitleDto.getAuthor(), bookTitleDto.getReleaseDate(),bookCopyMapper.mapToBookCopyList(bookTitleDto.getBookCopies()));
    }

    public BookTitle mapNewTitle(final BookTitleDto bookTitleDto){
        return new BookTitle(bookTitleDto.getId(), bookTitleDto.getTitle(), bookTitleDto.getAuthor(), bookTitleDto.getReleaseDate(),new ArrayList<BookCopy>());
    }

    public BookTitleDto mapToBookTitleDto(final BookTitle bookTitle){
        return new BookTitleDto(bookTitle.getId(), bookTitle.getTitle(), bookTitle.getAuthor(), bookTitle.getReleaseDate(),bookCopyMapper.mapToBookCopyDtoList(bookTitle.getBookCopies()));
    }

    public List<BookTitleDto> mapToBookTitleDToList(final List<BookTitle> bookTitleDto) {
        return bookTitleDto.stream()
                .map(this::mapToBookTitleDto)
                .collect(Collectors.toList());
    }
    public List<BookTitle> mapToBookTitle(final List<BookTitleDto> bookTitles) {
        return bookTitles.stream()
                .map(this::mapToBookTitle)
                .collect(Collectors.toList());
    }

}
