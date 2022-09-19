package com.kodillalibrary.domain.book_copy;


import com.kodillalibrary._resources.BookStatusEnum;
import com.kodillalibrary.domain.book_rental.BookRentalDto;
import com.kodillalibrary.domain.book_title.BookTitle;
import com.kodillalibrary.domain.book_title.BookTitleDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class BookCopyDto {
    private Long id;
    private Long titleId;
    private String status;
    private List<BookRentalDto> bookRentals;

}
