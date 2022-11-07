package com.nttdata.bootcamp.msaccounttype;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import com.nttdata.bootcamp.msaccounttype.model.AccountType;
import lombok.RequiredArgsConstructor;

/**
 * Class MsAccountTypeApplication Main.
 * AccountType microservice class MsAccountTypeApplication.
 */
@SpringBootApplication
@EnableEurekaClient
@RequiredArgsConstructor
public class MsAccountTypeApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsAccountTypeApplication.class, args);
    }

    @Bean
    public ReactiveRedisTemplate<String, AccountType> reactiveJsonPostRedisTemplate(
            ReactiveRedisConnectionFactory connectionFactory) {

        RedisSerializationContext<String, AccountType>
                serializationContext = RedisSerializationContext
                .<String, AccountType>newSerializationContext(new StringRedisSerializer())
                .hashKey(new StringRedisSerializer())
                .hashValue(new Jackson2JsonRedisSerializer<>(AccountType.class))
                .build();
        return new ReactiveRedisTemplate<>(connectionFactory, serializationContext);
    }

}
