package uz.ms.weatherservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uz.ms.weatherservice.dto.ResponseDto;
import uz.ms.weatherservice.dto.SubscriptionDto;
import uz.ms.weatherservice.model.Subscription;
import uz.ms.weatherservice.model.User;
import uz.ms.weatherservice.repository.CityRepository;
import uz.ms.weatherservice.repository.SubscriptionRepository;
import uz.ms.weatherservice.service.SubscriptionService;
import uz.ms.weatherservice.service.mapper.CityMapper;
import uz.ms.weatherservice.service.mapper.SubscriptionMapper;

import static uz.ms.weatherservice.config.AppStatusCodes.*;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final CityRepository cityRepository;
    private final CityMapper cityMapper;
    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionMapper subscriptionMapper;
//    private final Gson gson;

    private Mono<SubscriptionDto> loadCity(SubscriptionDto dto){
        return Mono.just(dto)
                .zipWith(cityRepository.findById(dto.getCity().getId()))
                .map(result -> {
                    result.getT1().setCity(cityMapper.toDto(result.getT2()));
                    return result.getT1();
                });
    }

    @Override
    public Mono<ResponseDto<SubscriptionDto>> subscribe(Integer cityId) {
        return ReactiveSecurityContextHolder.getContext()
                .flatMap(u -> {
//                    User user = gson.fromJson("{ 'email':'"+u.getAuthentication().getPrincipal().toString()+"'}", User.class);
//                    System.out.println(user.getId() + " " + user.getEmail() + " " + user.getFirstName());
                    User user = (User) u.getAuthentication().getPrincipal();
                    return cityRepository.findFirstByIdAndEnabled(cityId, true)
                            .flatMap(city ->
                                    subscriptionRepository.subscribe(user.getId(), cityId)
                                            .flatMap(sub -> Mono.just(subscriptionMapper.toDto(sub))
                                                    .flatMap(this::loadCity)
                                                    .map(subscription -> ResponseDto.<SubscriptionDto>builder()
                                                            .data(subscription)
                                                            .message("You already subscribed to this city!")
                                                            .code(DATABASE_ERROR_CODE)
                                                            .build()))
                                            .switchIfEmpty(Mono.just(
                                                            Subscription.builder()
                                                                    .userId(user.getId())
                                                                    .cityId(cityId)
                                                                    .build())
                                                    .flatMap(sub ->
                                                            subscriptionRepository.save(sub)
                                                                    .map(subscriptionMapper::toDto)
                                                                    .flatMap(this::loadCity)
                                                                    .map(saved -> ResponseDto.<SubscriptionDto>builder()
                                                                            .message("OK")
                                                                            .success(true)
                                                                            .data(saved)
                                                                            .build())
                                                    )))
                            .switchIfEmpty(Mono.just(ResponseDto.<SubscriptionDto>builder()
                                    .message("Sorry, city is not enabled")
                                    .code(DATABASE_ERROR_CODE)
                                    .build()));
                });
    }


    @Override
    public Flux<SubscriptionDto> getAllSubscriptions() {
        return ReactiveSecurityContextHolder.getContext()
                .flux()
                .flatMap(auth -> {
                    User user = (User) auth.getAuthentication().getPrincipal();
                    return subscriptionRepository.findAllByUserId(user.getId())
                            .map(subscriptionMapper::toDto)
                            .flatMap(this::loadCity);
                });
    }

    @Override
    public Flux<SubscriptionDto> getUserSubscriptions(Integer userId) {
        return subscriptionRepository.findAllByUserId(userId)
                .map(subscriptionMapper::toDto)
                .flatMap(this::loadCity);
    }
}
