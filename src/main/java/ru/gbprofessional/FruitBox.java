package ru.gbprofessional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FruitBox<T extends Fruit> {
    private final List<T> container;

    private int capacity;

    public FruitBox(int capacity, T ...fruits) {
        this.container = arrayToList(fruits);
        this.capacity = capacity;
    }

    /**
     * высчитывает вес коробки
     * @return вес
     */
    public float getWeight() {
        float weight = 0.0f;

        for(T fruit : container) {
            weight += fruit.getWeight();
        }

        return weight;
    }

    /**
     * Сравнивает вес коробок
     * @param box
     * @return
     */
    public boolean compare(FruitBox<?> box) {
        return Math.abs(this.getWeight() - box.getWeight()) < 0.01;
    }

    /**
     * пересыпание фруктов в другую коробку
     * @param box
     */
    public void pourFruitsToBox(FruitBox<T> box) {
        if (box == this) return;

        int size = Math.min(container.size(),box.capacity);

        List<T> fruits = container.subList(0, size);
        box.container.addAll(fruits);
        container.removeAll(fruits);

        box.capacity -= size;
        capacity += size;
    }

    /**
     * Добавление фрукта в коробку
     * @param fruit
     */
    public void addFruit(T fruit) {
        if(capacity - 1 > 0) {
            container.add(fruit);
            capacity--;
        }
    }

    /**
     * преобразует массив в ArrayList
     * @param arr
     * @param <T>
     * @return
     */
    public static <T> List<T> arrayToList(T[] arr) {
        return new ArrayList<>(Arrays.asList(arr));
    }
}
