package com.bookshop.simpleproject.services;

import com.bookshop.simpleproject.domain.Author;
import com.bookshop.simpleproject.domain.Book;
import com.bookshop.simpleproject.domain.Editor;
import com.bookshop.simpleproject.mapper.EditorMapper;
import com.bookshop.simpleproject.model.AuthorDTO;
import com.bookshop.simpleproject.model.EditorDTO;
import com.bookshop.simpleproject.repositories.EditorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EditorServiceImpl implements EditorService {
    private final EditorRepository editorRepository;

    @Autowired
    private EditorMapper editorMapper;

    public EditorServiceImpl(EditorRepository editorRepository) {
        this.editorRepository = editorRepository;
    }

    @Override
    public Iterable<Editor> findAll() {
        return editorRepository.findAll();
    }

    @Override
    public void save(Editor editor) {
        editorRepository.save(editor);
    }

    @Override
    public Editor findById(Long id) {
        for (Editor editor: editorRepository.findAll())
            if (editor.getId().equals(id)){
                return editor;
            }
        return null;
    }

    @Override
    public void deleteById(Long id) {
        editorRepository.deleteById(id);
    }

    @Override
    public List<EditorDTO> GetListEditorsDTO() {
        return editorRepository
                .findAll()
                .stream()
                .map(book -> {
                    return editorMapper.EditorToDTO(book);
                })
                .collect(Collectors.toList());
    }

    @Override
    public EditorDTO GetEditorDTO(Long id) throws IllegalArgumentException {
        for (Editor editor: editorRepository.findAll()){
            if (editor.getId().equals(id)){
                return editorMapper.EditorToDTO(editor);
            }
        }
        throw  new IllegalArgumentException();
    }

}
