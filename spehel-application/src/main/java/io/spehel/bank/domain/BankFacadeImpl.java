package io.spehel.bank.domain;

import com.google.common.collect.Lists;
import io.blend.api.model.Spend;
import io.spehel.bank.BankFacade;
import io.spehel.bank.domain.model.CategoryModel;
import io.spehel.bank.domain.model.RangeModel;
import io.spehel.spends.domain.SpendsRepository;
import io.spehel.spends.domain.SpentModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class BankFacadeImpl implements BankFacade {

    private static final Logger log = LoggerFactory.getLogger(BankFacadeImpl.class);

    @Value("${spehel.bank.monobank.dollarBalanceBlack}")
    private String dollarBalanceBlack;

    @Value("${spehel.bank.monobank.hrivnaBalanceBlack}")
    private String hrivnaBalanceBlack;

    @Value("${spehel.bank.monobank.hrivnaBalanceWhite}")
    private String hrivnaBalanceWhite;

    private final CategoriesRepository repository;

    private final SpendsRepository spendsRepository;

    private final CategoryResolver resolver;

    public BankFacadeImpl(CategoriesRepository repository, SpendsRepository spendsRepository, CategoryResolver resolver) {
        this.repository = repository;
        this.spendsRepository = spendsRepository;
        this.resolver = resolver;
    }

    @Override
    public List<Spend> getSpends(RangeModel range) {
        List<CategoryModel> all = repository.findAll();
        log.info(String.valueOf(all));

        List<SpentModel> spends;
        List<Spend> result = new ArrayList<>();

        if(Objects.nonNull(range)) {
            spends = spendsRepository.findAllByTime(range.getDateFrom().toInstant().getEpochSecond(), range.getDateTo().toInstant().getEpochSecond());
        } else {
            spends = spendsRepository.findAllByTime(Instant.now().minus(30, ChronoUnit.DAYS).getEpochSecond(), Instant.now().getEpochSecond());
        }

        if(Objects.nonNull(spends)) {
            List<SpentModel> spentModels = resolver.resolveCategories(spends);

            List<Spend> tempList = spentModels.stream().map(SpentModel::toDTO).collect(Collectors.toList());
            result =  Lists.reverse(tempList);
        }

        return result;
    }

}
