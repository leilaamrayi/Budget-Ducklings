package com.assignment.Budget.Ducklings.repository;

import com.assignment.Budget.Ducklings.db.MysqlDatabase;
import com.assignment.Budget.Ducklings.model.AuthUser;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {

    private MysqlDatabase db;

    public UserRepository() {
        db = MysqlDatabase.getInstance();
    }

    public AuthUser getUser(String username, String password) {
        AuthUser user;
        Connection conn = db.getConnection();
        String sql = "" +
                "SELECT * FROM user " +
                "WHERE userName = ? AND password = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            if (!rs.next()) {
                return null;
            }

            user = new AuthUser();
            user.setUsername(rs.getString("userName"));
            user.setPassword(rs.getString("password"));
            user.setId(rs.getInt("userId"));


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return user;
    }
}


