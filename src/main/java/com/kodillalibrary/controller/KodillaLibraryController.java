package com.kodillalibrary.controller;


import com.kodillalibrary._resources.BookStatusEnum;
import com.kodillalibrary.domain.book_copy.BookCopy;
import com.kodillalibrary.domain.book_copy.BookCopyDTO;
import com.kodillalibrary.domain.book_rental.BookRentalDto;
import com.kodillalibrary.domain.book_title.BookTitleDto;
import com.kodillalibrary.domain.users.User;
import com.kodillalibrary.domain.users.UserDto;
import com.kodillalibrary.mapper.UserMapper;
import com.kodillalibrary.service.DbService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
@AllArgsConstructor
public class KodillaLibraryController {

    private final DbService dbService;
    private final UserMapper userMapper;


    @GetMapping("/get-user/{id}")
    public UserDto getUser(@PathVariable Long id) throws Exception {
         return userMapper.mapToUserDto(dbService.getUser(id).orElseThrow(Exception::new));
    }

    @PostMapping("/add-user")
    public void addUser(UserDto userDto){

    }

    @PostMapping("/add-book-title")
    public void addBookTitle(BookTitleDto bookTitleDto){

    }
    @PostMapping("/add-book-copy")
    public void addBookCopy(BookCopyDTO bookCopyDTO){

    }

    @PutMapping("/edit-book-copy-status")
    public BookCopyDTO editBookCopyStatus(Long bookCopyID){
        return new BookCopyDTO(1L,"edited book", BookStatusEnum.DESTROYED);
    }

    @GetMapping("/get-available-copies")
    public int checkBookCopiesAvailable(String title){
        return 0;
    }

    @PostMapping("/rent-a-book")
    public BookRentalDto bookCopyRental(BookCopy userId, User bookCopyID){
        return new BookRentalDto(1L,userId,bookCopyID, LocalDate.now(),LocalDate.of(2026,6,6));
    }

    @PutMapping("/return-book-copy")
    public BookCopyDTO bookCopyReturn(Long bookCopyID){
        return new BookCopyDTO(2L,"returned book",BookStatusEnum.AVAILABLE);
    }

}
