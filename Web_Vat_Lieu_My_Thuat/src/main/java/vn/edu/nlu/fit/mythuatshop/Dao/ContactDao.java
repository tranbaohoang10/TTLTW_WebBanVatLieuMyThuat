package vn.edu.nlu.fit.mythuatshop.Dao;

import org.jdbi.v3.core.Jdbi;
import vn.edu.nlu.fit.mythuatshop.Model.Contact;

import java.util.List;

public class ContactDao {
    final Jdbi jdbi;

    public ContactDao() {
        this.jdbi = JDBIConnector.getJdbi();
    }

    public List<Contact> findAll() {
        String sql = "SELECT id,userID,fullName,email,phoneNumber,message,status,createAt FROM contacts ORDER BY createAt DESC\n ";
        return jdbi.withHandle(h -> h.createQuery(sql).mapToBean(Contact.class).list());
    }

    public void insert(Contact contact) {
        String sql = "INSERT INTO contacts(userID,fullName,email,phoneNumber,message) "
                + "VALUES (:userId,:fullName,:email,:phoneNumber,:message)";
        jdbi.useHandle(handle -> handle.createUpdate(sql).bindBean(contact).execute());
    }

    public boolean deleteById(int id) {
        String sql = "DELETE FROM contacts WHERE id = :id";
        return jdbi.withHandle(h ->
                h.createUpdate(sql)
                        .bind("id", id)
                        .execute()
        ) > 0;
    }

    public Contact findById(int id) {
        String sql = """
                    SELECT id, userID AS userId, fullName, email, phoneNumber, message, status, createAt
                    FROM contacts
                    WHERE id = :id
                """;
        return jdbi.withHandle(h ->
                h.createQuery(sql)
                        .bind("id", id)
                        .mapToBean(Contact.class)
                        .findOne()
                        .orElse(null)
        );
    }

    public boolean updateStatus(int id, String status) {
        String sql = "UPDATE contacts SET status = :status WHERE id = :id";
        return jdbi.withHandle(h ->
                h.createUpdate(sql)
                        .bind("status", status)
                        .bind("id", id)
                        .execute()
        ) > 0;
    }

}
