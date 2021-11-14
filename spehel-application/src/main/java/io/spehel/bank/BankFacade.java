package io.spehel.bank;

import io.blend.api.model.Spend;

import java.util.List;

public interface BankFacade {

    List<Spend> getSpends();

}
