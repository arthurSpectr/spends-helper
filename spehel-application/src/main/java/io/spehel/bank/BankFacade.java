package io.spehel.bank;

import io.blend.api.model.Spend;
import io.spehel.bank.domain.model.RangeModel;

import java.util.List;

public interface BankFacade {

    List<Spend> getSpends(RangeModel range);

}
