package de.stella.agora_web.generics;

public interface IGenericUpdateService<T, R> {

    R update(T type, Long id);
    
}
