package com.assignment.Budget.Ducklings.controller;


import com.assignment.Budget.Ducklings.model.Invoice;
import com.assignment.Budget.Ducklings.model.UserInvoices;
import com.assignment.Budget.Ducklings.service.UserInvoicesService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;



@Controller
@RequestMapping("/invoice")
public class UserInvoicesController {
    private UserInvoicesService userInvoicesService;

    public UserInvoicesController() {
        userInvoicesService = new UserInvoicesService();
    }

    @GetMapping
    protected String showUserInvoices(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");

        UserInvoices userInvoices = userInvoicesService.getInvoices(username);

        model.addAttribute("invoices", userInvoices.getInvoices());
        model.addAttribute("user", username);

        return "invoiceList";

    }

    @GetMapping("/new")
    public String invoiceRegistrationPage(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        model.addAttribute("user", username);
        return "invoiceRegistrationPage";
    }


    @PostMapping
    public String addInvoice(HttpSession session, @ModelAttribute Invoice invoice) {
        int userId = (int) session.getAttribute("userId");

        userInvoicesService.addInvoice(userId, invoice);

        return "redirect:/invoice";
    }

    @GetMapping("/delete/{id}")
    public String deleteInvoice(@PathVariable("id") int id){
        userInvoicesService.deleteInvoice(id);
        return "redirect:/invoice";
    }

    @GetMapping("/editpage/{id}")
    public String editPage(@PathVariable("id") int id, Model model, HttpSession session){
        Invoice invoice = userInvoicesService.getInvoice(id);
        model.addAttribute("invoice", invoice);
        model.addAttribute("user", session.getAttribute("username"));
        return "invoiceEditPage";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Invoice invoice) {

        userInvoicesService.updateInvoice(invoice);

        return "redirect:/invoice";
    }

}