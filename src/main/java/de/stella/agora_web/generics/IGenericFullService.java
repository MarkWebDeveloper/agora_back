package de.stella.agora_web.generics;

import java.util.List;

import org.springframework.lang.NonNull;

import de.stella.agora_web.messages.Message;


public interface IGenericFullService<T, DTO> {
    List<T> getAll();
    T getById(@NonNull Long id) throws Exception;
    T getByName(String name) throws Exception;
    public T save(DTO obj);
    public T update(@NonNull Long id, DTO obj) throws Exception;
    public Message delete (@NonNull Long id) throws Exception;
}
