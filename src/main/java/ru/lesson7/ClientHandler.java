package ru.lesson7;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

class ClientHandler {
    AuthService authService;
    Server server;
    Socket socket;
    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;
    Client client;

    ClientHandler(AuthService authService,Server server,Socket socket) {
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
        } catch (IOException e) {
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
    private boolean auth(DataInputStream dataInputStream,DataOutputStream dataOutputStream) throws IOException {

        dataOutputStream.writeUTF("Введите логин и пароль через пробел :");

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
                } else { // сообщение всем клиентам
                    server.sendMessage(client, newMsg);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
