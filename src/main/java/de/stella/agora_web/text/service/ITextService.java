package de.stella.agora_web.text.service;

import de.stella.agora_web.text.controller.dto.TextDTO;
import de.stella.agora_web.text.model.Text;
import de.stella.agora_web.text.repository.TextRepository;

import java.util.Collection;
import java.util.List;

public interface ITextService {
    void setTextRepository(TextRepository textRepository);
    List<Text> findTextsById(Collection<Long> textId);
    Text findTextById(Long textId);
    List<Text> findAll();
    Text save(Text text);
    Text getById(Long id);
    Text save(TextDTO textDTO);
    void deleteById(Long id);
    Text update(TextDTO textDTO, Long id);
    List<Text> getAll();

}
