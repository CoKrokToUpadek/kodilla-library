package com.kodillalibrary.domain.book_rental;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;


@Getter
@AllArgsConstructor
public class BookRentalDto {
    private Long id;
    private Long copyID;
    private Long userID;
    private LocalDate rentalDate;
    private LocalDate returnDate;
}
