package com.bookshop.simpleproject.services;

import com.bookshop.simpleproject.domain.Editor;
import com.bookshop.simpleproject.model.AuthorDTO;
import com.bookshop.simpleproject.model.EditorDTO;

import java.util.List;

public interface EditorService {
    Iterable<Editor> findAll();
    void save(Editor editor);

    Editor findById(Long Id);

    void deleteById(Long id);

    List<EditorDTO> GetListEditorsDTO();

    EditorDTO GetEditorDTO(Long id) throws IllegalArgumentException;
}
