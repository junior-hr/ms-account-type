package com.nttdata.bootcamp.msaccounttype.model;

import org.springframework.data.annotation.Id;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Class AccountType.
 * AccountType microservice class AccountType.
 */
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AccountType {

    @Id
    private String id;

    @NotNull
    private String accountType;

    @NotNull
    private String value;
    
}
