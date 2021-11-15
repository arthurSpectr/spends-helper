package io.spehel.bank.provider;

import io.spehel.bank.domain.SpentModel;

public interface BankProvider {
    SpentModel[] getSpends(String balance, long dateFrom, long dateTo);
}
