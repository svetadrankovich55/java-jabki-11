import classwork.CoffeeMaker;
import classwork.NoWaterException;
import homework.Task;
import homework.exception.InsufficientBalanceException;
import homework.exception.InvalidTransferAmountException;
import homework.exception.ItemNotFoundException;
import homework.exception.NegativeDepositException;

public class Main {
    public static void main(String[] args) {
        CoffeeMaker.makeCoffee(false);
        CoffeeMaker.makeCoffee(true);

        try {
            CoffeeMaker.makeCoffeeV2(true);
        } catch (NoWaterException e) {
            throw new RuntimeException(e);
        }

        // 5. Собственное исключение: депозит
        System.out.println("\n 5. Собственное исключение: депозит \n");
        try {
            Task.deposit(1000);
            Task.deposit(-500);
        } catch (NegativeDepositException e) {
            System.err.println("Ошибка при внесении средств: " + e.getMessage());
        }

        // 6. Поиск товара по коду
        System.out.println("\n 6. Поиск товара по коду \n");
        try {
            String item1 = Task.getItem("A123");
            System.out.println("Найден товар: " + item1);
            String item2 = Task.getItem("XYZ999");
            System.out.println("Найден товар: " + item2);
        } catch (ItemNotFoundException e) {
            System.err.println("Ошибка: " + e.getMessage());
        }

        // 9. Банковский перевод с валидацией
        System.out.println("\n 9. Банковский перевод с валидацией \n");
        try {
            Task.transfer(500.0, 1000.10, 50.0);
            Task.transfer(500.0, 1000.10, -50.0);
            Task.transfer(500.0, 1000.10, 550.0);
        } catch (InvalidTransferAmountException | InsufficientBalanceException e) {
            System.err.println("Ошибка перевода:" + e.getMessage());
        }
    }
}