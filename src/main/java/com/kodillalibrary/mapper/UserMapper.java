package com.kodillalibrary.mapper;

import com.kodillalibrary.domain.users.User;
import com.kodillalibrary.domain.users.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserMapper {
    public User mapToUser(final UserDto userDto){
        return new User(userDto.getId(),userDto.getFirstName(),userDto.getLastName(),userDto.getAccountCreationDate(),userDto.getBookRentals());
    }

    public UserDto mapToUserDto(final User user){
        return new UserDto(user.getId(),user.getFirstName(),user.getLastName(),user.getAccountCreationDate(),user.getBookRentals());
    }

    public List<UserDto> mapToUserDtoList(final List<User> userList) {
        return userList.stream()
                .map(this::mapToUserDto)
                .collect(Collectors.toList());
    }

}
