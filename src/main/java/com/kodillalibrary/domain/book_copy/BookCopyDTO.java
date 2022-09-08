package com.kodillalibrary.domain.book_copy;


import com.kodillalibrary._resources.BookStatusEnum;
import com.kodillalibrary.domain.book_title.Title;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookCopyDTO {
    private Long id;
    private Title titleInfo;
    private BookStatusEnum status;
}
