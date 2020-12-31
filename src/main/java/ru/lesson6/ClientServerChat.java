package ru.lesson6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

class ClientServerChat {
    public ClientServerChat(Socket socket,String nameFrom) throws IOException, InterruptedException {
        //get message
        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
        Thread thread1 = new Thread(() -> {
            try {
                while (true) {
                    String msg = dataInputStream.readUTF();
                    System.out.println(nameFrom + " : " + msg);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //send message
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        Scanner scanner = new Scanner(System.in);
        Thread thread2 =new Thread(() -> {
            try {
                while(true) {
                    String line = scanner.nextLine();
                    dataOutputStream.writeUTF(line);
                }
            } catch(IOException e) {
                e.printStackTrace();
            }
        });
        thread1.start();
        thread2.start();
    }
}

class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 8189);
            new ClientServerChat(socket,"Server");
        } catch(IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8189);
            Socket socket = serverSocket.accept();
            new ClientServerChat(socket,"Client");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}


