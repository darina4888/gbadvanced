package ru.geekbrains;

public class Main {

    public static void main(String[] args) {

        //массив с участниками
        Actions[] members = { new Person(11,1),
                new Cat(7,5),
                new Robot(10,9)};

        //массив с препятствиями
        PassLet[] lets = {new RaceTrack(),new Wall()};

        for (Actions member:members)
            for(PassLet let:lets)
                if(!let.pass(member))  break;
    }
}