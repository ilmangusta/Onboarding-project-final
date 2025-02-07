package com.bookshop.simpleproject.mapper;

import com.bookshop.simpleproject.domain.Editor;
import com.bookshop.simpleproject.model.EditorDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EditorMapper {
    EditorMapper INSTANCE = Mappers.getMapper(EditorMapper.class);
    EditorDTO EditorToDTO(Editor editor);
}
