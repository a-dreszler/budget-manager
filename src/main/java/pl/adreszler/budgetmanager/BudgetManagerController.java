package pl.adreszler.budgetmanager;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Controller
class BudgetManagerController {

    private final BudgetDao budgetDao;

    public BudgetManagerController(BudgetDao budgetDao) {
        this.budgetDao = budgetDao;
    }

    @GetMapping("/")
    String home(Model model, @RequestParam(required = false) Type type) {
        List<Transaction> allTransactions;

        if (type == Type.INCOME) {
            allTransactions = budgetDao.readAllTransactions().stream()
                    .filter(transaction -> transaction.getType() == Type.INCOME)
                    .collect(Collectors.toList());
        } else if (type == Type.EXPENDITURE) {
            allTransactions = budgetDao.readAllTransactions().stream()
                    .filter(transaction -> transaction.getType() == Type.EXPENDITURE)
                    .collect(Collectors.toList());
        } else {
            allTransactions = budgetDao.readAllTransactions();
        }

        model.addAttribute("allTransactions", allTransactions);
        return "index";
    }

    @GetMapping("/add")
    String add(Model model) {
        model.addAttribute("mode", "add");
        model.addAttribute("transaction", new Transaction());
        return "addOrEdit";
    }

    @PostMapping("/add")
    String add(Model model,
               Transaction transaction) {
        boolean added = budgetDao.saveTransaction(transaction);
        if (!added) {
            model.addAttribute("errorMessage", "Failed to add transaction. Try again");
        }
        return "redirect:/";
    }

    @GetMapping("/edit")
    String edit(Model model, @RequestParam Long id) {
        Transaction transaction = budgetDao.readTransaction(id)
                .orElseThrow(() -> new NoSuchElementException("Failed to find transaction of id " + id));
        model.addAttribute("transaction", transaction);
        model.addAttribute("mode", "edit");

        return "addOrEdit";
    }

    @PostMapping("/edit")
    String edit(Model model, Transaction transaction) {
        boolean updated = budgetDao.updateTransaction(transaction);

        if (!updated) {
            model.addAttribute("Failed to update transaction in the database");
        }

        return "redirect:/";
    }

    @GetMapping("/delete")
    String delete(Model model, @RequestParam Long id) {
        Transaction transaction = budgetDao.readTransaction(id)
                .orElseThrow(() -> new NoSuchElementException("Failed to find transaction of id " + id));
        model.addAttribute("transaction", transaction);

        return "delete";
    }

    @GetMapping("/delete/confirm")
    String confirmDelete(Model model, @RequestParam Long id) {
        boolean deleted = budgetDao.deleteTransaction(id);
        if (!deleted) {
            model.addAttribute("errorMessage", "Failed to delete transaction of id " + id + " from the database");
            return "error";
        }

        return "redirect:/";
    }

    @ExceptionHandler(NoSuchElementException.class)
    String handleTransactionNotFound(Model model, Exception ex) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }
}