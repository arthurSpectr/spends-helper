package io.spehel.bank.entrypoint;

import io.spehel.bank.BankFacade;
import io.spehel.bank.domain.model.ChartsData;
import io.spehel.bank.domain.model.RangeModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/charts")
public class ChartsController {

    private final BankFacade bankFacade;

    public ChartsController(BankFacade bankFacade) {
        this.bankFacade = bankFacade;
    }

    @GetMapping
    public String charts(HttpServletRequest request, HttpServletResponse response) {

        return "charts";
    }

    @GetMapping("/{chart-id}")
    public String chart(@PathVariable("chart-id") String chartId) {
        return "chart";
    }

    @GetMapping("/info")
    public ResponseEntity<Object> chartsInfo(HttpServletRequest request, HttpServletResponse response) {

        ChartsData allSpendsSortByCategoryInRange = bankFacade.getAllSpendsSortByCategoryInRange(new RangeModel());

        return ResponseEntity.ok(allSpendsSortByCategoryInRange);
    }

}
