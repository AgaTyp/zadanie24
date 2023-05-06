package pl.agntyp.zadanie24;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TransactionController {

    private List<TransactionType> transactionTypes = List.of(TransactionType.values());
    private TransactionDao transactionDao = new TransactionDao();

    public TransactionController() {

    }

    @GetMapping("/")
    public String home(Model model) {

//        List<Category> validCategories = new ArrayList<>();
//        for (Category category : Category.values()) {
//            if (category != Category.WSZYSTKO) {
//                validCategories.add(category);
//            }
//        }
//        model.addAttribute("validCategories", validCategories);
        model.addAttribute("transaction", new Transaction());
//        model.addAttribute("products", products);
        model.addAttribute("types", transactionTypes);

        return "home";
    }

    @PostMapping("/dodaj")
    public String add(Transaction transaction) {
        transactionDao.save(transaction);
        return "redirect:/";
    }
}
