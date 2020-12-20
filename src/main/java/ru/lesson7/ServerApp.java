package ru.lesson7;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

class Server {
    Map<String,List<Message>> messages = new HashMap<>();
    List<ClientHandler> clients = new ArrayList<>();

    Server() {
        try {
            ServerSocket serverSocket = new ServerSocket(8189);
           AuthService authService = new AuthService();
            /* Обработчик клиентов */
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(() -> {
                    new ClientHandler(authService,this,socket);
                }).start();
            }
        } catch(IOException e) {
            System.out.println("Сервер прекратил работу с ошибкой");
            e.printStackTrace();
        }
    }

    /**
     * Вывод сообщения у всех участников чата
     * @param clientFrom отправитель
     * @param message сообщение
     */
    synchronized void sendMessage(Client clientFrom, String message) {
        for (ClientHandler client : clients) {
            String clientTo = client.client.login;
            sendMessageTo(clientFrom, clientTo, message);
        }
    }

    /**
     * Вывод сообщения конкретному участнику чата
     * @param sender отправитель
     * @param recipient получатель
     * @param msg сообщение
     */
    synchronized void sendMessageTo(Client sender, String recipient, String msg) {

        String nameFrom  = sender.login;

        String key;
        if(nameFrom.compareTo(recipient) > 0) {
            key = sender + recipient;
        } else {
            key = recipient + sender;
        }

        if(!messages.containsKey(key)) {
            messages.put(key, new ArrayList<>());
        }

        messages.get(key).add(new Message(sender,msg));

        ClientHandler clientTo = null;
        for (ClientHandler clientHandler : clients) {
            if (clientHandler.client.login.equals(recipient)) {
                clientTo = clientHandler;
            }
        }

        if(clientTo != null) {
            clientTo.sendMsg(sender,msg);
        } else {
            System.out.println("Клиент " + recipient + " не найден");
        }
    }

    /**
     * Выход участника из чата
     * @param clientHandler
     */
    synchronized void onClientDisconnected(ClientHandler clientHandler) {
        clients.remove(clientHandler);
        sendMessage(clientHandler.client,"Покинул чат");
    }

    /**
     * Добавление участника в чат
     * @param clientHandler
     */
   synchronized void onNewClient(ClientHandler clientHandler) {
        clients.add(clientHandler);
        sendMessage(clientHandler.client,"Вошел в чат");
    }

    public static void main(String[] args) {
        new Server();
    }
}

class Message {
    Client client;
    String message;


    public Message(Client client, String message) {
        this.client = client;
        this.message = message;
    }
}

class Client {
    String name;
    String login;
    String pass;

    public Client(String name, String login, String pass) {
        this.name = name;
        this.login = login;
        this.pass = pass;
    }
}

