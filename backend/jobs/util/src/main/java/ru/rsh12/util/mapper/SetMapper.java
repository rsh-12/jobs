package ru.rsh12.util.mapper;

import java.util.Set;

public interface SetMapper<E, D> {

    Set<E> dtoSetToEntitySet(Set<D> dtoSet);

    Set<D> entitySetToDtoSet(Set<E> entitySet);
}
