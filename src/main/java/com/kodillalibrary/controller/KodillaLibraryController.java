package com.kodillalibrary.controller;


import com.kodillalibrary._resources.BookStatusEnum;
import com.kodillalibrary._resources.CommunicationClass;
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
    public UserDto getUser(@PathVariable Long id) throws Exception {
        return userMapper.mapToUserDto(dbService.getUser(id).orElseThrow(Exception::new));
    }

    @GetMapping("/get-book-copy/{id}")
    public BookCopyDto getBookCopy(@PathVariable Long id) throws Exception {
        return bookCopyMapper.mapToBookCopyDto(dbService.getBookCopy(id).orElseThrow(Exception::new));
    }

    @GetMapping("/get-book-title/{id}")
    public BookTitleDto getBookTitle(@PathVariable Long id) throws Exception {
        return bookTitleMapper.mapToBookTitleDto(dbService.getBookTitle(id).orElseThrow(Exception::new));
    }

    @GetMapping("/get-book-rentals/{id}")
    public BookRentalDto getBookRental(@PathVariable Long id) throws Exception {
        return bookRentalMapper.mapToBookRentalDto(dbService.getBookRental(id).orElseThrow(Exception::new));
    }

    //works but doesn't throw exception
    @PostMapping(path = "/create-user", consumes = MediaType.APPLICATION_JSON_VALUE)
    public CommunicationClass addUser(@RequestBody UserDto userDto) {
        User user = userMapper.mapToUser(userDto);
        try {
            dbService.createUser(user);
            return new CommunicationClass("user created successfully");
        } catch (Exception e) {
            return new CommunicationClass("failed to create user");
        }
    }


    //works but doesn't throw exception
    @PostMapping(path = "/add-book-title", consumes = MediaType.APPLICATION_JSON_VALUE)
    public CommunicationClass addBookTitle(@RequestBody BookTitleDto bookTitleDto) {
        BookTitle title = bookTitleMapper.mapNewTitle(bookTitleDto);
        try {
            dbService.createTitle(title);
            return new CommunicationClass("title added successfully");
        } catch (Exception e) {
            return new CommunicationClass("failed to add title");
        }

    }

    //works
    @PostMapping("add-book-copy")
    public CommunicationClass addBookCopy(@RequestParam(required = true) String title) {
        try {
            dbService.createCopy(title);
            return new CommunicationClass("copy added successfully");
        } catch (Exception e) {
            return new CommunicationClass("failed to add copy");
        }

    }

    //works
    @PutMapping("/edit-book-copy-status")
    public CommunicationClass editBookCopyStatus(@RequestParam(required = true) Long id, @RequestParam(required = true) BookStatusEnum statusEnum) {
        try {
            dbService.changeBookCopyStatus(id, statusEnum);
            return new CommunicationClass("book status changed successfully");
        } catch (Exception e) {
            return new CommunicationClass("failed to change status");
        }
    }

    //do poprawy
    @GetMapping("/get-available-copies")
    public List<BookCopyDto> checkBookCopiesAvailable(@RequestParam(required = true) String title) {
        try {
            List<BookCopy> copies = dbService.getAvailableCopies(title);
            if (copies.isEmpty()) {
                return null;
            } else {
                return bookCopyMapper.mapToBookCopyDtoList(copies);
            }
        } catch (Exception e) {
            return null;
        }
    }

    @PostMapping("/rent-a-book/")
    public CommunicationClass bookCopyRental(@RequestParam(required = true) String bookTitle, @RequestParam(required = true) Long userId) {
        try {
            dbService.rentBook(userId, bookTitle);
            return new CommunicationClass("book rented successfully");
        } catch (Exception e) {
            System.out.println(e);
            return new CommunicationClass("failed to rent a book");
        }

    }

    @PutMapping("/return-book-copy")
    public CommunicationClass bookCopyReturn(@RequestParam(required = true) Long bookCopyId) throws Exception {
        if (dbService.checkStatusForReturn(bookCopyId)) {
            dbService.changeBookCopyStatus(bookCopyId, BookStatusEnum.AVAILABLE);
            dbService.returnBook(bookCopyId);
            return new CommunicationClass("Book returned successfully");
        } else {
            return new CommunicationClass("failed to return a book");
        }
    }
}
