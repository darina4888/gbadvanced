package ru.geekbrains;

/**
 * Класс препятствие "стена"
 */
public class Wall implements PassLet{

    /**
     * высота стены
     */
    public int wallHeight = 10;

    @Override
    public boolean pass(Actions ob) {
        if(ob.jump(wallHeight)) {
            System.out.println(ob.getClass().getName() + " успешно перепрыгнул");
            return true;
        }  else System.out.println(ob.getClass().getName() + " не смог перепрыгнуть");
        return false;
    }
}