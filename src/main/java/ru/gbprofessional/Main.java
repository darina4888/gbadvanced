package ru.gbprofessional;

public class Main {
    public static void main(String[] args) {
        FruitBox<Apple> appleFruitBox = new FruitBox<>(5, new Apple(), new Apple());
        appleFruitBox.addFruit(new Apple());
        appleFruitBox.addFruit(new Apple());
        System.out.println(appleFruitBox.getWeight());

        FruitBox<Orange> orangeFruitBox = new FruitBox<>(3, new Orange(), new Orange());
        System.out.println(orangeFruitBox.compare(appleFruitBox));

        FruitBox<Apple> appleFruitBox2 = new FruitBox<>(2);
        appleFruitBox.pourFruitsToBox(appleFruitBox2);
        System.out.println(appleFruitBox.getWeight());
    }

    public static <T> void swapArrayEl(T[] arr,int el1,int el2) {
        T element = arr[el2];
        arr[el2] = arr[el1];
        arr[el1] = element;
    }
}
