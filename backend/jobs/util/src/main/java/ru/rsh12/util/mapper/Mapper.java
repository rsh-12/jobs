package ru.rsh12.util.mapper;

public interface Mapper<E, D> {

    E dtoToEntity(D dto);

    D entityToDto(E entity);

}
