package ru.rsh12.util;

import java.util.Collection;

public interface CollectionMapper<E, D> {

    Collection<E> dtoToEntities(Collection<D> dto);

    Collection<D> entitiesToDto(Collection<E> entities);

}
