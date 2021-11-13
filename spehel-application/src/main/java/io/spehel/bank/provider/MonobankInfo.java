package io.spehel.bank.provider;

//import com.google.gson.Gson;
//import io.spehel.bank.domain.Balance;
//import io.spehel.bank.domain.SpentModel;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class MonobankInfo {

    private static final String MONOBANK_URL = "https://api.monobank.ua";
    private static final String X_TOKEN_HEADER_NAME = "X-Token";
    private static final String X_TOKEN_HEADER_VALUE = "ucPcKJkQzKyPcQLQIiWqINYiKeZvKhv_VrNFtlb8TyKc";

    private static final String DOLLAR_BALANCE_BLACK = "ryhGec43gBCMdzCFG7k3_A";
    private static final String UAH_BALANCE_BLACK = "P5e_ivygxvD1QGrqZhyRpg";
    private static final String UAH_BALANCE_WHITE = "g0ZzhwWPTHrZf6ZlByCeqg";

    public static void main(String[] args) {

        long dateFrom = createTimestamp(2021, 9, 0);
        long dateTo = createTimestamp(2021, 10, 0);

        printSpends(UAH_BALANCE_WHITE, dateFrom, dateTo);

//        printBalances();

    }

    static void printSpends(String balance, long dateFrom, long dateTo) {
//        OkHttpClient client = new OkHttpClient();
//
//        Request req = new Request.Builder()
//                .get()
//                .url(MONOBANK_URL + "/personal/statement/" + balance + "/" + dateFrom + "/" + dateTo)
//                .addHeader(X_TOKEN_HEADER_NAME, X_TOKEN_HEADER_VALUE)
//                .build();
//
//        try {
//            Response execute = client.newCall(req).execute();
//
//            String jsonResponse = "";
//
//            if(execute.code() != 200) {
//                System.out.println("Bad request");
//                jsonResponse = execute.body().string();
//                System.out.println(jsonResponse);
//            } else {
//                jsonResponse = execute.body().string();
//                System.out.println(jsonResponse);
//
//                SpentModel[] spentModels = new Gson().fromJson(jsonResponse, SpentModel[].class);
//
//                int counter = 0;
//                for (SpentModel spentModel : spentModels) {
//                    System.out.println("Spent #" + (++counter) + spentModel);
//                }
//
//            }
//
//        } catch (IOException e) {
//            System.out.println("some exception during performing request");
//        }
    }

    static void printBalances() {
//        OkHttpClient client = new OkHttpClient();
//
//        Request req = new Request.Builder()
//                .get()
//                .url(MONOBANK_URL + "/personal/client-info")
//                .addHeader(X_TOKEN_HEADER_NAME, X_TOKEN_HEADER_VALUE)
//                .build();
//
//        try {
//            Response execute = client.newCall(req).execute();
//
//            String jsonResponse = "";
//
//            if(execute.code() != 200) {
//                System.out.println("Bad request");
//                jsonResponse = execute.body().string();
//                System.out.println(jsonResponse);
//            } else {
//                jsonResponse = execute.body().string();
//                System.out.println(jsonResponse);
//
//                Balance balances = new Gson().fromJson(jsonResponse, Balance.class);
//
//                System.out.println(balances);
//
//                System.out.println("=============BALANCES=============");
//
//                for (Balance.Account account : balances.getAccounts()) {
//                    System.out.println(account);
//                }
//
//            }
//
//        } catch (IOException e) {
//            System.out.println("some exception during performing request");
//        }
    }

    static long createTimestamp(int yearFrom, int monthFrom, int dayFrom) {
        Date dateFrom = new Date();
        Calendar calendar = new Calendar.Builder().setDate(yearFrom, monthFrom, dayFrom).build();

        System.out.println(calendar.getTime());
        return calendar.getTimeInMillis();
    }

    static void createTimestamp(int yearFrom, int monthFrom, int dayFrom, int today) {

    }
}