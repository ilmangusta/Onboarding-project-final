package com.bookshop.simpleproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditorDTO {

    private Long id;
    private String Name;
    private String PhisicalAddress;
}
