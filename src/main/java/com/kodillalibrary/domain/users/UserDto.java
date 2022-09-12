package com.kodillalibrary.domain.users;

import com.kodillalibrary.domain.book_rental.BookRental;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate accountCreationDate;
    private List<BookRental> bookRentals;
}
