package com.pollsystem.simpleproject.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PollListPage {

    private boolean first;
    private boolean last;
    private int size;
    private long totalElements;
    private int totalPages;
    private int number;
    private List<PollDTO> contents;

}
