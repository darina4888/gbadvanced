package ru.lesson3;

import java.util.*;

public class Phonebook {

    private HashMap<String,String> phonebook;

    public Phonebook()
    {
        phonebook = new HashMap<>();
        phonebook.put("09323133", "Sazonov");
        phonebook.put("345323233", "Lemegov");
        phonebook.put("345345345", "Ivanov");
    }

    public void add(Map<String, String> phonebook,  String phone,String lastname) {
        if(phonebook.containsKey(phone))
            System.out.println("Cannot add phone number. '" + phone + "' already exists in the directory ");
        else
            phonebook.put(phone,lastname);
    }

    public Set<String> get(Map<String, String> phonebook, String lastname)
    {
        Set<String> phones = new HashSet<>();

        for (Map.Entry entry : phonebook.entrySet())
            if (Objects.equals(lastname, entry.getValue()))
                phones.add((String) entry.getKey());

        return phones;
    }

    public void print(Set<String> values,String lastname) {
        if(values.isEmpty())
            System.out.println("No records for user " + lastname);
        else {
            System.out.println("Phones for lastname '" + lastname + "' : ");
            for (String value: values)
                System.out.println(value);
        }

    }

    public HashMap<String, String> getPhonebook() {
        return phonebook;
    }

    public void setPhonebook(HashMap<String, String> phonebook_) {
        phonebook = phonebook_;
    }
}
