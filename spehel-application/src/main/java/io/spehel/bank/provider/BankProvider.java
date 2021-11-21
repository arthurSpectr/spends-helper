package io.spehel.bank.provider;

import io.spehel.bank.domain.model.SpentModel;

public interface BankProvider {
    SpentModel[] getSpends(String balance, long dateFrom, long dateTo);
}
