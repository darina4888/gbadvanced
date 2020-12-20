package ru.lesson7;

import java.util.ArrayList;
import java.util.List;

class AuthService {
    List<Client> clients = new ArrayList();

    AuthService() {
        clients.add(new Client("Pavel","pavel1","pass"));
        clients.add(new Client("Oleg","oleg","pass"));
        clients.add(new Client("Masha","masha1","pass"));
        clients.add(new Client("Gena","gena","pass"));
    }

    /**
     * Авторизация клиента
     * @param login логин
     * @param pass пароль
     * @return Client obj
     */
    synchronized Client auth(String login,String pass) {
        for(int i = 0; i < clients.size(); i++) {
            Client client = clients.get(i);
            if(client.login.equals(login) && client.pass.equals(pass)) {
                return client;
            }
        }
        return null;
    }
}
