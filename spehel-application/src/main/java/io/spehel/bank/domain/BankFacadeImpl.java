package io.spehel.bank.domain;

import io.blend.api.model.Spend;
import io.spehel.bank.BankFacade;
import io.spehel.bank.domain.model.CategoryModel;
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

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class BankFacadeImpl implements BankFacade {

    private static final Logger log = LoggerFactory.getLogger(BankFacadeImpl.class);

    private final CategoriesRepository repository;

    private final SpendsRepository spendsRepository;

    public BankFacadeImpl(CategoriesRepository repository, SpendsRepository spendsRepository) {
        this.repository = repository;
        this.spendsRepository = spendsRepository;
    }

    @Override
    public Paged<Spend> getSpends(RangeModel range, int pageNumber, int size) {
        List<CategoryModel> all = repository.findAll();
        log.info(String.valueOf(all));

        Page<SpentModel> spends;
        Page<Spend> result = Page.empty();

        if(Objects.nonNull(range)) {
            spends = spendsRepository.findAllByTimeBetween(range.getDateFrom().toInstant().getEpochSecond(), range.getDateTo().toInstant().getEpochSecond(), PageRequest.of(pageNumber, size, Sort.by(Sort.Direction.DESC, "time")));
        } else {
            spends = spendsRepository.findAllByTimeBetween(Instant.now().minus(30, ChronoUnit.DAYS).getEpochSecond(), Instant.now().getEpochSecond(), PageRequest.of(pageNumber, size, Sort.by(Sort.Direction.DESC, "time")));
        }

        if(Objects.nonNull(spends)) {
            result = spends.map(SpentModel::toDTO);
        }

        List<Integer> indexes = IntStream.range(size * (pageNumber - 1)+1, size * pageNumber+1).boxed().collect(Collectors.toList());

        return new Paged<>(result, Paging.of(result.getTotalPages(), pageNumber, size), indexes);
    }

}
