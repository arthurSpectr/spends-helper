package io.spehel.bank.domain;

import io.blend.api.model.Spend;
import io.spehel.bank.BankFacade;
import io.spehel.bank.domain.model.Category;
import io.spehel.bank.domain.model.ChartsData;
import io.spehel.bank.domain.model.RangeModel;
import io.spehel.bank.domain.paging.Paged;
import io.spehel.bank.domain.paging.Paging;
import io.spehel.spends.domain.SpendsRepository;
import io.spehel.spends.domain.SpentModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class BankFacadeImpl implements BankFacade {

    private static final Logger log = LoggerFactory.getLogger(BankFacadeImpl.class);

    private final SpendsRepository spendsRepository;

    public BankFacadeImpl(SpendsRepository spendsRepository) {
        this.spendsRepository = spendsRepository;
    }

    @Override
    public Paged<Spend> getSpendsInRange(RangeModel range, int pageNumber, int size) {
        Page<SpentModel> spends;
        Page<Spend> result = Page.empty();

        if(Objects.nonNull(range) && (range.getDateFrom() != null && range.getDateTo() != null)) {
            spends = spendsRepository.findAllByTimeBetween(range.getDateFrom().toInstant().getEpochSecond(), range.getDateTo().toInstant().getEpochSecond(), PageRequest.of(pageNumber, size, Sort.by(Sort.Direction.DESC, "time")));
        } else {
            spends = spendsRepository.findAllByTimeBetween(Instant.now().minus(90, ChronoUnit.DAYS).getEpochSecond(), Instant.now().getEpochSecond(), PageRequest.of(pageNumber, size, Sort.by(Sort.Direction.DESC, "time")));
        }

        log.info("spends records queried size {}", spends.getTotalElements());

        if(Objects.nonNull(spends)) {
            result = spends.map(SpentModel::toDTO);
        }

        List<Integer> indexes = IntStream.range(size * (pageNumber - 1)+1, size * pageNumber+1).boxed().collect(Collectors.toList());

        return new Paged<>(result, Paging.of(result.getTotalPages(), pageNumber, size), indexes);
    }

    @Override
    public ChartsData getAllSpendsSortByCategoryInRange(RangeModel range) {
        List<List<String>> result = new ArrayList<>();
        long sum = 0L;

        DecimalFormat df = new DecimalFormat("0.00");

        Map<Category, Double> spendsByCategories = new EnumMap<>(Category.class);

        List<SpentModel> spends = spendsRepository.findAllByTimeBetween(Instant.now().minus(90, ChronoUnit.DAYS).getEpochSecond(), Instant.now().getEpochSecond());

        for (SpentModel spend : spends) {
            String categoryString = extractCategory(spend.getDescription());
            Category category = Category.fromString(categoryString);

            if(Category.SALARY_AND_INCOME.equals(category)) continue;

            if(spendsByCategories.get(category) == null) {
                spendsByCategories.put(category, (spend.getAmount().doubleValue() / 100));
            } else {
                Double amount = spendsByCategories.get(category);
                spendsByCategories.put(category, (amount + (spend.getAmount().doubleValue() / 100)));
            }
        }

        for (Map.Entry<Category, Double> entry : spendsByCategories.entrySet()) {
            String amount;

            if(entry.getValue() < 0) {
                amount = df.format(entry.getValue() * -1);
                sum+= entry.getValue() * -1;
            } else {
                amount = df.format(entry.getValue());
                sum+= entry.getValue();
            }


            result.add(List.of(entry.getKey().name(), amount));
        }

        return new ChartsData(sum, result);
    }

    @Override
    public Paged<Spend> getSpendsByCategoryInRange(Category category, RangeModel range, int pageNumber, int size) {
        Page<SpentModel> spends;
        Page<Spend> result = Page.empty();

        if(Objects.nonNull(range) && (range.getDateFrom() != null && range.getDateTo() != null)) {
            spends = spendsRepository.findAllByTimeBetween(range.getDateFrom().toInstant().getEpochSecond(), range.getDateTo().toInstant().getEpochSecond(), PageRequest.of(pageNumber, size, Sort.by(Sort.Direction.DESC, "time")));
        } else {
            spends = spendsRepository.findAllByTimeBetween(Instant.now().minus(90, ChronoUnit.DAYS).getEpochSecond(), Instant.now().getEpochSecond(), PageRequest.of(pageNumber, size, Sort.by(Sort.Direction.DESC, "time")));
        }

        log.info("spends records queried size {}", spends.getTotalElements());

        if(Objects.nonNull(spends)) {
            result = spends.map(SpentModel::toDTO);
        }

        List<Integer> indexes = IntStream.range(size * (pageNumber - 1)+1, size * pageNumber+1).boxed().collect(Collectors.toList());

        return new Paged<>(result, Paging.of(result.getTotalPages(), pageNumber, size), indexes);
//        return null;
    }

    private String extractCategory(String spentModelDescription) {
        String substring = spentModelDescription.substring(spentModelDescription.lastIndexOf(" ")+1);

        return Category.fromString(substring).name();
    }
}
