package com.nttdata.bootcamp.msaccounttype.infrastructure;

import com.nttdata.bootcamp.msaccounttype.model.AccountType;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import java.util.UUID;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@Repository
@RequiredArgsConstructor
public class AccountTypeRepository {
    private final ReactiveRedisOperations<String, AccountType> reactiveRedisOperations;

    public Flux<AccountType> findAll() {
        return this.reactiveRedisOperations.<String, AccountType>opsForHash().values("accountTypes");
    }

    public Mono<AccountType> findById(String id) {
        return this.reactiveRedisOperations.<String, AccountType>opsForHash().get("accountTypes", id);
    }

    public Mono<AccountType> save(AccountType accountType) {
        if (accountType.getId() == null) {
            String id = UUID.randomUUID().toString();
            accountType.setId(id);
        }
        return this.reactiveRedisOperations.<String, AccountType>opsForHash()
                .put("accountTypes", accountType.getId(), accountType).log().map(p -> accountType);
    }

    public Mono<Void> delete(AccountType accountType) {
        return this.reactiveRedisOperations.<String, AccountType>opsForHash()
                .remove("accountTypes", accountType.getId())
                .flatMap(p -> Mono.just(accountType)).then();
    }

}
