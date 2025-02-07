package com.bookshop.simpleproject.controller;


import com.bookshop.simpleproject.domain.Editor;
import com.bookshop.simpleproject.model.EditorDTO;
import com.bookshop.simpleproject.services.EditorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class EditorRestController {

    @Autowired
    private EditorService editorService;

    @GetMapping(value = "/GetEditors")
    public ResponseEntity<List<EditorDTO>> getEditors(Model model){
        return new ResponseEntity<List<EditorDTO>>(editorService.GetListEditorsDTO(), HttpStatus.OK);
    }

    @GetMapping(value = "/GetEditors/{EditorId}")
    public ResponseEntity<EditorDTO> getEditor(@PathVariable("EditorId") Long id){
        return new ResponseEntity<EditorDTO>(editorService.GetEditorDTO(id), HttpStatus.OK);
    }

    @DeleteMapping(value = "/DeleteEditors/{EditorId}")
    public void deleteEditor(@PathVariable("EditorId") Long id, Model model){
        if (editorService.findById(id) != null){
            editorService.deleteById(id);
        }else {
            throw new NoSuchElementException();
        }
    }

    //@PutMapping(value = "/editor")
    //public Editor addEditor(
    //        @RequestBody Map<String, String> body){

    //    Editor editor = new Editor(body.get("name"),body.get("address"));
    //    editorService.save(editor);
    //    return editor;
    //}

    @PostMapping(value = "/AddEditor")
    public ResponseEntity<Editor> addEditor(
            @RequestBody EditorDTO editorDTO){

        Editor editor = new Editor(editorDTO.getName(), editorDTO.getPhisicalAddress());
        editorService.save(editor);
        return new ResponseEntity<Editor>(editor, HttpStatus.OK);
    }

    @PostMapping(value = "/EditEditors/{EditorId}")
    public ResponseEntity<Editor> editEditor(
            @PathVariable("EditorId") Long id,
            @RequestParam String address){

        if (editorService.findById(id) != null){
            Editor editor = editorService.findById(id);
            editor.setPhisicalAddress(address);
            editorService.save(editor);
            return new ResponseEntity<Editor>(editor, HttpStatus.OK);
        }else {
            throw new NoSuchElementException();
        }
    }

}
