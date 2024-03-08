package de.stella.agora_web.text.service;

import de.stella.agora_web.text.controller.dto.TextDTO;
import de.stella.agora_web.text.model.Text;
import java.util.Collection;
import java.util.List;

public interface ITextService {
    List<Text> findTextsById(Collection<Long> textId);
    Text findTextById(Long textId);
    Object findAll();
    Text save(Text text);
    Text getById(Long id);
    Text save(TextDTO textDTO);
    void deleteById(Long id);
    Text update(TextDTO textDTO, Long id);
}