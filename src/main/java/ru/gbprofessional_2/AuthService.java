package ru.gbprofessional_2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class AuthService {

    /**
     * Авторизация клиента
     * @param login логин
     * @param pass пароль
     * @return Client obj
     */
    synchronized Client auth(String login, String pass) throws SQLException {
        Client client = null;
        Connection dbConnection = DbConnection.getInstance().getConnection();
        PreparedStatement statement = dbConnection.prepareStatement("SELECT * FROM user WHERE login = ? AND password = ?;");

        statement.setString(1, login);
        statement.setString(2, pass);

        ResultSet resultSet = statement.executeQuery();

        while(resultSet.next()) {
            client = new Client(resultSet.getString("name"),resultSet.getString("login"),resultSet.getString("password"));
        }

        statement.close();

        return client;
    }
}
