package homework;

import homework.exception.*;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import static homework.Task.CORRECT_PASSWORD;
import static homework.Task.CORRECT_USERNAME;
import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    @Test
    public void testSafeDivide() {
        assertEquals(5, Task.safeDivide(10, 2));
        assertEquals(0, Task.safeDivide(0, 5));
        assertEquals(-3, Task.safeDivide(-9, 3));
    }

    @Test
    public void testSafeDivideByZeroThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> Task.safeDivide(10, 0));

        assertEquals("Деление на ноль запрещено", exception.getMessage());
    }

    @Test
    void testValidateStringNotThrowException() {
        assertDoesNotThrow(() -> Task.validateString("Valid string"));
        assertDoesNotThrow(() -> Task.validateString("  With spaces  "));
        assertDoesNotThrow(() -> Task.validateString("a"));
    }

    @Test
    void testValidateStringNullThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> Task.validateString(null));

        assertEquals("Строка пустая или состоит только из пробелов", exception.getMessage());
    }

    @Test
    void testValidateStringEmptyThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> Task.validateString(""));

        assertEquals("Строка пустая или состоит только из пробелов", exception.getMessage());
    }

    @Test
    void testValidateStringWithSpaceThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> Task.validateString("   "));

        assertEquals("Строка пустая или состоит только из пробелов", exception.getMessage());
    }

    @Test
    void testParseNumbers() {
        List<String> input = Arrays.asList("1", "2", "3");
        List<Integer> expected = Arrays.asList(1, 2, 3);
        List<Integer> result = Task.parseNumbers(input);

        assertEquals(expected, result);
    }

    @Test
    void testParseNumbersWithMixedValues() {
        List<String> input = Arrays.asList("10", "abc", "20", "30", "xyz");
        List<Integer> expected = Arrays.asList(10, 20, 30);
        List<Integer> result = Task.parseNumbers(input);

        assertEquals(expected, result);
    }

    @Test
    void testParseNumbersWithMixedValuesPrintErrorForInvalidValues() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        List<String> input = Arrays.asList("10", "abc", "20", "30", "xyz");
        List<Integer> expected = Arrays.asList(10, 20, 30);
        List<Integer> result = Task.parseNumbers(input);

        assertTrue(outContent.toString().contains("Ошибка: 'abc' не является числом"));
        assertTrue(outContent.toString().contains("Ошибка: 'xyz' не является числом"));
        assertEquals(expected, result);

        System.setOut(System.out);
    }

    @Test
    void testSetAge() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Task.setAge(25);

        assertEquals("Возраст установлен: 25" + System.lineSeparator(), outContent.toString());

        System.setOut(System.out);
    }

    @Test
    public void testSetAgeNegativeAgeThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> Task.setAge(-10));

        assertEquals("Возраст не может быть отрицательным: -10", exception.getMessage());
    }

    @Test
    void testDepositPositiveAmount() throws NegativeDepositException {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Task.deposit(100.50);

        assertEquals("Успешно внесено: 100.5" + System.lineSeparator(), outContent.toString());

        System.setOut(System.out);
    }

    @Test
    void testDepositNegativeAmountNegative() {
        NegativeDepositException exception = assertThrows(NegativeDepositException.class, () -> Task.deposit(-100.50));

        assertEquals("Нельзя внести отрицательную сумму: -100.5", exception.getMessage());
    }

    @Test
    void testGetItemExistingCode() {
        String result = Task.getItem("A123");
        assertEquals("Ноутбук", result);
    }

    @Test
    void testGetItemNonExistingCodeThrowException() {
        ItemNotFoundException exception = assertThrows(ItemNotFoundException.class, () -> Task.getItem("XYZ123"));

        assertEquals("Товар с кодом 'XYZ123' не найден", exception.getMessage());
    }

    @Test
    void testReadFileValidFile() {
        List<String> result = Task.readFile("src/homework/test.txt");

        assertEquals(2, result.size());
        assertEquals("Первая строка", result.get(0));
        assertEquals("Вторая строка", result.get(1));
    }

    @Test
    void testReadFileNonExistentFile() {
        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errContent));

        List<String> result = Task.readFile("nonexistent.txt");

        assertTrue(result.isEmpty());
        assertTrue(errContent.toString().contains("Ошибка при чтении файла"));

        System.setErr(System.err);
    }

    @Test
    void testLoginCorrectCredentials() throws LoginFailedException {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Task.login(CORRECT_USERNAME, CORRECT_PASSWORD);

        assertEquals("Аутентификация успешно пройдена!" + System.lineSeparator(), outContent.toString());

        System.setOut(System.out);
    }

    @Test
    void testLoginWrongUsernameThrowException() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        LoginFailedException exception = assertThrows(LoginFailedException.class, () -> Task.login("wrong", CORRECT_PASSWORD));

        assertEquals("Неверный логин или пароль", exception.getMessage());
        assertEquals("", outContent.toString());

        System.setOut(System.out);
    }

    @Test
    void testLoginWrongPasswordThrowException() {
        LoginFailedException exception = assertThrows(LoginFailedException.class, () -> Task.login(CORRECT_USERNAME, "wrong"));

        assertEquals("Неверный логин или пароль", exception.getMessage());
    }

    @Test
    void testLoginNullUsernameThrowException() {
        LoginFailedException exception = assertThrows(LoginFailedException.class, () -> Task.login(null, CORRECT_PASSWORD));

        assertEquals("Логин и пароль не могут быть пустым", exception.getMessage());
    }

    @Test
    void testLoginNullPasswordThrowException() {
        LoginFailedException exception = assertThrows(LoginFailedException.class, () -> Task.login(CORRECT_USERNAME, null));
        assertEquals("Логин и пароль не могут быть пустым", exception.getMessage());
    }

    @Test
    void testTransferValidAmount() throws InvalidTransferAmountException, InsufficientBalanceException {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Task.transfer(1000.0, 500.0, 300.0);

        String output = outContent.toString();
        assertTrue(output.contains("Перевод на сумму: 300.0 успешно проведен"));
        assertTrue(output.contains("Баланс отправителя: 700.0"));
        assertTrue(output.contains("Баланс получателя: 800.0"));

        System.setOut(System.out);
    }

    @Test
    void testTransferNegativeAmountThrowsException() {
        InvalidTransferAmountException exception = assertThrows(InvalidTransferAmountException.class, () -> Task.transfer(1000.0, 500.0, -100.0));

        assertEquals("Сумма перевода должна быть положительной", exception.getMessage());
    }

    @Test
    void testTransferZeroAmountThrowsException() {
        InvalidTransferAmountException exception = assertThrows(InvalidTransferAmountException.class, () -> Task.transfer(1000.0, 500.0, 0.0));

        assertEquals("Сумма перевода должна быть положительной", exception.getMessage());
    }

    @Test
    void testTransferInsufficientBalanceThrowsException() {
        InsufficientBalanceException exception = assertThrows(InsufficientBalanceException.class, () -> Task.transfer(200.0, 500.0, 300.0));

        assertEquals("Сумма на балансе недостаточная для перевода", exception.getMessage());
    }

    @Test
    void testRateProductValidRating() throws InvalidRatingException {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        List<Integer> taskRatings = Task.getRatings();
        taskRatings.clear();

        Task.rateProduct(1);
        assertEquals(1, taskRatings.size());
        assertEquals(1, taskRatings.get(0));
        assertTrue(outContent.toString().contains("Рейтинг 1 успешно добавлен"));

        Task.rateProduct(3);
        assertEquals(2, taskRatings.size());
        assertTrue(taskRatings.contains(3));
        assertTrue(outContent.toString().contains("Рейтинг 3 успешно добавлен"));

        System.setOut(System.out);
    }

    @Test
    void testRateProductRatingBelow1ThrowException() {
        InvalidRatingException exception = assertThrows(InvalidRatingException.class, () -> Task.rateProduct(0));

        assertEquals("Рейтинг должен быть от 1 до 5", exception.getMessage());
    }

    @Test
    void testRateProductRatingAbove5ThrowException() {
        InvalidRatingException exception = assertThrows(InvalidRatingException.class, () -> Task.rateProduct(6));

        assertEquals("Рейтинг должен быть от 1 до 5", exception.getMessage());
    }

    @Test
    void testRateProductValidString() throws InvalidRatingException {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));

        Task.rateProduct("4");
        assertTrue(outContent.toString().contains("Рейтинг 4 успешно добавлен"));
        assertTrue(errContent.toString().isEmpty());

        System.setOut(System.out);
        System.setErr(System.err);
    }

    @Test
    void testRateProductInvalidStringThrowException() {
        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errContent));

        assertThrows(InvalidRatingException.class, () -> Task.rateProduct("0"));
        assertTrue(errContent.toString().isEmpty());

        System.setErr(System.err);
    }

    @Test
    void testRateProductNonNumberThrowException() {
        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errContent));
        InvalidRatingException exception = assertThrows(InvalidRatingException.class, () -> Task.rateProduct("пять"));

        assertEquals("Рейтинг должен быть числом от 1 до 5", exception.getMessage());
        assertTrue(errContent.toString().contains("Ошибка: 'пять' не является числом"));

        System.setErr(System.err);
    }
}