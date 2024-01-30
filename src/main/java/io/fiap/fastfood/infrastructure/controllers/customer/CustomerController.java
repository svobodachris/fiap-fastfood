package io.fiap.fastfood.infrastructure.controllers.customer;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE;

import io.fiap.fastfood.domain.application.exception.HttpStatusExceptionConverter;
import io.fiap.fastfood.domain.application.usecases.CustomerUseCase;
import io.fiap.fastfood.infrastructure.controllers.customer.dto.CustomerDTO;
import io.fiap.fastfood.infrastructure.gateways.CustomerMapper;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/v1/customers", produces = APPLICATION_JSON_VALUE)
public class CustomerController {

    private static final Logger LOGGER = getLogger(CustomerController.class);
    private final CustomerMapper mapper;
    private final CustomerUseCase customerUseCase;
    private final HttpStatusExceptionConverter httpStatusExceptionConverter;

    public CustomerController(CustomerMapper mapper,
                              CustomerUseCase customerUseCase,
                              HttpStatusExceptionConverter httpStatusExceptionConverter) {
        this.mapper = mapper;
        this.customerUseCase = customerUseCase;
        this.httpStatusExceptionConverter = httpStatusExceptionConverter;
    }

    @PostMapping
    @Operation(description = "Create a customer")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Added"),
        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
        @ApiResponse(responseCode = "409", description = "Duplicated", content = @Content)
    })
    public Mono<ResponseEntity<CustomerDTO>> create(@Validated @RequestBody CustomerDTO value) {
        return customerUseCase.create(mapper.domainFromDto(value))
            .map(mapper::dtoFromDomain)
            .map(v -> ResponseEntity.status(HttpStatus.CREATED).body(v))
            .onErrorMap(e ->
                new ResponseStatusException(httpStatusExceptionConverter.convert(e), e.getMessage(), e))
            .doOnError(throwable -> LOGGER.error(throwable.getMessage(), throwable));
    }

    @PatchMapping(value = "/{id}", consumes = "application/json-patch+json")
    @Operation(description = "Update a customer")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Updated"),
        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
        @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    public Mono<ResponseEntity<CustomerDTO>> update(@PathVariable String id,
                                                    @RequestBody String operations) {
        return customerUseCase.update(id, operations)
            .map(mapper::dtoFromDomain)
            .map(ResponseEntity::ok)
            .defaultIfEmpty(ResponseEntity.notFound().build())
            .onErrorMap(e ->
                new ResponseStatusException(httpStatusExceptionConverter.convert(e), e.getMessage(), e))
            .doOnError(throwable -> LOGGER.error(throwable.getMessage(), throwable));
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Delete a customer")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Deleted."),
        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
        @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
        return customerUseCase.delete(id)
            .map(__ -> new ResponseEntity<Void>(HttpStatus.NO_CONTENT))
            .defaultIfEmpty(ResponseEntity.noContent().build())
            .onErrorMap(e ->
                new ResponseStatusException(httpStatusExceptionConverter.convert(e), e.getMessage(), e))
            .doOnError(throwable -> LOGGER.error(throwable.getMessage(), throwable));
    }

    @GetMapping(produces = TEXT_EVENT_STREAM_VALUE)
    @Operation(description = "List customers")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200"),
        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
        @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    public Flux<CustomerDTO> find(Pageable pageable) {
        return customerUseCase.list(pageable)
            .map(mapper::dtoFromDomain)
            .onErrorMap(e ->
                new ResponseStatusException(httpStatusExceptionConverter.convert(e), e.getMessage(), e))
            .doOnError(throwable -> LOGGER.error(throwable.getMessage(), throwable));
    }
}
