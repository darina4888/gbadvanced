package ru.lesson2;

public class Exceptions {
    public static void main(String[] args) {
        String[][]arr = {
                {"4","7","90","56"},
                {"5","7","8","76"},
                {"4","32","17","34"},
                {"56","32","17","89"}
        };
        try {
            System.out.println(sum(arr));
        } catch (MySizeArrayException | MyArrayDataException e) {
            System.out.println(e.getMessage());
        }
    }

    private static int sum(String[][] arr) {
        String errMsg = "Размер массива должен быть 4*4.";
        if(arr.length != 4)
            throw new MySizeArrayException(errMsg);

        int sum  = 0;
        for(int i = 0; i < arr.length; i++) {
            if(arr[i].length != 4)
                throw new MySizeArrayException(errMsg);
            for(int j = 0; j < arr[i].length; j++)
                try {
                    sum += Integer.parseInt(arr[i][j]);
                } catch (NumberFormatException e){
                    throw new MyArrayDataException("Элемент "+ i + " - " + j + " не является числом");
                }
        }

        return sum;
    }
}

class MyArrayDataException extends RuntimeException{
    public MyArrayDataException(String s) {
        super(s);
    }
}

class MySizeArrayException extends RuntimeException{
    public MySizeArrayException(String s) {
        super(s);
    }
}
