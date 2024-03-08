package de.stella.agora_web.text.service.impl;

import de.stella.agora_web.text.controller.dto.TextDTO;
import de.stella.agora_web.text.model.Text;
import de.stella.agora_web.text.persistence.ITextDAO;
import de.stella.agora_web.text.service.ITextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class TextServiceImpl implements ITextService {

    @Autowired
    private ITextDAO textDAO;

    @Override
    public List<Text> findTextsById(Collection<Long> textId) {
        return textDAO.findAllByIdIn(textId);
    }

    @Override
    public Text findTextById(Long textId) {
        return textDAO.findById(textId);
    }

    @Override
    public Object findAll() {
        return textDAO.findAll();
    }

    @Override
    public Text save(Text text) {
        return textDAO.save(text);
    }

    @Override
    public Text getById(Long id) {
        return textDAO.findById(id);
    }

    @Override
    public Text save(TextDTO textDTO) {
        // Implementación para guardar un TextDTO
        return null;
    }

    @Override
    public void deleteById(Long id) {
        textDAO.deleteById(id);
    }

    @Override
    public Text update(TextDTO textDTO, Long id) {
        // Implementación para actualizar un TextDTO
        return null;
    }
}