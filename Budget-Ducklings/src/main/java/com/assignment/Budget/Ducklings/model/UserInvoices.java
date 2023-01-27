package com.assignment.Budget.Ducklings.model;

import java.util.ArrayList;
import java.util.List;

public class UserInvoices {
    private List <Invoice> invoices;
//    private String owner;

    public UserInvoices(String owner){
//        this.owner=owner;
        this.invoices=new ArrayList<>();

    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

//    public String getOwner() {
//        return owner;
//    }
}
