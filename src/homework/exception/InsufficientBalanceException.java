package homework.exception;

public class InsufficientBalanceException extends Exception{
    public InsufficientBalanceException() {
        super("Сумма на балансе недостаточная для перевода");
    }
}
