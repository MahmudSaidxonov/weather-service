package uz.ms.weatherservice.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uz.ms.weatherservice.model.Subscription;

@Repository
public interface SubscriptionRepository extends ReactiveCrudRepository<Subscription, Integer> {

    Flux<Subscription> findAllByUserId(Integer userId);

    @Query("select * from subscription s left join city c on c.id = s.city_id " +
            "where c.enabled is true and s.user_id = :userId and c.id = :cityId")
    Mono<Subscription> subscribe(Integer userId, Integer cityId);

}
