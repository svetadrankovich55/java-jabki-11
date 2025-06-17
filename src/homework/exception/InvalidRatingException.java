package homework.exception;

public class InvalidRatingException extends Exception{
    public InvalidRatingException() {
        super("Рейтинг должен быть от 1 до 5");
    }
}