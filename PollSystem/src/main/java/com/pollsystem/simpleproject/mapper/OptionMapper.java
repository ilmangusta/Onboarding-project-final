package com.pollsystem.simpleproject.mapper;

import com.pollsystem.simpleproject.domain.Option;
import com.pollsystem.simpleproject.model.OptionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OptionMapper {
    OptionMapper INSTANCE = Mappers.getMapper(OptionMapper.class);
    OptionDTO OptionToOptionDTO(Option option);
}
