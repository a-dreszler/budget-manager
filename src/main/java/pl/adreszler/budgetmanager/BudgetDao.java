package pl.adreszler.budgetmanager;

import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
class BudgetDao {

    private final Connection connection;

    public BudgetDao(Connection connection) {
        this.connection = connection;
    }

    boolean deleteTransaction(Long id) {
        final String sql = """
                DELETE FROM
                    transaction
                WHERE
                    id = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            int rowsDeleted = statement.executeUpdate();

            return rowsDeleted > 0;
        } catch (SQLException e) {
            System.out.println("Nie udało się usunąć transakcji:\n" + e.getMessage());
        }

        return false;
    }

    List<Transaction> readAllTransactions() {
        final String sql = """
                SELECT
                    *
                FROM
                    transaction
                """;
        List<Transaction> transactions = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet transactionResultSet = statement.executeQuery();
            while (transactionResultSet.next()) {
                transactions.add(
                        new Transaction(
                                transactionResultSet.getLong("id"),
                                transactionResultSet.getString("description"),
                                transactionResultSet.getBigDecimal("amount"),
                                transactionResultSet.getObject("date", LocalDate.class),
                                Type.valueOf(transactionResultSet.getString("type")))
                        );
            }
        } catch (SQLException e) {
            System.out.println("Nie udało się wczytać wszystkich transakcji: " + e.getMessage());
        }

        return transactions;
    }

    Optional<Transaction> readTransaction(Long id) {
        final String sql = """
                SELECT
                    *
                FROM
                    transaction
                WHERE
                    id = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(new Transaction(
                        resultSet.getLong("id"),
                        resultSet.getString("description"),
                        resultSet.getBigDecimal("amount"),
                        resultSet.getObject("date", LocalDate.class),
                        Type.valueOf(resultSet.getString("type")))
                );
            }

        } catch (SQLException e) {
            System.out.println("Nie udało się odczytać transakcji:\n" + e.getMessage());
        }

        return Optional.empty();
    }

    boolean saveTransaction(Transaction transaction) {
        final String sql = """
                INSERT INTO transaction (type, description, amount, date)
                VALUES (?, ?, ?, ?)
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, transaction.getType().name());
            statement.setString(2, transaction.getDescription());
            statement.setBigDecimal(3, transaction.getAmount());
            statement.setObject(4, transaction.getDate());
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.out.println("Nie udało się zapisać transakcji:\n" + e.getMessage());
        }

        return false;
    }

    boolean updateTransaction(Transaction transaction) {
        final String sql = """
                UPDATE transaction
                SET
                    type = ?,
                    description = ?,
                    amount = ?,
                    date = ?
                WHERE
                    id = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, transaction.getType().name());
            statement.setString(2, transaction.getDescription());
            statement.setBigDecimal(3, transaction.getAmount());
            statement.setObject(4, transaction.getDate());
            statement.setLong(5, transaction.getId());
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.out.println("Nie udało się zaktualizować transakcji:\n" + e.getMessage());
        }

        return false;
    }
}
