package de.stella.agora_web.generics;

import java.util.List;

public interface IGenericSearchService<T> {
    List<T> getManyByName(String name) throws Exception;
    List<T> getManyByCategoryName(String name) throws Exception;
}