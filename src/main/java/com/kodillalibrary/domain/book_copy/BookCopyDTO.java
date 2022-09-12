package com.kodillalibrary.domain.book_copy;


import com.kodillalibrary._resources.BookStatusEnum;
import com.kodillalibrary.domain.book_rental.BookRental;
import com.kodillalibrary.domain.book_title.BookTitle;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class BookCopyDTO {
    private Long id;
    private BookTitle title;
    private BookStatusEnum status;
    private List<BookRental> bookRentals;
}
