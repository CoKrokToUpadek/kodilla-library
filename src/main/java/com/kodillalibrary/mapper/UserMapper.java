package com.kodillalibrary.mapper;

import com.kodillalibrary.domain.users.User;
import com.kodillalibrary.domain.users.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserMapper {
    @Autowired
    BookRentalMapper bookRentalMapper;

    public User mapToUser(final UserDto userDto){
        return new User(userDto.getId(),userDto.getFirstName(),userDto.getLastName(), userDto.getAccountCreationDate(),bookRentalMapper.mapToBookRentalList(userDto.getBookRentals()));
    }

    public User mapToNewUser(final UserDto userDto){
        return new User(null, userDto.getFirstName(), userDto.getLastName(), null,null);
    }



    public UserDto mapToUserDto(final User user){
        return new UserDto(user.getId(), user.getFirstName(),user.getLastName(),user.getAccountCreationDate(),bookRentalMapper.mapToBookRentalDToList(user.getBookRentals()));
    }

    public List<UserDto> mapToUserDtoList(final List<User> userList) {
        return userList.stream()
                .map(this::mapToUserDto)
                .collect(Collectors.toList());
    }

    public List<User> mapToUserList(final List<UserDto> userListDto) {
        return userListDto.stream()
                .map(this::mapToUser)
                .collect(Collectors.toList());
    }

}
