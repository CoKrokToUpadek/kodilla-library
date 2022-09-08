package com.kodillalibrary.domain.book_rental;

import com.kodillalibrary.domain.book_copy.BookCopy;
import com.kodillalibrary.domain.users.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;


@Getter
@AllArgsConstructor
public class BookRentalDto {
    private Long id;
    private BookCopy copyID;
    private User userID;
    private LocalDate rentalDate;
    private LocalDate returnDate;
}
