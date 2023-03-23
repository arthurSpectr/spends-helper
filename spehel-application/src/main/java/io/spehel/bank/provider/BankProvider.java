package io.spehel.bank.provider;

import io.spehel.bank.domain.model.SpentModelDTO;

import java.util.List;

public interface BankProvider {
    List<SpentModelDTO> getSpends(String balance, long dateFrom, long dateTo);
}
