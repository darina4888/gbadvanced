package ru.geekbrains;

/**
 * Класс препятствие "беговая дорожка"
 */
public class RaceTrack implements PassLet{

    /**
     * длина дорожки
     */
    public int trackLength = 10;

    @Override
    public boolean pass(Actions ob) {
        if(ob.run(trackLength)) {
            System.out.println(ob.getClass().getName() + " успешно пробежал");
            return true;
        }  else System.out.println(ob.getClass().getName() + " не смог пробежать");
        return false;
    }
}