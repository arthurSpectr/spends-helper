package io.spehel.bank.domain;

import io.blend.api.model.Spend;
import io.spehel.bank.BankFacade;
import io.spehel.bank.provider.BankProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.spehel.bank.util.TimestampHelper.createTimestamp;

@Component
public class BankFacadeImpl implements BankFacade {

    @Value("${spehel.bank.monobank.dollarBalanceBlack}")
    private String dollarBalanceBlack;

    @Value("${spehel.bank.monobank.hrivnaBalanceBlack}")
    private String hrivnaBalanceBlack;

    @Value("${spehel.bank.monobank.hrivnaBalanceWhite}")
    private String hrivnaBalanceWhite;

    private final BankProvider provider;

    public BankFacadeImpl(BankProvider provider) {
        this.provider = provider;
    }

    @Override
    public List<Spend> getSpends() {
        long dateFrom = createTimestamp(2021, 9, 0);
        long dateTo = createTimestamp(2021, 10, 0);

        SpentModel[] spends = provider.getSpends(hrivnaBalanceWhite, dateFrom, dateTo);

        return Stream.of(spends).map(SpentModel::toDTO).collect(Collectors.toList());
    }

}
