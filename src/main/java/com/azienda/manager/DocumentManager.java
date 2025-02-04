package com.azienda.manager;

import com.azienda.model.Document;
import com.azienda.utils.DBConnection;
import org.apache.log4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class DocumentManager {

    private static final Logger logger = Logger.getLogger(DocumentManager.class);

    /**
     * Crea (inserisce) un nuovo documento nel DB.
     * @param title   titolo del documento
     * @param content testo o contenuto (stringa) del documento
     */
    public void createDocument(String title, String content) {
        String sql = "INSERT INTO documents (title, content, created_date) VALUES (?,?,?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, title);
            ps.setString(2, content);
            // Per salvare la data corrente come Timestamp
            ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            ps.executeUpdate();

            logger.info("Creato documento: " + title);

        } catch (SQLException e) {
            logger.error("Errore in createDocument", e);
        }
    }

    /**
     * Restituisce la lista di tutti i documenti nel DB.
     */
    public List<Document> listDocuments() {
        List<Document> docs = new ArrayList<>();
        String sql = "SELECT * FROM documents ORDER BY id";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Document doc = new Document();
                doc.setId(rs.getInt("id"));
                doc.setTitle(rs.getString("title"));
                doc.setContent(rs.getString("content"));

                Timestamp ts = rs.getTimestamp("created_date");
                if (ts != null) {
                    doc.setCreatedDate(ts.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
                }
                docs.add(doc);
            }

        } catch (SQLException e) {
            logger.error("Errore in listDocuments", e);
        }
        return docs;
    }

    // Potresti aggiungere altri metodi: searchDocument, updateDocument, deleteDocument, etc.
}
