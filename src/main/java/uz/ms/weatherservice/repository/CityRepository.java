package uz.ms.weatherservice.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uz.ms.weatherservice.model.City;

@Repository
public interface CityRepository extends ReactiveCrudRepository<City, Integer> {
    Flux<City> findAllByEnabled(Boolean visibility);
    Mono<City> findByCityName(String cityName);
    Mono<City> findFirstByIdAndEnabled(Integer id, boolean visibility);
}
