package ru.gbprofessional_2;

import ru.lesson7.DbConnection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class ClientHandler {
    AuthService authService;
    Server server;
    Socket socket;
    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;
    Client client;

    ClientHandler(AuthService authService, Server server, Socket socket) {
        this.server = server;
        this.socket = socket;
        this.authService = authService;

        try {
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());

            if(!auth(dataInputStream,dataOutputStream)) {
                dataInputStream.close();
                dataOutputStream.close();
                socket.close();
                server.onClientDisconnected(this);
                return;
            }
            server.onNewClient(this);
            messageListener(dataInputStream);
        } catch (IOException | SQLException e) {
            try {
                dataOutputStream.close();
                dataInputStream.close();
                socket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            server.onClientDisconnected(this);
            e.printStackTrace();
        }
    }

    /**
     * Вывод сообщения
     * @param client отправитель
     * @param message сообщение
     */
    void sendMsg(Client client,String message) {
        try {
            dataOutputStream.writeUTF(client.name + ": " + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Авторизация клиента в чат
     * @param dataInputStream
     * @param dataOutputStream
     * @return
     * @throws IOException
     */
    private boolean auth(DataInputStream dataInputStream,DataOutputStream dataOutputStream) throws IOException, SQLException {

        int tryCnt = 0;

        while (true) {
            String authData = dataInputStream.readUTF();

            String[] messageData = authData.split("\\s");
            if(messageData.length == 3 && messageData[0].equals("/auth")) {
                tryCnt++;

                String login = messageData[1];
                String pass = messageData[2];
                client = authService.auth(login,pass);
                if(client != null) {
                    dataOutputStream.writeUTF("ok");
                    break;
                } else {
                    dataOutputStream.writeUTF("Неправильный логин или пароль");
                }
            } else {
                dataOutputStream.writeUTF("Ошибка авторизации");
            }
            if(tryCnt == 5) {
                dataOutputStream.writeUTF(("Превышен лимит попыток"));
                dataInputStream.close();
                dataOutputStream.close();
                socket.close();
                return false;
            }
        }
        return true;
    }

    /**
     * обработка входящего сообщения
     * @param dataInputStream
     */
    private void messageListener(DataInputStream dataInputStream) {
        while (true) {
            try {
                String newMsg = dataInputStream.readUTF();
                if(newMsg.equals("/exit")) { // выход из чата
                    dataInputStream.close();
                    dataOutputStream.close();
                    socket.close();
                    server.onClientDisconnected(this);
                } else if(newMsg.startsWith("/w ")) { //адресное сообщение клиенту
                    String msg = newMsg.substring(3);

                    int msgIndex = msg.indexOf(" ");
                    String nick = msg.substring(0, msgIndex);
                    String message = msg.substring(msgIndex);

                    server.sendMessageTo(client, nick, message);
                } else if(newMsg.startsWith("/changeNick")) {//смена ника
                    changeNick(newMsg.substring(12));

                    server.sendMessageTo(client, client.login, "Ник обновлен успешно");
                } else { // сообщение всем клиентам
                    server.sendMessage(client, newMsg);
                }
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void changeNick(String nick) throws SQLException {
        Connection dbConnection = DbConnection.getInstance().getConnection();
        PreparedStatement statement = dbConnection.prepareStatement("UPDATE user set name = ? WHERE login = ?;");

        statement.setString(1, nick);
        statement.setString(2, client.login);

        statement.executeUpdate();
        statement.close();

        client.name = nick;
    }
}
