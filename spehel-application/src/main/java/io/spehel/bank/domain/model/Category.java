package io.spehel.bank.domain.model;

public enum Category {

    PRIVATE, PRODUCTS, RESTAURANTS_AND_DELIVERY, ENTERTAINMENT, HOUSING, DEBT_LOAN_INSTALLMENTS, DEPOSITS, NASTYA_SCHOOL, COFFEE,
    PHARMACY, MOBILE_CONNECTION, CASH_WITHDRAWAL, FOR_NASTYA, SUBSCRIPTIONS, MONEY_TRANSFER, TAXI_TRANSPORT, TICKETS, DONATIONS, SALARY_AND_INCOME, UNRECOGNISED;

    public static Category fromString(String category) {

        for (Category value : Category.values()) {
            if(value.name().equals(category)) return value;
        }

        throw new IllegalArgumentException();

    }

    @Override
    public String toString() {
        return super.toString();
    }
}
