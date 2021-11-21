package io.spehel.bank.entrypoint;

import io.blend.api.model.Spend;
import io.spehel.bank.BankFacade;
import io.spehel.bank.domain.model.RangeModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class BankController {

    private final BankFacade bankFacade;

    public BankController(BankFacade bankFacade) {
        this.bankFacade = bankFacade;
    }

    @GetMapping("/spends")
    public String getSpends(Model model) {
        List<Spend> spends = bankFacade.getSpends(null);
        model.addAttribute("spends", spends);
        model.addAttribute("range", new RangeModel());

        return "spends";
    }

    @PostMapping("/spends")
    public String spendsByDate(RangeModel rangeModel, Model model) {
        List<Spend> spends = bankFacade.getSpends(rangeModel);
        model.addAttribute("spends", spends);
        model.addAttribute("range", rangeModel);

        return "spends";
    }

}
