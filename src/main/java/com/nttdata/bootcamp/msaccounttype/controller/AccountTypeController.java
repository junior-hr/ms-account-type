package com.nttdata.bootcamp.msaccounttype.controller;

import com.nttdata.bootcamp.msaccounttype.application.AccountTypeService;
import com.nttdata.bootcamp.msaccounttype.model.AccountType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import javax.validation.Valid;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/accounttype")
@Slf4j
public class AccountTypeController {

    @Autowired
    private AccountTypeService accountTypeService;

    @GetMapping
    public Mono<ResponseEntity<Flux<AccountType>>> listAccountTypes() {
        return Mono.just(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(accountTypeService.findAll()));
    }

    @GetMapping("/{idAccountType}")
    public Mono<ResponseEntity<AccountType>> getAccountTypeDetails(@PathVariable("idAccountType") String idAccountType) {
        return accountTypeService.findById(idAccountType)
                .map(c -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                        .body(c))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<Map<String, Object>>> saveAccountType(@Valid @RequestBody Mono<AccountType> accountType) {
        Map<String, Object> request = new HashMap<>();
        return accountType
                .flatMap(pType -> accountTypeService.save(pType).map(baSv -> {
                            request.put("AccountType", baSv);
                            request.put("message", "Tipo de cuenta guardado con exito");
                            request.put("timestamp", new Date());
                            return ResponseEntity.created(URI.create("/api/accounttype/".concat(baSv.getId())))
                                    .contentType(MediaType.APPLICATION_JSON).body(request);
                        })
                );
    }

    @PutMapping("/{idAccountType}")
    public Mono<ResponseEntity<AccountType>> editAccountType(@Valid @RequestBody AccountType accountType, @PathVariable("idAccountType") String idAccountType) {
        return accountTypeService.update(accountType, idAccountType)
                .map(c -> ResponseEntity.created(URI.create("/api/accounttype/".concat(idAccountType)))
                        .contentType(MediaType.APPLICATION_JSON).body(c));
    }

    @DeleteMapping("/{idAccountType}")
    public Mono<ResponseEntity<Void>> deletePersonType(@PathVariable("idAccountType") String idAccountType) {
        return accountTypeService.delete(idAccountType)
                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
    }
}
