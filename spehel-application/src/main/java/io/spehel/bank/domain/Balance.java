
package io.spehel.bank.domain;

import java.util.List;
public class Balance {

    private List<Account> accounts;
    private String id;
    private String name;
    private String webHookUrl;

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebHookUrl() {
        return webHookUrl;
    }

    public void setWebHookUrl(String webHookUrl) {
        this.webHookUrl = webHookUrl;
    }

    static class Account {

        private Long balance;
        private String cashbackType;
        private Long creditLimit;
        private Long currencyCode;
        private String id;
        private String type;

        public Long getBalance() {
            return balance;
        }

        public void setBalance(Long balance) {
            this.balance = balance;
        }

        public String getCashbackType() {
            return cashbackType;
        }

        public void setCashbackType(String cashbackType) {
            this.cashbackType = cashbackType;
        }

        public Long getCreditLimit() {
            return creditLimit;
        }

        public void setCreditLimit(Long creditLimit) {
            this.creditLimit = creditLimit;
        }

        public Long getCurrencyCode() {
            return currencyCode;
        }

        public void setCurrencyCode(Long currencyCode) {
            this.currencyCode = currencyCode;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return "Account{" +
                    "balance=" + balance +
                    ", cashbackType='" + cashbackType + '\'' +
                    ", creditLimit=" + creditLimit +
                    ", currencyCode=" + currencyCode +
                    ", id='" + id + '\'' +
                    ", type='" + type + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Balance{" +
                "accounts=" + accounts.size() +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", webHookUrl='" + webHookUrl + '\'' +
                '}';
    }
}


