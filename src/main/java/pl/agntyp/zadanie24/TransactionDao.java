package pl.agntyp.zadanie24;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransactionDao {
    private DataSource dataSource;

    TransactionDao() {
        try {
            this.dataSource = DataSourceProvider.getDataSource();
        } catch (NamingException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    void save(Transaction transaction) {
        final String sql = "INSERT INTO transaction (type, description, amount, date) VALUES(?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, transaction.getType());
            statement.setString(2, transaction.getDescription());
            statement.setDouble(3, transaction.getAmount());
            statement.setDate(4, Date.valueOf(transaction.getDate()));
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                transaction.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    List<Transaction> findAll() {
        final String sql = "SELECT id, type, description, amount, date from transaction ORDER BY date DESC";
        List<Transaction> transactions = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            addTransactionToList(transactions, statement);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return transactions;
    }

    List<Transaction> findByType(String type) {
        final String sql = "SELECT id, type, description, amount, date from transaction WHERE type = ? ORDER BY date DESC";
        List<Transaction> transactions = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, type);
            addTransactionToList(transactions, statement);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return transactions;
    }

    Transaction findById(Integer id) {
        final String sql = "SELECT id, type, description, amount, date from transaction WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int transactionId = resultSet.getInt("id");
                String transactionType = resultSet.getString("type");
                String description = resultSet.getString("description");
                double amount = resultSet.getDouble("amount");
                LocalDate date = resultSet.getDate("date").toLocalDate();
                return new Transaction(transactionId, transactionType, description, amount, date);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    double sumByType(String type) {
        final String sql = "SELECT sum(amount) from transaction WHERE type = ? GROUP BY type";
        double sum = 0.0;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, type);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                sum += resultSet.getDouble("sum");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return sum;
    }

    boolean delete(int id) {
        final String sql = "DELETE FROM transaction WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            int updatedRows = statement.executeUpdate();
            return updatedRows != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    boolean update(Transaction transaction) {
        final String sql = "UPDATE transaction SET type = ?, description = ?, amount = ?, date = ? WHERE id =?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, transaction.getType());
            statement.setString(2, transaction.getDescription());
            statement.setDouble(3, transaction.getAmount());
            statement.setDate(4, Date.valueOf(transaction.getDate()));
            statement.setInt(5, transaction.getId());
            int updatedRows = statement.executeUpdate();
            return updatedRows != 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void addTransactionToList(List<Transaction> transactions, PreparedStatement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Integer transactionId = resultSet.getInt("id");
            String transactionType = resultSet.getString("type");
            String description = resultSet.getString("description");
            Double amount = resultSet.getDouble("amount");
            LocalDate date = resultSet.getDate("date").toLocalDate();

            transactions.add(new Transaction(transactionId, transactionType, description, amount, date));
        }
    }
}
