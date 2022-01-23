package io.spehel.configuration;

import io.spehel.bank.domain.CategoryResolver;
import io.spehel.bank.domain.model.SpentModelDTO;
import io.spehel.bank.provider.BankProvider;
import io.spehel.spends.domain.SpendsRepository;
import io.spehel.spends.domain.SpentModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MonobankActuator {

    private static final Logger log = LoggerFactory.getLogger(MonobankActuator.class);

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

            resolver.resolveCategories(spends);

            spendsRepository.saveAll(spends.stream().map(SpentModelDTO::toSpendModel).collect(Collectors.toList()));
        } else {
            spends = bankProvider.getSpends(hrivnaBalanceWhite, rangeEnd.getTimeInstant().plus(1, ChronoUnit.SECONDS).toEpochMilli(), Instant.now().toEpochMilli());

            if (spends.isEmpty()) {
                log.info("records are up to date");
            } else {
                log.info("save new records from date {}", rangeEnd.getPrettyTime());
                resolver.resolveCategories(spends);
                spendsRepository.saveAll(spends.stream().map(SpentModelDTO::toSpendModel).collect(Collectors.toList()));
            }
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
