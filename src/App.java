import java.sql.DriverManager;
import com.mysql.jdbc.Driver;

import frontend.Home;

public class App {
    public static void main(String[] args) throws Exception {
        DriverManager.registerDriver(new Driver());
        new Home();
    }
}
