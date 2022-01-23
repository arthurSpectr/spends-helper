package io.spehel.bank.provider;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.spehel.bank.domain.model.SpentModelDTO;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Component
public class MonobankProvider implements BankProvider {

    private static final Logger log = LoggerFactory.getLogger(MonobankProvider.class);

    private final String X_TOKEN_HEADER_NAME = "X-Token";

    @Value("${spehel.bank.monobank.url}")
    private String monobankUrl;

    @Value("${spehel.bank.monobank.xToken}")
    private String xTokenHeaderValue;

    @Override
    public List<SpentModelDTO> getSpends(String balance, long dateFrom, long dateTo) {

        List<SpentModelDTO> spentModels = null;

        OkHttpClient client = new OkHttpClient();

        Request req = new Request.Builder()
                .get()
                .url(monobankUrl + "/personal/statement/" + balance + "/" + dateFrom + "/" + dateTo)
                .addHeader(X_TOKEN_HEADER_NAME, xTokenHeaderValue)
                .build();

        try {
            Response execute = client.newCall(req).execute();

            if(execute.code() != 200) {
                log.info("Bad request");
                String jsonResponse = execute.body().string();
                log.info(jsonResponse);
            } else {
                String jsonResponse = execute.body().string();

                Type userListType = new TypeToken<ArrayList<SpentModelDTO>>(){}.getType();

                spentModels = new Gson().fromJson(jsonResponse, userListType);
            }

        } catch (IOException e) {
            log.error("some exception during performing request");
        }

        return spentModels;
    }
}