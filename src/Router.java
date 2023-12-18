import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Bu sınıf, Spark framework kullanarak API endpoint'lerini yönetir.
 */
public class Router {

    private static final String SECRET_KEY = "your_secret_key";

    /**
     * Kullanıcı girişini sağlayan endpoint.
     * @param request HTTP isteği
     * @param response HTTP cevabı
     * @return JSON formatında API yanıtı
     */
    public static Route loginRoute = (Request request, Response response) -> {
        String username = request.queryParams("username");
        String password = request.queryParams("password");

        // Kullanıcı adı ve şifre doğrulaması yapılır
        if (DatabaseManager.isValidUser(username, password)) {
            String jwt = JwtManager.generateJwt(username);
            return new Gson().toJson(new ApiResponse(true, "Login successful", jwt));
        } else {
            response.status(401);
            return new Gson().toJson(new ApiResponse(false, "Invalid credentials", null));
        }
    };

    /**
     * Yeni kullanıcı kaydı oluşturan endpoint.
     * @param request HTTP isteği
     * @param response HTTP cevabı
     * @return JSON formatında API yanıtı
     */
    public static Route registerRoute = (Request request, Response response) -> {
        String username = request.queryParams("username");
        String password = request.queryParams("password");
        String email = request.queryParams("email");

        // Kullanıcı kaydı yapılır
        if (DatabaseManager.registerUser(username, password, email)) {
            return new Gson().toJson(new ApiResponse(true, "Registration successful", null));
        } else {
            response.status(400);
            return new Gson().toJson(new ApiResponse(false, "Registration failed. Username may be taken.", null));
        }
    };

    /**
     * Kullanıcı çıkışını sağlayan endpoint.
     * @param request HTTP isteği
     * @param response HTTP cevabı
     * @return JSON formatında API yanıtı
     */
    public static Route logoutRoute = (Request request, Response response) -> {
        String authorizationHeader = request.headers("Authorization");

        // JWT'den kullanıcı adını al
        String username = JwtManager.getUsernameFromRequest(authorizationHeader);

        // Oturumu sonlandırma işlemi gerçekleştirilir
        // (Gerçek projelerde oturum yönetimi daha karmaşık olabilir)
        if (username != null) {
            // Burada kullanıcı adına göre oturumu sonlandırma işlemi yapabilirsiniz.
            // Örneğin: Oturumu sonlandırma veritabanında bir tabloda tutularak yönetilebilir.
            return new Gson().toJson(new ApiResponse(true, "Logout successful", null));
        } else {
            response.status(401);
            return new Gson().toJson(new ApiResponse(false, "Unauthorized", null));
        }
    };

    // Diğer route'lar ve metotlar burada eklenebilir.
}
