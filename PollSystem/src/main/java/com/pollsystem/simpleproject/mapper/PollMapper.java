package com.pollsystem.simpleproject.mapper;

import com.pollsystem.simpleproject.domain.Poll;
import com.pollsystem.simpleproject.model.PollDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PollMapper {
    PollMapper INSTANCE = Mappers.getMapper(PollMapper.class);
    //@Mapping(target = "owner",expression = "java(poll.getUser().getUsername())")
    PollDTO PollToPollDTO(Poll poll);
    //Poll PollDTOtoPoll(PollDTO pollDTO);
}
