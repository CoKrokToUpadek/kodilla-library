package com.kodillalibrary.mapper;


import com.kodillalibrary.domain.book_copy.BookCopy;
import com.kodillalibrary.domain.book_copy.BookCopyDto;
import com.kodillalibrary.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookCopyMapper {

    @Autowired
    BookRentalMapper bookRentalMapper;
    DbService service;

   public BookCopy mapToBookCopy(final BookCopyDto bookCopyDto){
        BookCopy copy=service.getBookCopy(bookCopyDto.getTitleId()).orElse(null);
        return new BookCopy(bookCopyDto.getId(),copy.getTitle(),bookCopyDto.getStatus(),bookRentalMapper.mapToBookRentalList(bookCopyDto.getBookRentals()));
    }

    public BookCopyDto mapToBookCopyDto(final BookCopy bookCopy){
       return new BookCopyDto(bookCopy.getId(),bookCopy.getTitle().getId(),bookCopy.getStatus(),bookRentalMapper.mapToBookRentalDToList(bookCopy.getBookRentals()));
    }


   public List<BookCopy> mapToBookCopyList(final List<BookCopyDto> bookCopies) {
        return bookCopies.stream()
                .map(this::mapToBookCopy)
                .collect(Collectors.toList());
    }

    public List<BookCopyDto> mapToBookCopyDtoList(final List<BookCopy> bookCopies) {
        return bookCopies.stream()
                .map(this::mapToBookCopyDto)
                .collect(Collectors.toList());
    }
}
