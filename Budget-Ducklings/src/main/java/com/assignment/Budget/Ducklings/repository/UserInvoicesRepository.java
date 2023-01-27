package com.assignment.Budget.Ducklings.repository;

import com.assignment.Budget.Ducklings.db.MysqlDatabase;
import com.assignment.Budget.Ducklings.model.Invoice;
import com.assignment.Budget.Ducklings.model.UserInvoices;
import java.sql.*;


public class UserInvoicesRepository {

    private MysqlDatabase db;

    public UserInvoicesRepository() {
        db = MysqlDatabase.getInstance();
    }

    public UserInvoices getInvoices(String username) {
        Connection conn = db.getConnection();
        UserInvoices userInvoices = new UserInvoices(username);
        String sql = "" +
                "SELECT * FROM invoice " +
                "JOIN user " +
                "ON invoice.userId = user.userId " +
                "WHERE user.userName = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();

            if(!rs.next()) { return userInvoices; }

            System.out.println(username);
            do {
                Invoice invoice = new Invoice();
                invoice.setInvoiceId(rs.getInt("InvoiceId"));
                invoice.setDate(rs.getDate("Date"));
                invoice.setTitle(rs.getString("Title"));
                invoice.setDescription(rs.getString("Description"));
                invoice.setCategory(rs.getString("Category"));
                invoice.setPrice(rs.getDouble("Price"));

                userInvoices.getInvoices().add(invoice);
            } while(rs.next());


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return userInvoices;
    }

    public void addInvoice(int userId, Invoice invoice) {
        Connection conn = db.getConnection();

        try {
            String sql = "INSERT INTO invoice (userId, Date, Title, Description, Category, Price)" +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            pstmt.setDate(2,new java.sql.Date(invoice.getDate().getTime()) );
            pstmt.setString(3, invoice.getTitle());
            pstmt.setString(4, invoice.getDescription());
            pstmt.setString(5, invoice.getCategory());
            pstmt.setDouble(6, invoice.getPrice());

            pstmt.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteInvoice(int id) {
        Connection conn = db.getConnection();
        String sql = "DELETE FROM invoice WHERE InvoiceId = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, id);
            return stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Invoice getInvoice(int id) {
        Invoice invoice = null;
        Connection conn = db.getConnection();
        String sql = "" +
                "SELECT * FROM invoice " +
                "WHERE InvoiceId = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();

            if(!rs.next()) { return null; }

            invoice = new Invoice();
            invoice.setInvoiceId(rs.getInt("InvoiceId"));
            invoice.setDate(rs.getDate("Date"));
            invoice.setTitle(rs.getString("Title"));
            invoice.setDescription(rs.getString("Description"));
            invoice.setCategory(rs.getString("Category"));
            invoice.setPrice(rs.getDouble("Price"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return invoice;
    }

    public void updateInvoice(Invoice invoice) {
        Connection conn = db.getConnection();
        String sql = "UPDATE invoice SET Date = ?, Title = ?, Description = ?, Category = ?, Price = ?"
                    +" WHERE InvoiceId = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDate(1,new java.sql.Date(invoice.getDate().getTime()) );
            pstmt.setString(2, invoice.getTitle());
            pstmt.setString(3, invoice.getDescription());
            pstmt.setString(4, invoice.getCategory());
            pstmt.setDouble(5, invoice.getPrice());
            pstmt.setInt(6, invoice.getInvoiceId());
            pstmt.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}