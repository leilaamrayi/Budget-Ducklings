package com.assignment.Budget.Ducklings.service;

import com.assignment.Budget.Ducklings.repository.UserInvoicesRepository;
import com.assignment.Budget.Ducklings.model.Invoice;
import com.assignment.Budget.Ducklings.model.UserInvoices;

public class UserInvoicesService {
    private UserInvoicesRepository userInvoicesRepository;

    public UserInvoicesService() {
        this.userInvoicesRepository = new UserInvoicesRepository();
    }

    public UserInvoices getInvoices(String username) {
        UserInvoices invoices = userInvoicesRepository.getInvoices(username);
        return invoices;
    }

    public void addInvoice(int userId, Invoice invoice) {
        userInvoicesRepository.addInvoice(userId, invoice);
    }

    public boolean deleteInvoice(int id) {
        return userInvoicesRepository.deleteInvoice(id);
    }

    public Invoice getInvoice(int id) {
        return userInvoicesRepository.getInvoice(id);
    }

    public void updateInvoice(Invoice invoice) {
        userInvoicesRepository.updateInvoice(invoice);
    }

}

