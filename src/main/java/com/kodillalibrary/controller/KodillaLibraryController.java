package com.kodillalibrary.controller;


import com.kodillalibrary._resources.BookStatusEnum;
import com.kodillalibrary.domain.book_copy.BookCopyDTO;
import com.kodillalibrary.domain.book_rental.BookRentalDto;
import com.kodillalibrary.domain.book_title.BookTitleDto;
import com.kodillalibrary.domain.users.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
@RequiredArgsConstructor
public class KodillaLibraryController {

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
    public BookRentalDto bookCopyRental(Long userId, Long bookCopyID){
        return new BookRentalDto(1L,userId,bookCopyID, LocalDate.now(),LocalDate.of(2026,6,6));
    }

    @PutMapping("/return-book-copy")
    public BookCopyDTO bookCopyReturn(Long bookCopyID){
        return new BookCopyDTO(2L,"returned book",BookStatusEnum.AVAILABLE);
    }

}
