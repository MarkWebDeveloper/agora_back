package de.stella.agora_web.text.persistence.impl;

import de.stella.agora_web.text.model.Text;
import de.stella.agora_web.text.persistence.ITextDAO;
import de.stella.agora_web.text.repository.TextRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Collection;
import java.util.List;

@Repository
public class TextDAOImpl implements ITextDAO {



   

    @Autowired
    private TextRepository textRepository;
   

    @Override
    public Text save(Text text) {
        return textRepository.save(text);
    }

    @Override
    public Text findById(Long id) {
        return textRepository.findById(id).orElse(null);
    }

    @Override
    public List<Text> findAllByIdIn(Collection<Long> ids) {
        return textRepository.findAllById(ids);
    }

    @Override
    public void deleteById(Long id) {
        textRepository.deleteById(id);
    }

    @Override
    public List<Text> findAll() {
        return textRepository.findAll();
    }
}