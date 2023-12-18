import static spark.Spark.*;

/**
 * Bu sınıf, uygulamanın başlangıç noktasını oluşturur ve Spark framework'ünü başlatır.
 */
public class Main {

    public static void main(String[] args) {
        // Sunucu portu belirlenir (örneğin: 4567)
        port(4567);

        // Veritabanı tablolarını oluşturmak için TableManager kullanılır
        TableManager tableManager = new TableManager();
        tableManager.createTables();

        // HTTP isteği geldiğinde öncelikle bu metot çalışır
        before((request, response) -> {
            // CORS (Cross-Origin Resource Sharing) ayarları
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            response.header("Access-Control-Allow-Headers", "Content-Type, Authorization");
        });

        // Router sınıfının örneği oluşturulur
        Router router = new Router();

        // Login endpoint'i tanımlanır
        post("/login", router::loginRoute);

        // Register endpoint'i tanımlanır
        post("/register", router::registerRoute);

        // Logout endpoint'i tanımlanır
        post("/logout", router::logoutRoute);

        // Diğer route'lar ve metotlar burada eklenebilir.

        // Uygulama sonlandığında Spark'ı kapat
        after((request, response) -> {
            response.type("application/json");
        });

        // Uygulama sonlandığında Spark'ı kapat
        Runtime.getRuntime().addShutdownHook(new Thread(Main::stop));
    }
}
