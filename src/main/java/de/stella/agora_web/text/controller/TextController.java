package de.stella.agora_web.text.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.stella.agora_web.posts.controller.dto.PostDTO;
import de.stella.agora_web.posts.model.Post;
import de.stella.agora_web.posts.services.IPostService;
import de.stella.agora_web.text.controller.dto.TextDTO;
import de.stella.agora_web.text.model.Text;
import de.stella.agora_web.text.service.ITextService;
import lombok.NonNull;


@RestController
@RequestMapping(path="${api-endpoints}/texts")

public class TextController {

    private ITextService textService;


    @Autowired
    public TextController(ITextService textService) {
        this.textService = textService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Text> show(@NonNull @PathVariable("id") Long id) {
        Text text = textService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(text);
    }

    @PostMapping("/store") 
    public ResponseEntity<Text> store(@RequestBody TextDTO textDTO) {
       Text text = textService.save(textDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(text);
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        textService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Text> update(@PathVariable("id") Long id, @RequestBody TextDTO textDTO) {
       Text text = textService.update(textDTO, id);
        return ResponseEntity.accepted().body(text);
    }
 

  

  
}