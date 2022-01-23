package io.spehel.bank;

import io.blend.api.model.Spend;
import io.spehel.bank.domain.model.RangeModel;
import io.spehel.bank.domain.paging.Paged;

public interface BankFacade {

    Paged<Spend> getSpends(RangeModel range, int pageNumber, int size);

}
