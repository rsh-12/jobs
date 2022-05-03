package ru.rsh12.util.mapper;

import java.util.List;

public interface ListMapper<E,D> {

    List<E> dtoListToEntityList(List<D> dtoList);

    List<D> entityListToDtoList(List<E> entityList);
}
