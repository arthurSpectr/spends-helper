package io.spehel.bank.domain;

import io.blend.api.model.Spend;
import io.spehel.bank.BankFacade;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BankFacadeImpl implements BankFacade {

    @Override
    public List<Spend> getSpends() {
        return null;
    }

}
