package classwork;

public class CoffeeMaker {
    private CoffeeMaker() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static void makeCoffee(boolean hasWater) {
        try {
            if (!hasWater) {
                throw new NoWaterException("Нет воды!");
            }
            // ...
            System.out.println("Кофе готов!");
        } catch (NoWaterException e) {
            System.out.println("Ошибка: " + e.getMessage());
        } finally {
            System.out.println("Очистка кофемашины");
        }
    }

    public static void makeCoffeeV2(boolean hasWater) throws NoWaterException {
        if (!hasWater) {
            throw new NoWaterException("Нет воды!");
        }
        // ...
        System.out.println("Кофе готов!");
    }
}
