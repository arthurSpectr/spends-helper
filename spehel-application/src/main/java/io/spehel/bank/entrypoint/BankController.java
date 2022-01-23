package io.spehel.bank.entrypoint;

import io.blend.api.model.Spend;
import io.spehel.bank.BankFacade;
import io.spehel.bank.domain.model.RangeModel;
import io.spehel.bank.domain.paging.Paged;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.util.Date;

@Controller
public class BankController {

    private final BankFacade bankFacade;

    public BankController(BankFacade bankFacade) {
        this.bankFacade = bankFacade;
    }

    @GetMapping
    public String getSpends(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                            @RequestParam(value = "size", required = false, defaultValue = "12") int size,
                            @Nullable @CookieValue(value = "startDate") String startDate,
                            @Nullable @CookieValue(value = "endDate") String endDate,
                            Model model) {

        RangeModel rangeModel = new RangeModel();
        if(startDate != null && !startDate.isBlank() && endDate != null && !endDate.isBlank()) {
            Long start = Long.valueOf(startDate);
            Long end = Long.valueOf(endDate);

            rangeModel.setDateFrom(new Date(start));
            rangeModel.setDateTo(new Date(end));
        }

        Paged<Spend> spends = bankFacade.getSpends(rangeModel, pageNumber, size);
        model.addAttribute("spendsPage", spends);
        model.addAttribute("range", rangeModel);

        return "spends";
    }

    @PostMapping
    public String spendsByDate(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                               @RequestParam(value = "size", required = false, defaultValue = "12") int size,
                               RangeModel rangeModel, Model model,
                               HttpServletResponse response) {
        Paged<Spend> spends = bankFacade.getSpends(rangeModel, pageNumber, size);
        model.addAttribute("spendsPage", spends);
        model.addAttribute("range", rangeModel);

        Cookie startDate = new Cookie("startDate", String.valueOf(rangeModel.getDateFrom().getTime()));
        Cookie endDate = new Cookie("endDate", String.valueOf(rangeModel.getDateTo().getTime()));
        response.addCookie(startDate);
        response.addCookie(endDate);

        return "spends";
    }

    // Maybe it is not good idea to redirect and call one more time getSpends endpoint
    @GetMapping("/clear")
    public String clear(HttpServletRequest request, HttpServletResponse response) {

        if(request.getCookies() != null && request.getCookies().length > 0) {
            Cookie startDate = new Cookie("startDate", null);
            startDate.setMaxAge(0);
            startDate.setSecure(true);
            startDate.setHttpOnly(true);

            Cookie endDate = new Cookie("endDate", null);
            endDate.setMaxAge(0);
            endDate.setSecure(true);
            endDate.setHttpOnly(true);

            response.addCookie(startDate);
            response.addCookie(endDate);
        }

        return "redirect:/";
    }

}
