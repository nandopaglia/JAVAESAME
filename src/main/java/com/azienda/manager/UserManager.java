package com.azienda.manager;

import com.azienda.model.User;
import com.azienda.utils.DBConnection;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserManager {

    private static final Logger logger = Logger.getLogger(UserManager.class);

    /**
     * Verifica se esiste un utente con le credenziali specificate
     * Restituisce true se le credenziali sono valide, false altrimenti.
     */
    public boolean checkCredentials(String username, String password) {
        String sql = "SELECT * FROM users WHERE username=? AND password=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // true se trova almeno una riga
            }

        } catch (SQLException e) {
            logger.error("Errore in checkCredentials", e);
            return false;
        }
    }

    /**
     * Crea un nuovo utente nel DB
     */
    public void createUser(String username, String password, String role) {
        String sql = "INSERT INTO users (username, password, role) VALUES (?,?,?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, role);
            ps.executeUpdate();

            logger.info("Creato utente con username: " + username);

        } catch (SQLException e) {
            logger.error("Errore in createUser", e);
        }
    }

    /**
     * Restituisce l'elenco di tutti gli utenti
     */
    public List<User> listUsers() {
        List<User> result = new ArrayList<>();
        String sql = "SELECT * FROM users ORDER BY id";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                User u = new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("role")
                );
                result.add(u);
            }
        } catch (SQLException e) {
            logger.error("Errore in listUsers", e);
        }
        return result;
    }
}
