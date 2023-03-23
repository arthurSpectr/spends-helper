package io.spehel.bank;

import io.blend.api.model.Spend;
import io.spehel.bank.domain.model.Category;
import io.spehel.bank.domain.model.ChartsData;
import io.spehel.bank.domain.model.RangeModel;
import io.spehel.bank.domain.paging.Paged;

public interface BankFacade {

    Paged<Spend> getSpendsInRange(RangeModel range, int pageNumber, int size);

    ChartsData getAllSpendsSortByCategoryInRange(RangeModel range);

    Paged<Spend> getSpendsByCategoryInRange(Category category, RangeModel rangeModel, int pageNumber, int size);
}
