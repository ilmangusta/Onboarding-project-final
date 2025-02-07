package com.bookshop.simpleproject.mapper;

import com.bookshop.simpleproject.domain.Author;
import com.bookshop.simpleproject.model.AuthorDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthorMapper {

    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);
    AuthorDTO AuthorToDTO(Author author);

}
