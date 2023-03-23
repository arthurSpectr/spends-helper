package io.spehel.bank.domain.model;

import java.util.List;
import java.util.Objects;

public class ChartsData {

    private Long totalSpendsSum;

    private List<List<String>> totalSpends;

    public ChartsData(Long totalSpendsSum, List<List<String>>  totalSpends) {
        Objects.requireNonNull(totalSpends);
        this.totalSpendsSum = totalSpendsSum;
        this.totalSpends = totalSpends;
    }

    public Long getTotalSpendsSum() {
        return totalSpendsSum;
    }

    public List<List<String>> getTotalSpends() {
        return totalSpends;
    }
}
