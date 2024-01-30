package io.fiap.fastfood.infrastructure.controllers.order;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE;

import io.fiap.fastfood.domain.application.exception.HttpStatusExceptionConverter;
import io.fiap.fastfood.domain.application.usecases.OrderUseCase;
import io.fiap.fastfood.infrastructure.controllers.order.dto.OrderDTO;
import io.fiap.fastfood.infrastructure.gateways.OrderMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/v1/orders", produces = APPLICATION_JSON_VALUE)
public class OrderController {

    private static final Logger LOGGER = getLogger(OrderController.class);

    private final OrderMapper mapper;
    private final OrderUseCase orderUseCase;

    private final HttpStatusExceptionConverter httpStatusExceptionConverter;

    public OrderController(OrderMapper mapper,
                             OrderUseCase orderUseCase,
                             HttpStatusExceptionConverter httpStatusExceptionConverter) {
        this.mapper = mapper;
        this.orderUseCase = orderUseCase;
        this.httpStatusExceptionConverter = httpStatusExceptionConverter;
    }

    @PostMapping
    @Operation(description = "Create a order")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Added"),
        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
        @ApiResponse(responseCode = "409", description = "Duplicated", content = @Content)
    })
    public Mono<ResponseEntity<OrderDTO>> create(@Validated @RequestBody OrderDTO value) {
        return orderUseCase.create(mapper.domainFromDto(value))
            .map(mapper::dtoFromDomain)
            .map(v -> ResponseEntity.status(HttpStatus.CREATED).body(v))
            .onErrorMap(e ->
                new ResponseStatusException(httpStatusExceptionConverter.convert(e), e.getMessage(), e))
            .doOnError(throwable -> LOGGER.error(throwable.getMessage(), throwable));
    }

    @PatchMapping(value = "/{id}", consumes = "application/json-patch+json")
    @Operation(description = "Update a order")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Updated"),
        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
        @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    public Mono<ResponseEntity<OrderDTO>> update(@PathVariable String id,
                                                   @RequestBody String operations) {
        return orderUseCase.update(id, operations)
            .map(mapper::dtoFromDomain)
            .map(ResponseEntity::ok)
            .defaultIfEmpty(ResponseEntity.notFound().build())
            .onErrorMap(e ->
                new ResponseStatusException(httpStatusExceptionConverter.convert(e), e.getMessage(), e))
            .doOnError(throwable -> LOGGER.error(throwable.getMessage(), throwable));
    }

    @GetMapping(produces = TEXT_EVENT_STREAM_VALUE)
    @Operation(description = "List orders")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200"),
        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
        @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    public Flux<OrderDTO> find(@RequestParam(required = false) String id, Pageable pageable) {
        return orderUseCase.list(id, pageable)
            .map(mapper::dtoFromDomain)
            .onErrorMap(e ->
                new ResponseStatusException(httpStatusExceptionConverter.convert(e), e.getMessage(), e))
            .doOnError(throwable -> LOGGER.error(throwable.getMessage(), throwable));
    }
}
