package com.kodillalibrary.controller;

import com.kodillalibrary._resources.*;
import com.kodillalibrary.domain.book_rental.BookRentalDto;
import com.kodillalibrary.mapper.BookRentalMapper;
import com.kodillalibrary.service.DbService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/rentals")
@AllArgsConstructor
@Transactional(propagation= Propagation.REQUIRED)
public class LibraryRentalsController {

    private final DbService dbService;
    private final BookRentalMapper bookRentalMapper;
    @PostMapping("/rent-a-book/")
    public ResponseEntity<CommunicationClass> bookCopyRental(@RequestParam String bookTitle, @RequestParam Long userId) throws FailedToFetchDataException, CorruptedDataException {
        dbService.rentBook(userId, bookTitle);
        return ResponseEntity.ok(new CommunicationClass("book rented successfully"));
    }

    @PutMapping("/return-book-copy")
    public ResponseEntity<CommunicationClass> bookCopyReturn(@RequestParam Long bookCopyId) throws FailedToFetchDataException, CorruptedDataException, EmptyListException {
        dbService.checkStatusForReturn(bookCopyId);
        dbService.changeBookCopyStatus(bookCopyId, BookStatusEnum.AVAILABLE);
        dbService.returnBook(bookCopyId);
        return ResponseEntity.ok(new CommunicationClass("Book returned successfully"));
    }

    @GetMapping("/get-book-rentals/{id}")
    public ResponseEntity<BookRentalDto> getBookRental(@PathVariable Long id) throws FailedToFetchDataException {
        return ResponseEntity.ok(bookRentalMapper.mapToBookRentalDto(dbService.getBookRental(id).orElseThrow(FailedToFetchDataException::new)));
    }

    @PutMapping("/edit-book-copy-status")
    public ResponseEntity<CommunicationClass> editBookCopyStatus(@RequestParam Long id, @RequestParam BookStatusEnum statusEnum) throws CorruptedDataException {
        dbService.changeBookCopyStatus(id, statusEnum);
        return ResponseEntity.ok(new CommunicationClass("book status changed successfully"));
    }

}
