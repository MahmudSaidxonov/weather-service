package uz.ms.weatherservice.config;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;
import uz.ms.weatherservice.dto.ResponseDto;

@RestControllerAdvice
public class MyExceptionHandler {
    @ExceptionHandler
    public Mono<ResponseDto<Void>> dataIntegrityViolationException(DataIntegrityViolationException e){
        return Mono.just(ResponseDto.<Void>builder()
                .code(AppStatusCodes.DATABASE_ERROR_CODE)
                .message("Error while saving data: " + e.getMessage())
                .build());
    }
}
