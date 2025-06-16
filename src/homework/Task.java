package homework;

import homework.exception.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Task {

    private Task() {
        throw new AssertionError("Создание экземпляров - запрещено!");
    }

    // Константа для 6 задания
    private static final Map<String, String> itemsMap = Map.of(
            "A123", "Ноутбук",
            "B456", "Смартфон",
            "C789", "Наушники"
    );

    // Константа для 8 задания
    static final String CORRECT_USERNAME = "admin";
    static final String CORRECT_PASSWORD = "qwerty123";

    // Константа и геттер для 10 задания
    private static List<Integer> ratings = new ArrayList<>();

    public static List<Integer> getRatings() {
        return ratings;
    }

    // 1. Безопасное деление
    public static int safeDivide(int a, int b) {
        if (b == 0) {
            throw new IllegalArgumentException("Деление на ноль запрещено");
        }
        return a / b;
    }

    //  2. Проверка строки
    public static void validateString(String str) {
        if (str == null || str.trim().isEmpty()) {
            throw new IllegalArgumentException("Строка пустая или состоит только из пробелов");
        }
    }

    // 3. Преобразование строки в число
    public static List<Integer> parseNumbers(List<String> strings) {
        List<Integer> numbers = new ArrayList<>();
        for (String s : strings) {
            try {
                int num = Integer.parseInt(s);
                numbers.add(num);
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: '" + s + "' не является числом");
            }
        }
        return numbers;
    }

    // 4. Простая валидация возраста
    public static void setAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Возраст не может быть отрицательным: " + age);
        }
        System.out.println("Возраст установлен: " + age);
    }

    // 5. Собственное исключение: депозит
    public static void deposit(double amount) throws NegativeDepositException {
        if (amount < 0) {
            throw new NegativeDepositException(amount);
        }
        System.out.println("Успешно внесено: " + amount);
    }

    // 6. Поиск товара по коду
    public static String getItem(String code) {
        if (!itemsMap.containsKey(code)) {
            throw new ItemNotFoundException(code);
        }
        return itemsMap.get(code);
    }

    // 7. Чтение из файла
    public static List<String> readFile(String path) {
        List<String> lines = new ArrayList<>();

        try (
                BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }
        return lines;
    }

    // 8. Система логина
    public static void login(String username, String password) throws LoginFailedException {
        if (username == null || password == null) {
            throw new LoginFailedException("Логин и пароль не могут быть пустым");
        }
        if (!username.equals(CORRECT_USERNAME) || !password.equals(CORRECT_PASSWORD)) {
            throw new LoginFailedException("Неверный логин или пароль");
        }

        System.out.println("Аутентификация успешно пройдена!");
    }

    // 9. Банковский перевод с валидацией
    public static void transfer(double fromAccount, double toAccount, double amount) throws InvalidTransferAmountException, InsufficientBalanceException {
        if (amount <= 0) {
            throw new InvalidTransferAmountException();
        }
        if (fromAccount < amount) {
            throw new InsufficientBalanceException();
        }

        fromAccount -= amount;
        toAccount += amount;
        System.out.println("Перевод на сумму: " + amount + " успешно проведен");
        System.out.println("Баланс отправителя: " + fromAccount + " Баланс получателя: " + toAccount);
    }

    // 10. Сервис оценки товара
    public static void rateProduct(int rating) throws InvalidRatingException {
        if (rating < 1 || rating > 5) {
            throw new InvalidRatingException();
        }
        ratings.add(rating);
        System.out.println("Рейтинг " + rating + " успешно добавлен");
    }

    public static void rateProduct(String ratingStr) throws InvalidRatingException {
        try {
            int rating = Integer.parseInt(ratingStr);
            rateProduct(rating);
        } catch (NumberFormatException e) {
            System.err.println("Ошибка: '" + ratingStr + "' не является числом");
            throw new InvalidRatingException();
        }
    }
}