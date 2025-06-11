import classwork.CoffeeMaker;
import classwork.NoWaterException;

public class Main {
    public static void main(String[] args) {
        CoffeeMaker.makeCoffee(false);
        CoffeeMaker.makeCoffee(true);

        try {
            CoffeeMaker.makeCoffeeV2(true);
        } catch (NoWaterException e) {
            throw new RuntimeException(e);
        }
    }
}