package pl.agntyp.zadanie24;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TransactionController {

    private List<TransactionType> transactionTypes = List.of(TransactionType.values());
    private TransactionDao transactionDao = new TransactionDao();

    public TransactionController() {

    }

    @GetMapping("/")
    public String home(Model model) {

        model.addAttribute("transaction", new Transaction());
        model.addAttribute("types", transactionTypes);

        return "home";
    }

    @PostMapping("/dodaj")
    public String add(Transaction transaction) {
        transactionDao.save(transaction);
        return "redirect:/";
    }

    @GetMapping("/list")
    public String list(@RequestParam TransactionType type, Model model) {

        model.addAttribute("type", type);
        List<Transaction> list = transactionDao.findByType(String.valueOf(type));
        double sum = transactionDao.sumByType(String.valueOf(type));

        model.addAttribute("transactionList", list);
        model.addAttribute("sum", sum);
        return "list";
    }

    @GetMapping("/edit")
    public String editForm(@RequestParam int id, Model model) {
        Transaction transaction = transactionDao.findById(id);
        model.addAttribute("transaction", transaction);
        model.addAttribute("types", transactionTypes);
        return "edit";
    }

    @PostMapping("/edit")
    public String edit(Transaction transaction) {
        if (transactionDao.update(transaction)) {
            return "redirect:/list?type=" + transaction.getType();
        };
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String delete(Transaction transaction) {
        if (transactionDao.delete(transaction.getId())) {
            return "redirect:/list?type=" + transaction.getType();
        };
        return "redirect:/";
    }
}
