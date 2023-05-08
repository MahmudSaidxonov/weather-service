package uz.ms.weatherservice.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uz.ms.weatherservice.dto.ResponseDto;
import uz.ms.weatherservice.dto.SubscriptionDto;

public interface SubscriptionService {

    /**
     * Postman: http://localhost:8080/subscription/subscribe-to-city?cityId=1
     * @param cityId
     */
    Mono<ResponseDto<SubscriptionDto>> subscribe(Integer cityId);

    /**
     * Postman: http://localhost:8080/subscription/user-subscriptions
     */
    Flux<SubscriptionDto> getAllSubscriptions();

    /**
     * Postman: http://localhost:8080/subscription/user-sub-info-for-admin?userId=1
     * @param userId
     */
    Flux<SubscriptionDto> getUserSubscriptions(Integer userId);
}
