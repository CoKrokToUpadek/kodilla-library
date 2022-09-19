package com.kodillalibrary.mapper;

import com.kodillalibrary.domain.book_copy.BookCopy;
import com.kodillalibrary.domain.book_rental.BookRental;
import com.kodillalibrary.domain.book_rental.BookRentalDto;
import com.kodillalibrary.domain.users.User;
import com.kodillalibrary.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookRentalMapper {
    @Autowired
    DbService dbService;


    public BookRental mapToBookRental(final BookRentalDto bookRentalDto){
        BookCopy bookCopy=dbService.getBookCopy(bookRentalDto.getCopyID()).orElse(null);
        User user=dbService.getUser(bookRentalDto.getUserID()).orElse(null);


        return new BookRental(bookRentalDto.getId(),bookCopy ,user ,bookRentalDto.getRentalDate(),bookRentalDto.getReturnDate());
    }

    public BookRentalDto mapToBookRentalDto(final BookRental bookRental){
        return new BookRentalDto(bookRental.getId(),bookRental.getCopyID().getId(),bookRental.getUserID().getId(),bookRental.getRentalDate(),bookRental.getReturnDate());
    }

    public List<BookRentalDto> mapToBookRentalDToList(final List<BookRental> bookRentals) {
        return bookRentals.stream()
                .map(this::mapToBookRentalDto)
                .collect(Collectors.toList());
    }

    public List<BookRental> mapToBookRentalList(final List<BookRentalDto> bookRentalsDto) {
        return bookRentalsDto.stream()
                .map(this::mapToBookRental)
                .collect(Collectors.toList());
    }

}
