package io.spehel.configuration;

import io.spehel.bank.domain.CategoryResolver;
import io.spehel.bank.domain.model.SpentModelDTO;
import io.spehel.bank.provider.BankProvider;
import io.spehel.spends.domain.SpendsRepository;
import io.spehel.spends.domain.SpentModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MonobankActuator {

    private static final Logger log = LoggerFactory.getLogger(MonobankActuator.class);

    private String hrivnaBalanceWhite;

    private int monthToStart;

    private final BankProvider bankProvider;

    private final SpendsRepository spendsRepository;

    private final CategoryResolver resolver;

    public MonobankActuator(String hrivnaBalanceWhite, int monthToStart, BankProvider bankProvider, SpendsRepository spendsRepository, CategoryResolver resolver) {
        this.hrivnaBalanceWhite = hrivnaBalanceWhite;
        this.monthToStart = monthToStart;
        this.bankProvider = bankProvider;
        this.spendsRepository = spendsRepository;
        this.resolver = resolver;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void actualizeSpends() {

//        log.info("add indexes for category");
//        mongoTemplate.indexOps(CategoryModel.class.getSimpleName().toLowerCase())
//                .ensureIndex(new Index().on("words", Sort.Direction.ASC));

        List<SpentModelDTO> spends;
        SpentModel rangeEnd = spendsRepository.findByMaxTime();

        if(rangeEnd == null) {
            log.info("save new records from last 30 days");
            spends = accumulateRecords(monthToStart);

            resolveAndSaveSpends(spends, LocalDate.now().plus(30, ChronoUnit.DAYS));
        } else {

            Instant lastTime = rangeEnd.getTimeInstant().plus(1, ChronoUnit.SECONDS);

            long between = ChronoUnit.DAYS.between(lastTime, Instant.now());

            if(between >= 30) {
                while(between != 0) {
                    long step = between > 30 ? 30 : between;
                    spends = bankProvider.getSpends(hrivnaBalanceWhite, lastTime.toEpochMilli(), lastTime.plus(step, ChronoUnit.DAYS).toEpochMilli());
                    lastTime = lastTime.plus(step, ChronoUnit.DAYS);
                    between = ChronoUnit.DAYS.between(lastTime, Instant.now());
                    resolveAndSaveSpends(spends, rangeEnd.getPrettyTime());
                }
            } else {
                spends = bankProvider.getSpends(hrivnaBalanceWhite, rangeEnd.getTimeInstant().plus(1, ChronoUnit.SECONDS).toEpochMilli(), Instant.now().toEpochMilli());
                resolveAndSaveSpends(spends, rangeEnd.getPrettyTime());
            }

        }
    }

    private void resolveAndSaveSpends(List<SpentModelDTO> spends, LocalDate rangeEnd) {
        if (spends.isEmpty()) {
            log.info("records are up to date");
        } else {
            log.info("save new records from date {}", rangeEnd);
            resolver.resolveCategories(spends);
            spendsRepository.saveAll(spends.stream().map(SpentModelDTO::toSpendModel).collect(Collectors.toList()));
        }
    }
    
    public List<SpentModelDTO> accumulateRecords(int monthToStart) {
        List<SpentModelDTO> spends = new ArrayList<>();

        for (int i = 1; i < monthToStart + 1; i++) {
            int startRatio = 30 * i;
            int endRatio = startRatio - 30;
            List<SpentModelDTO> monthExpenses = bankProvider.getSpends(hrivnaBalanceWhite,
                    Instant.now().minus(startRatio, ChronoUnit.DAYS).toEpochMilli(), Instant.now().minus(endRatio, ChronoUnit.DAYS).toEpochMilli());
            spends.addAll(monthExpenses);
        }

        return spends;
    }

}
