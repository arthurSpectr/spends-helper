package io.spehel.configuration;

import io.spehel.bank.domain.CategoryResolver;
import io.spehel.bank.provider.BankProvider;
import io.spehel.spends.domain.SpendsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
public class MonoBankConfiguration {

    @Value("${spehel.bank.monobank.hrivnaBalanceWhite}")
    private String hrivnaBalanceWhite;

    @Value("${spehel.monthToStart}")
    private int monthToStart;

    @Autowired
    private BankProvider bankProvider;

    @Autowired
    private SpendsRepository spendsRepository;

    @Autowired
    private CategoryResolver resolver;

    @Bean
    @DependsOn("redisActuator")
    public MonobankActuator monobankActuator() {
        return new MonobankActuator(hrivnaBalanceWhite, monthToStart, bankProvider, spendsRepository, resolver);
    }

}
