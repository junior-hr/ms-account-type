package com.nttdata.bootcamp.msaccounttype.application;

import com.nttdata.bootcamp.msaccounttype.exception.ResourceNotFoundException;
import com.nttdata.bootcamp.msaccounttype.infrastructure.AccountTypeRepository;
import com.nttdata.bootcamp.msaccounttype.model.AccountType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AccountTypeServiceImpl implements AccountTypeService {

    @Autowired
    private AccountTypeRepository accountTypeRepository;

    @Override
    public Flux<AccountType> findAll() {
        return accountTypeRepository.findAll();
    }

    @Override
    public Mono<AccountType> findById(String idAccountType) {
        return Mono.just(idAccountType)
                .flatMap(accountTypeRepository::findById)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Account Type", "idAccountType", idAccountType)));
    }

    @Override
    public Mono<AccountType> save(AccountType accountType) {
        return Mono.just(accountType)
                .flatMap(dc -> accountTypeRepository.save(dc));
    }

    @Override
    public Mono<AccountType> update(AccountType accountType, String idAccountType) {
        return accountTypeRepository.findById(idAccountType)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Account Type", "idAccountType", idAccountType)))
                .flatMap(dcu -> {
                    dcu.setAccountType(accountType.getAccountType());
                    dcu.setValue(accountType.getValue());
                    return accountTypeRepository.save(dcu);
                });
    }

    @Override
    public Mono<Void> delete(String idAccountType) {
        return accountTypeRepository.findById(idAccountType)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Account Type", "idAccountType", idAccountType)))
                .flatMap(dcu -> accountTypeRepository.delete(dcu));
    }
}
