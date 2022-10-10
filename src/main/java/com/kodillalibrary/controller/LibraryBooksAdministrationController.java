package com.kodillalibrary.controller;

import com.kodillalibrary._resources.*;
import com.kodillalibrary.domain.book_copy.BookCopy;
import com.kodillalibrary.domain.book_copy.BookCopyDto;
import com.kodillalibrary.domain.book_title.BookTitle;
import com.kodillalibrary.domain.book_title.BookTitleDto;
import com.kodillalibrary.mapper.BookCopyMapper;
import com.kodillalibrary.mapper.BookTitleMapper;
import com.kodillalibrary.service.DbService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/books_administration")
@AllArgsConstructor
@Transactional(propagation= Propagation.REQUIRED)
public class LibraryBooksAdministrationController {

    private final DbService dbService;
    private final BookCopyMapper bookCopyMapper;
    private final BookTitleMapper bookTitleMapper;

    @GetMapping("/get-book-copy/{id}")
    public ResponseEntity<BookCopyDto> getBookCopy(@PathVariable Long id) throws FailedToFetchDataException {
        return ResponseEntity.ok(bookCopyMapper.mapToBookCopyDto(dbService.getBookCopy(id).orElseThrow(FailedToFetchDataException::new)));
    }

    @GetMapping("/get-book-title/{id}")
    public ResponseEntity<BookTitleDto> getBookTitle(@PathVariable Long id) throws FailedToFetchDataException {
        return ResponseEntity.ok(bookTitleMapper.mapToBookTitleDto(dbService.getBookTitle(id).orElseThrow(FailedToFetchDataException::new)));
    }

    @PostMapping(path = "/add-book-title", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommunicationClass> addBookTitle(@RequestBody BookTitleDto bookTitleDto) throws CorruptedDataException {
        BookTitle title = bookTitleMapper.mapNewTitle(bookTitleDto);
        dbService.createTitle(title);
        return ResponseEntity.ok(new CommunicationClass("title added successfully"));
    }

    @PostMapping("add-book-copy")
    public ResponseEntity<CommunicationClass> addBookCopy(@RequestParam String title) throws CorruptedDataException {
        dbService.createCopy(title);
        return ResponseEntity.ok(new CommunicationClass("copy added successfully"));
    }


    @GetMapping("/get-available-copies")
    public ResponseEntity<List<BookCopyDto>> checkBookCopiesAvailable(@RequestParam String title) throws FailedToFetchDataException, EmptyListException {

        List<BookCopy> copies = dbService.getAvailableCopies(title);
        if (copies.isEmpty()) {
            throw new EmptyListException();
        } else {
            return ResponseEntity.ok(bookCopyMapper.mapToBookCopyDtoList(copies));
        }

    }



}