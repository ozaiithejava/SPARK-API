import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Bu sınıf, veritabanı tablolarını yönetir.
 */
public class TableManager {

    private static final String CREATE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS users (" +
            "id INT AUTO_INCREMENT PRIMARY KEY," +
            "username VARCHAR(255) NOT NULL," +
            "password VARCHAR(255) NOT NULL," +
            "email VARCHAR(255) NOT NULL" +
            ")";

    /**
     * Veritabanında "users" tablosunu oluşturur (eğer yoksa).
     *
     * @param connection Veritabanı bağlantısı
     * @param statement  SQL ifadesi oluşturucu
     */
    public static void createTableIfNotExists(Connection connection, Statement statement) {
        try {
            statement.executeUpdate(CREATE_TABLE_QUERY);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
