package io.spehel.spends.domain;

import java.util.List;

public interface SpendsCustomRepository {
    List<SpentModel> findAllByTime(Long dateFrom, Long dateTo);

    SpentModel findByMaxTime();
}
