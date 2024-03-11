package de.stella.agora_web.text.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.stella.agora_web.text.controller.dto.TextDTO;
import de.stella.agora_web.text.model.Text;
import de.stella.agora_web.text.repository.TextRepository;
import de.stella.agora_web.text.service.ITextService;


@RestController
@RequestMapping("api/v1/texts")
public class TextController {

    private ITextService textService;
    private TextRepository textRepository;
    @Autowired
    public TextController(ITextService textService) {
        this.textService = textService;
    }

    @GetMapping
    public List<Text> getAllTexts() {
        return textService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getTextById(@PathVariable("id") Long id) {
        @SuppressWarnings("null")
        Optional<Text> textOptional = textRepository.findById(id);

        if (!textOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Text text = textOptional.get();

        Map<String, Object> response = new HashMap<>();
        response.put("title", text.getTitle());
        response.put("description", text.getDescription());
        // Add other fields as needed

        return ResponseEntity.ok(response);
    }

    @PostMapping("/store") 
    @PreAuthorize("hasRole('ADMIN')") // Requiere que el usuario autenticado tenga el rol 'ADMIN'
    public ResponseEntity<Text> store(@RequestBody TextDTO textDTO) {
       Text text = textService.save(textDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(text);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')") // Requiere que el usuario autenticado tenga el rol 'ADMIN'
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        textService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')") // Requiere que el usuario autenticado tenga el rol 'ADMIN'
    public ResponseEntity<Text> update(@PathVariable("id") Long id, @RequestBody TextDTO textDTO) {
       Text text = textService.update(textDTO, id);
        return ResponseEntity.accepted().body(text);
    }
}
