package io.fiap.fastfood.infrastructure.controllers.billing;

import static org.slf4j.LoggerFactory.getLogger;

import io.fiap.fastfood.domain.application.exception.HttpStatusExceptionConverter;
import io.fiap.fastfood.domain.application.usecases.BillingUseCase;
import io.fiap.fastfood.infrastructure.controllers.billing.dto.BillingDTO;
import io.fiap.fastfood.infrastructure.gateways.BillingMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/v1/billing", produces = MediaType.APPLICATION_JSON_VALUE)
public class BillingController {
    private static final Logger LOGGER = getLogger(BillingController.class);
    private final BillingMapper mapper;
    private final BillingUseCase billingUseCase;
    private final HttpStatusExceptionConverter httpStatusExceptionConverter;

    public BillingController(BillingMapper mapper,
                             BillingUseCase billingUseCase,
                             HttpStatusExceptionConverter httpStatusExceptionConverter) {
        this.mapper = mapper;
        this.billingUseCase = billingUseCase;
        this.httpStatusExceptionConverter = httpStatusExceptionConverter;
    }

    @PatchMapping(value = "/open")
    @Operation(description = "Opens a new billing day")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Opened"),
        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
        @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    public Mono<ResponseEntity<BillingDTO>> open() {
        return billingUseCase.open()
            .map(mapper::dtoFromDomain)
            .map(ResponseEntity::ok)
            .defaultIfEmpty(ResponseEntity.notFound().build())
            .onErrorMap(e ->
                new ResponseStatusException(httpStatusExceptionConverter.convert(e), e.getMessage(), e))
            .doOnError(throwable -> LOGGER.error(throwable.getMessage(), throwable));
    }

    @PatchMapping(value = "/close")
    @Operation(description = "Closes a billing day")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Closed"),
        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
        @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    public Mono<ResponseEntity<BillingDTO>> close() {
        return billingUseCase.close()
            .map(mapper::dtoFromDomain)
            .map(ResponseEntity::ok)
            .defaultIfEmpty(ResponseEntity.notFound().build())
            .onErrorMap(e ->
                new ResponseStatusException(httpStatusExceptionConverter.convert(e), e.getMessage(), e))
            .doOnError(throwable -> LOGGER.error(throwable.getMessage(), throwable));
    }

}
