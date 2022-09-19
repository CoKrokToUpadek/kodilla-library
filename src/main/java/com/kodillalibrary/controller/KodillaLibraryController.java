package com.kodillalibrary.controller;

import com.kodillalibrary._resources.*;
import com.kodillalibrary.domain.book_copy.BookCopy;
import com.kodillalibrary.domain.book_copy.BookCopyDto;
import com.kodillalibrary.domain.book_rental.BookRentalDto;
import com.kodillalibrary.domain.book_title.BookTitle;
import com.kodillalibrary.domain.book_title.BookTitleDto;
import com.kodillalibrary.domain.users.User;
import com.kodillalibrary.domain.users.UserDto;
import com.kodillalibrary.mapper.BookCopyMapper;
import com.kodillalibrary.mapper.BookRentalMapper;
import com.kodillalibrary.mapper.BookTitleMapper;
import com.kodillalibrary.mapper.UserMapper;
import com.kodillalibrary.service.DbService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
@AllArgsConstructor
public class KodillaLibraryController {

    private final DbService dbService;
    private final UserMapper userMapper;
    private final BookCopyMapper bookCopyMapper;
    private final BookTitleMapper bookTitleMapper;

    private final BookRentalMapper bookRentalMapper;

    //works
    @GetMapping("/get-user/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) throws FailedToFetchDataException {
        return ResponseEntity.ok(userMapper.mapToUserDto(dbService.getUser(id).orElseThrow(FailedToFetchDataException::new)));
    }

    @GetMapping("/get-book-copy/{id}")
    public ResponseEntity<BookCopyDto> getBookCopy(@PathVariable Long id) throws FailedToFetchDataException {
        return ResponseEntity.ok(bookCopyMapper.mapToBookCopyDto(dbService.getBookCopy(id).orElseThrow(FailedToFetchDataException::new)));
    }

    @GetMapping("/get-book-title/{id}")
    public ResponseEntity<BookTitleDto> getBookTitle(@PathVariable Long id) throws FailedToFetchDataException {
        return ResponseEntity.ok(bookTitleMapper.mapToBookTitleDto(dbService.getBookTitle(id).orElseThrow(FailedToFetchDataException::new)));
    }

    @GetMapping("/get-book-rentals/{id}")
    public ResponseEntity<BookRentalDto> getBookRental(@PathVariable Long id) throws FailedToFetchDataException {
        return ResponseEntity.ok(bookRentalMapper.mapToBookRentalDto(dbService.getBookRental(id).orElseThrow(FailedToFetchDataException::new)));
    }

    @PostMapping(path = "/create-user", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommunicationClass> addUser(@RequestBody UserDto userDto) throws CorruptedDataException {
        User user = userMapper.mapToNewUser(userDto);
        dbService.createUser(user);
        return ResponseEntity.ok(new CommunicationClass("user created successfully"));

    }

    @PostMapping(path = "/add-book-title", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommunicationClass> addBookTitle(@RequestBody BookTitleDto bookTitleDto) throws CorruptedDataException {
        BookTitle title = bookTitleMapper.mapNewTitle(bookTitleDto);
        dbService.createTitle(title);
        return ResponseEntity.ok(new CommunicationClass("title added successfully"));
    }


    @PostMapping("add-book-copy")
    public ResponseEntity<CommunicationClass> addBookCopy(@RequestParam(required = true) String title) throws CorruptedDataException {
        dbService.createCopy(title);
        return ResponseEntity.ok(new CommunicationClass("copy added successfully"));
    }


    @PutMapping("/edit-book-copy-status")
    public ResponseEntity<CommunicationClass> editBookCopyStatus(@RequestParam(required = true) Long id, @RequestParam(required = true) BookStatusEnum statusEnum) throws CorruptedDataException {
        dbService.changeBookCopyStatus(id, statusEnum);
        return ResponseEntity.ok(new CommunicationClass("book status changed successfully"));
    }


    @GetMapping("/get-available-copies")
    public ResponseEntity<List<BookCopyDto>> checkBookCopiesAvailable(@RequestParam(required = true) String title) throws FailedToFetchDataException, EmptyListException {

        List<BookCopy> copies = dbService.getAvailableCopies(title);
        if (copies.isEmpty()) {
            throw new EmptyListException();
        } else {
            return ResponseEntity.ok(bookCopyMapper.mapToBookCopyDtoList(copies));
        }

    }

    @PostMapping("/rent-a-book/")
    public ResponseEntity<CommunicationClass> bookCopyRental(@RequestParam(required = true) String bookTitle, @RequestParam(required = true) Long userId) throws FailedToFetchDataException, CorruptedDataException {
        dbService.rentBook(userId, bookTitle);
        return ResponseEntity.ok(new CommunicationClass("book rented successfully"));
    }

    @PutMapping("/return-book-copy")
    public ResponseEntity<CommunicationClass> bookCopyReturn(@RequestParam(required = true) Long bookCopyId) throws FailedToFetchDataException, CorruptedDataException, EmptyListException {
            dbService.checkStatusForReturn(bookCopyId);
            dbService.changeBookCopyStatus(bookCopyId, BookStatusEnum.AVAILABLE);
            dbService.returnBook(bookCopyId);
            return ResponseEntity.ok(new CommunicationClass("Book returned successfully"));
        }

}