package io.spehel.bank.provider;

import com.google.gson.Gson;
import io.spehel.bank.domain.Balance;
import io.spehel.bank.domain.SpentModel;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

@Component
public class MonobankProvider implements BankProvider {

    private final String X_TOKEN_HEADER_NAME = "X-Token";

    @Value("${spehel.bank.monobank.url}")
    private String monobankUrl;

    @Value("${spehel.bank.monobank.xToken}")
    private String xTokenHeaderValue;

    @Override
    public SpentModel[] getSpends(String balance, long dateFrom, long dateTo) {

        SpentModel[] spentModels = null;

        OkHttpClient client = new OkHttpClient();

        Request req = new Request.Builder()
                .get()
                .url(monobankUrl + "/personal/statement/" + balance + "/" + dateFrom + "/" + dateTo)
                .addHeader(X_TOKEN_HEADER_NAME, xTokenHeaderValue)
                .build();

        try {
            Response execute = client.newCall(req).execute();

            String jsonResponse = "";

            if(execute.code() != 200) {
                System.out.println("Bad request");
                jsonResponse = execute.body().string();
                System.out.println(jsonResponse);
            } else {
                jsonResponse = execute.body().string();
                System.out.println(jsonResponse);

                spentModels = new Gson().fromJson(jsonResponse, SpentModel[].class);

                int counter = 0;
                for (SpentModel spentModel : spentModels) {
                    System.out.println("Spent #" + (++counter) + spentModel);
                }

            }

        } catch (IOException e) {
            System.out.println("some exception during performing request");
        }

        return spentModels;
    }

    void printBalances() {
        OkHttpClient client = new OkHttpClient();

        Request req = new Request.Builder()
                .get()
                .url(monobankUrl + "/personal/client-info")
                .addHeader(X_TOKEN_HEADER_NAME, xTokenHeaderValue)
                .build();

        try {
            Response execute = client.newCall(req).execute();

            String jsonResponse = "";

            if(execute.code() != 200) {
                System.out.println("Bad request");
                jsonResponse = execute.body().string();
                System.out.println(jsonResponse);
            } else {
                jsonResponse = execute.body().string();
                System.out.println(jsonResponse);

                Balance balances = new Gson().fromJson(jsonResponse, Balance.class);

                System.out.println(balances);

                System.out.println("=============BALANCES=============");

                for (Balance.Account account : balances.getAccounts()) {
                    System.out.println(account);
                }

            }

        } catch (IOException e) {
            System.out.println("some exception during performing request");
        }
    }

}