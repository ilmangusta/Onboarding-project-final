package com.pollsystem.simpleproject.mapper;

import com.pollsystem.simpleproject.domain.Users;
import com.pollsystem.simpleproject.model.UsersDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    UsersDTO UserToUserDTO(Users user);
}
