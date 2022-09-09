package com.kodillalibrary.domain.book_copy;


import com.kodillalibrary._resources.BookStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookCopyDTO {
    private Long id;
    private String titleInfo;//temp
    private BookStatusEnum status;
}
