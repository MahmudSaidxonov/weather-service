package uz.ms.weatherservice.service.mapper;

public interface CommonMapper<D, E> {
    D toDto(E entity);
    E toEntity(D dto);
}