package homework.exception;

public class InvalidTransferAmountException extends Exception{
    public InvalidTransferAmountException() {
        super("Сумма перевода должна быть положительной");
    }
}
