package io.spehel.bank.domain.paging;

import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class Paged<T> {

    private Page<T> page;

    private Paging paging;

    private List<Integer> indexes = new ArrayList<>();

    public Paged(Page<T> page, Paging paging, List<Integer> indexes) {
        this.page = page;
        this.paging = paging;
        this.indexes = indexes;
    }

    public Paged() {
    }

    public Page<T> getPage() {
        return page;
    }

    public void setPage(Page<T> page) {
        this.page = page;
    }

    public Paging getPaging() {
        return paging;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }

    public List<Integer> getIndexes() {
        return indexes;
    }

    public void setIndexes(List<Integer> indexes) {
        this.indexes = indexes;
    }
}
