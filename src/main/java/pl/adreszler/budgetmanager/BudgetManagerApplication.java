package pl.adreszler.budgetmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@SpringBootApplication
public class BudgetManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BudgetManagerApplication.class, args);
    }

    @Bean
    Connection connection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/budget", "root", "admin");
        } catch (SQLException e) {
            throw new RuntimeException("Nie udało się połączyć z bazą danych:\n" + e.getMessage());
        }
    }
}
