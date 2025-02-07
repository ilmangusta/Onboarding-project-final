package com.bookshop.simpleproject.mapper;

import com.bookshop.simpleproject.domain.Book;
import com.bookshop.simpleproject.model.BookDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);
    BookDTO bookTobookDTO(Book book);
    //Book bookDTOToBook(BookDTO BookDTO);
}

