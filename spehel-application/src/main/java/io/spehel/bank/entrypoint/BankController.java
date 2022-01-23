package io.spehel.bank.entrypoint;

import io.blend.api.model.Spend;
import io.spehel.bank.BankFacade;
import io.spehel.bank.domain.model.RangeModel;
import io.spehel.bank.domain.paging.Paged;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BankController {

    private final BankFacade bankFacade;

    public BankController(BankFacade bankFacade) {
        this.bankFacade = bankFacade;
    }

    @GetMapping
    public String getSpends(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                            @RequestParam(value = "size", required = false, defaultValue = "12") int size,
                            Model model) {
        Paged<Spend> spends = bankFacade.getSpends(null, pageNumber, size);
        model.addAttribute("spendsPage", spends);
        model.addAttribute("range", new RangeModel());

        return "spends";
    }

    @PostMapping("/spends")
    public String spendsByDate(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                               @RequestParam(value = "size", required = false, defaultValue = "5") int size,
                               RangeModel rangeModel, Model model) {
        Paged<Spend> spends = bankFacade.getSpends(rangeModel, pageNumber, size);
        model.addAttribute("spendsPage", spends);
        model.addAttribute("range", rangeModel);

        return "spends";
    }

}
