package uz.ms.weatherservice.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import uz.ms.weatherservice.model.Weather;

@Repository
public interface WeatherRepository extends ReactiveCrudRepository<Weather, Integer> {

    @Query("select * from weather w " +
            "where (w.city_id, w.id) in (select w2.city_id, max(w2.id) from weather w2 " +
            "where w2.city_id in (select s.city_id from subscription s " +
            "left join city c on c.id = s.city_id " +
            "where s.user_id = :userId and c.enabled is true) " +
            "group by w2.city_id)")
    Flux<Weather> getUserWeather(Integer userId);
}
