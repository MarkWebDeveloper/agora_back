package de.stella.agora_web.text.persistence.impl;

import de.stella.agora_web.text.model.Text;
import de.stella.agora_web.text.persistence.ITextDAO;
import de.stella.agora_web.text.repository.TextRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public class TextDAOImpl implements ITextDAO {



   

    @Autowired
    private TextRepository textRepository;
   

    @SuppressWarnings("null")
    @Override
    public Text save(Text text) {
        return textRepository.save(text);
    }

    @SuppressWarnings("null")
    @Override
    public Text findById(Long id) {
        return textRepository.findById(id).orElse(null);
    }

    @SuppressWarnings("null")
    @Override
    public List<Text> findAllByIdIn(Collection<Long> id) {
        return textRepository.findAllById(id);
    }

    @SuppressWarnings("null")
    @Override
    public void deleteById(Long id) {
        textRepository.deleteById(id);
    }

    @Override
    public List<Text> findAll() {
        return textRepository.findAll();
    }
}