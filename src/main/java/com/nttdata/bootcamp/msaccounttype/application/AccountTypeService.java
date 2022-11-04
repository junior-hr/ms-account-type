package com.nttdata.bootcamp.msaccounttype.application;

import com.nttdata.bootcamp.msaccounttype.model.AccountType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountTypeService {
    public Flux<AccountType> findAll();
    public Mono<AccountType> findById(String idAccountType);
    public Mono<AccountType> save(AccountType accountType);
    public Mono<AccountType> update(AccountType accountType, String idAccountType);
    public Mono<Void> delete(String idAccountType);

}
