package io.spehel.bank.domain;

import com.google.common.collect.Lists;
import io.blend.api.model.Spend;
import io.spehel.bank.BankFacade;
import io.spehel.bank.domain.model.CategoriesWords;
import io.spehel.bank.domain.model.RangeModel;
import io.spehel.bank.domain.model.SpentModel;
import io.spehel.bank.provider.BankProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.spehel.bank.util.TimestampHelper.createTimestamp;

@Component
public class BankFacadeImpl implements BankFacade {

    private static final Logger log = LoggerFactory.getLogger(BankFacadeImpl.class);

    @Autowired
    private CategoriesWordsRepository repository;

    @Value("${spehel.bank.monobank.dollarBalanceBlack}")
    private String dollarBalanceBlack;

    @Value("${spehel.bank.monobank.hrivnaBalanceBlack}")
    private String hrivnaBalanceBlack;

    @Value("${spehel.bank.monobank.hrivnaBalanceWhite}")
    private String hrivnaBalanceWhite;

    private final BankProvider provider;
    private final CategoryResolver resolver;

    public BankFacadeImpl(BankProvider provider, CategoryResolver resolver) {
        this.provider = provider;
        this.resolver = resolver;
    }

    @Override
    public List<Spend> getSpends(RangeModel range) {
        List<CategoriesWords> all = repository.findAll();
        log.info(String.valueOf(all));

        SpentModel[] spends;
        List<Spend> result = new ArrayList<>();

        if(Objects.nonNull(range)) {
            spends = provider.getSpends(hrivnaBalanceWhite, range.getDateFrom().getTime(), range.getDateTo().getTime());
        } else {
            long dateFrom = createTimestamp(2021, 9, 0);
            long dateTo = createTimestamp(2021, 10, 0);
            spends = provider.getSpends(hrivnaBalanceWhite, dateFrom, dateTo);
        }

        if(Objects.nonNull(spends)) {
//            SpentModel[] spentModels = resolver.resolveCategories(spends);

            List<Spend> tempList = Stream.of(spends).map(SpentModel::toDTO).collect(Collectors.toList());
            result =  Lists.reverse(tempList);
        }

        return result;
    }

}
