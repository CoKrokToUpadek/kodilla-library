package com.kodillalibrary.domain.users;

import com.kodillalibrary.domain.book_rental.BookRental;
import com.kodillalibrary.domain.book_rental.BookRentalDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate accountCreationDate;
    private List<BookRentalDto> bookRentals;
}
