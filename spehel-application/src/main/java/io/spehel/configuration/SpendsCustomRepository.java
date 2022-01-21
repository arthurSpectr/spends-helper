package io.spehel.configuration;

import java.util.List;

public interface SpendsCustomRepository {
    List<SpentModel> findAllByTime(Long dateFrom, Long dateTo);

    SpentModel findByMaxTime();

    SpentModel findByMinTime();
}
