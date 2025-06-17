package homework.exception;

public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException(String code) {
        super("Товар с кодом '" + code + "' не найден");
    }
}