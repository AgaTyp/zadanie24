package pl.agntyp.zadanie24;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDao {
    private DataSource dataSource;

    TransactionDao() {
        try {
            this.dataSource = DataSourceProvider.getDataSource();
        } catch (NamingException e) {
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
            statement.setString(4, transaction.getDate());
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
        final String sql = "SELECT id, type, description, amount, date) from transaction ORDER BY date";
        List<Transaction> transactions = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String type = resultSet.getString("type");
                String description = resultSet.getString("description");
                Double amount = resultSet.getDouble("amount");
                String date = resultSet.getString("date");

                transactions.add(new Transaction(type, description, amount, date));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return transactions;
    }
}
