package ru.lesson3;

import java.util.*;

public class Collections {
    public static void main(String[] args) {
        String[] words = {"London","Paris","Moscow","New-York","London","Brazil","Chile","Colombia","Paris","Canada","London"};
        List<String> list = new ArrayList<>(Arrays.asList(words));

        //List of unique words
        Set<String>set = new HashSet<>(list);

        //Number of repetitions for each value in the map
        Map<String,Integer>map = new HashMap<>();

        for(String el : list)
            if(map.get(el) == null)
                map.put(el,1);
            else if(map.containsKey(el))
                map.put(el,map.get(el)+1);

        for (Map.Entry entry : map.entrySet())
           System.out.println(entry.getKey() + " - " + entry.getValue());


        /*Phonebook*/
        Phonebook phonebook = new Phonebook();
        phonebook.add(phonebook.getPhonebook(),"1234567","Ivanov");
        phonebook.add(phonebook.getPhonebook(),"33355566","Sidorov");
        phonebook.add(phonebook.getPhonebook(),"999888866","Ivanov");
        phonebook.add(phonebook.getPhonebook(),"999888866","Karakov");
        phonebook.add(phonebook.getPhonebook(),"99988111","Sidorov");

        String lastname = "Lemegov";
        Set<String> phones = phonebook.get(phonebook.getPhonebook(),lastname);
        phonebook.print(phones,lastname);
    }
}
