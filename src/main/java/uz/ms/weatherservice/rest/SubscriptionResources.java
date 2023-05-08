package uz.ms.weatherservice.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uz.ms.weatherservice.dto.ResponseDto;
import uz.ms.weatherservice.dto.SubscriptionDto;
import uz.ms.weatherservice.service.SubscriptionService;

@RestController
@RequestMapping("subscription")
@RequiredArgsConstructor
public class SubscriptionResources {

    private final SubscriptionService subscriptionService;

    @PostMapping("subscribe-to-city")
    public Mono<ResponseDto<SubscriptionDto>> subscribeToCity(@RequestParam Integer cityId){
        return subscriptionService.subscribe(cityId);
    }

    @GetMapping("user-subscriptions")
    public Flux<SubscriptionDto> getSubscriptions(){
        return subscriptionService.getAllSubscriptions();
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("user-sub-info-for-admin")
    public Flux<SubscriptionDto> getUserSubscriptions(@RequestParam Integer userId){
        return subscriptionService.getUserSubscriptions(userId);
    }
}
