package de.stella.agora_web.text.persistence;

import de.stella.agora_web.text.model.Text;
import java.util.Collection;
import java.util.List;

public interface ITextDAO {
    Text save(Text text);
    Text findById(Long id);
    List<Text> findAllByIdIn(Collection<Long> ids);
    void deleteById(Long id);
    List<Text> findAll();
}