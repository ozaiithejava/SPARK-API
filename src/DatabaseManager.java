import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Bu sınıf, veritabanı işlemlerini yönetir.
 */
public class DatabaseManager {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/your_database_name";
    private static final String USERNAME = "your_username";
    private static final String PASSWORD = "your_password";

    /**
     * Veritabanına bağlantı sağlar.
     * @return Veritabanı bağlantısı
     * @throws SQLException Bağlantı hatası durumunda fırlatılır
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }

    /**
     * Veritabanı bağlantısını kapatır.
     * @param connection Kapatılacak bağlantı
     */
    public static void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Kullanıcı doğrulaması yapar.
     * @param username Kullanıcı adı
     * @param password Şifre
     * @return Kullanıcı doğrulama sonucu
     */
    public static boolean isValidUser(String username, String password) {
        // Gerçek projelerde şifreleri güvenli bir şekilde saklamak için daha kapsamlı bir yöntem kullanılmalıdır.
        String hashedPassword = hashPassword(password);

        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, hashedPassword);

            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next(); // Eğer sonuç dönerse, kullanıcı doğrudur.
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Yeni kullanıcı kaydı oluşturur.
     * @param username Kullanıcı adı
     * @param password Şifre
     * @param email E-posta
     * @return Kayıt sonucu
     */
    public static boolean registerUser(String username, String password, String email) {
        String hashedPassword = hashPassword(password);

        String query = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, hashedPassword);
            statement.setString(3, email);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexStringBuilder = new StringBuilder(2 * bytes.length);
        for (byte b : bytes) {
            hexStringBuilder.append(String.format("%02x", b));
        }
        return hexStringBuilder.toString();
    }
}
