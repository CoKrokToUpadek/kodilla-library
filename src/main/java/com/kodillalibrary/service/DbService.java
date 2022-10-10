package com.kodillalibrary.service;

import com.kodillalibrary._resources.BookStatusEnum;
import com.kodillalibrary._resources.CorruptedDataException;
import com.kodillalibrary._resources.EmptyListException;
import com.kodillalibrary._resources.FailedToFetchDataException;
import com.kodillalibrary.domain.book_copy.BookCopy;
import com.kodillalibrary.domain.book_rental.BookRental;
import com.kodillalibrary.domain.book_title.BookTitle;
import com.kodillalibrary.domain.users.User;
import com.kodillalibrary.repository.LibraryBookCopyRepository;
import com.kodillalibrary.repository.LibraryBookRentalRepository;
import com.kodillalibrary.repository.LibraryBookTitleRepository;
import com.kodillalibrary.repository.LibraryUsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.EnumUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DbService {

    private final LibraryBookCopyRepository libraryBookCopyRepository;
    private final LibraryBookTitleRepository libraryBookTitleRepository;
    private final LibraryUsersRepository libraryUsersRepository;

    private final LibraryBookRentalRepository libraryBookRentalRepository;


    public List<User> getAllUsers() {
        return libraryUsersRepository.findAll();
    }

    public Optional<User> getUser(Long id) {
        return libraryUsersRepository.findById(id);
    }

    public Optional<BookTitle> getBookTitle(Long id){
        return libraryBookTitleRepository.findById(id);
    }

    public Optional<BookRental> getBookRental(Long id){
        return libraryBookRentalRepository.findById(id);
    }

    public Optional<BookCopy> getBookCopy(Long id) {
        return libraryBookCopyRepository.findById(id);
    }


    public BookCopy saveBookCopy(final BookCopy bookCopy) {
        return libraryBookCopyRepository.save(bookCopy);
    }

    public void changeBookCopyStatus(Long bookCopyId, BookStatusEnum statusEnum) throws CorruptedDataException {
        BookCopy copy=libraryBookCopyRepository.findById(bookCopyId).orElse(null);
        if (copy==null || !EnumUtils.isValidEnum(BookStatusEnum.class, statusEnum.toString())){
            throw new CorruptedDataException();
        }else {
            BookCopy updatedCopy = new BookCopy(copy.getId(), copy.getTitle(),statusEnum.toString(), copy.getBookRentals());
            saveBookCopy(updatedCopy);
        }
    }

    public boolean checkStatusForReturn(Long bookCopyId) throws FailedToFetchDataException {
        BookCopy copy=libraryBookCopyRepository.findById(bookCopyId).orElseThrow(FailedToFetchDataException::new);
        return copy.getStatus().equals(BookStatusEnum.RENTED.toString());
    }


    public List<BookCopy> getAvailableCopies(String title) throws FailedToFetchDataException {
        List<BookTitle> allTitles=libraryBookTitleRepository.findAll();
        Long specifiedTitleId=allTitles.stream().filter(e->title.equals(e.getTitle())).map(BookTitle::getId).findAny().orElse(0L);
        if (specifiedTitleId==0){
            throw new FailedToFetchDataException();
        }else {
           List<BookCopy> availableCopies=libraryBookCopyRepository.findAll().stream().filter(e->e.getTitle().getId()==specifiedTitleId).collect(Collectors.toList());
           return availableCopies.stream().filter(s->s.getStatus().equals(BookStatusEnum.AVAILABLE.toString())).collect(Collectors.toList());
        }
    }


    public void createUser(User user) throws CorruptedDataException{
        User newUser=new User(user.getId(), user.getFirstName(), user.getLastName(), LocalDate.now(),new ArrayList<>());
        if (newUser.getFirstName()==null || newUser.getLastName()==null) {
            throw new CorruptedDataException();
        }else {
            libraryUsersRepository.save(newUser);
        }

    }

    public void createTitle(BookTitle title) throws CorruptedDataException{
        BookTitle newTitle=new BookTitle(title.getId(),title.getTitle(),title.getAuthor(),title.getReleaseDate(),new ArrayList<>());
        if (newTitle.getTitle()==null || newTitle.getAuthor()==null||newTitle.getReleaseDate()==null){
            throw new CorruptedDataException();
        }else {
            libraryBookTitleRepository.save(newTitle);
        }
    }

    public BookCopy initiateNewCopy(final BookTitle bookTitle){
        return new BookCopy(null,bookTitle, BookStatusEnum.AVAILABLE.toString(),new ArrayList<>());
    }

    public void createCopy(String title) throws CorruptedDataException{
            List<BookTitle> allTitles=libraryBookTitleRepository.findAll();
            BookTitle specifiedTitle=allTitles.stream().filter(e->title.equals(e.getTitle())).findAny().orElse(null);
            if (specifiedTitle==null){
                throw new CorruptedDataException();
            }
            BookCopy copy= initiateNewCopy(specifiedTitle);
            libraryBookCopyRepository.save(copy);
    }

    public void createRental(User user, BookCopy copy){
        BookRental rental=new BookRental(null,copy,user,LocalDate.now(),null);
        libraryBookRentalRepository.save(rental);
    }

    public void rentBook(Long userId, String title) throws FailedToFetchDataException, CorruptedDataException {
        User user=getUser(userId).orElseThrow(FailedToFetchDataException::new);
        List<BookCopy> copies=getAvailableCopies(title);
        createRental(user,copies.get(0));
        changeBookCopyStatus(copies.get(0).getId(),BookStatusEnum.RENTED);

    }

    public void returnBook(Long copyId) throws EmptyListException {
     List<BookRental> rental=libraryBookRentalRepository.findAll().stream().filter(e->e.getCopyID().getId().equals(copyId))
                .collect(Collectors.toList()).stream().filter(r->r.getReturnDate()==(null)).collect(Collectors.toList());
     if (rental.isEmpty()){
         throw new EmptyListException();
     }else
     {
         BookRental returnedRental=new BookRental(rental.get(0).getId(),rental.get(0).getCopyID(),rental.get(0).getUserID(),rental.get(0).getRentalDate(),LocalDate.now());
         libraryBookRentalRepository.save(returnedRental);
     }

    }

}



