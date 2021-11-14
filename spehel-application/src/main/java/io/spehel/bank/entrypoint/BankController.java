package io.spehel.bank.entrypoint;

import io.blend.api.SpehelApi;
import io.blend.api.model.Spend;
import io.spehel.bank.BankFacade;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = {"spehel"})
public class BankController implements SpehelApi {

    private final BankFacade bankFacade;

    public BankController(BankFacade bankFacade) {
        this.bankFacade = bankFacade;
    }

    @Override
    public ResponseEntity<List<Spend>> getSpends() {
        return SpehelApi.super.getSpends();
    }
}
