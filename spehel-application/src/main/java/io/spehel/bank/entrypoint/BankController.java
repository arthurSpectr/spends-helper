package io.spehel.bank.entrypoint;

import io.blend.api.SpehelApi;
import io.blend.api.model.Spend;
import io.spehel.bank.BankFacade;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
//@Api(tags = {"spehel"})
public class BankController {

    private final BankFacade bankFacade;

    public BankController(BankFacade bankFacade) {
        this.bankFacade = bankFacade;
    }

    @GetMapping("/spends")
    public String getSpends(Model model) {
        List<Spend> spends = bankFacade.getSpends();
        model.addAttribute("spends", spends);

        return "spends";
    }
}
