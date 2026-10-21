import java.util.List;
import java.util.function.UnaryOperator;

public class PizzaDemo {
    public static void main(String[] args) {
        Pizza pizza = new PlainPizza();
        pizza = new Cheese(pizza);
        pizza = new Olives(pizza);
        pizza = new Pineapple(pizza);

        System.out.println(pizza.getDescription());
        System.out.printf("Total: $%.2f%n", pizza.getCost());

        pizza = new Cheese(pizza);
        System.out.println("\n-- Extra cheese! --");
        System.out.println(pizza.getDescription());
        System.out.printf("Total: $%.2f%n", pizza.getCost());

        List<UnaryOperator<Pizza>> order =
                List.of(Cheese::new, Olives::new, Pineapple::new, Cheese::new);

        Pizza custom = new PlainPizza();
        for (UnaryOperator<Pizza> topping : order) {
            custom = topping.apply(custom);
        }
        System.out.println("\n-- Built from a list --");
        System.out.println(custom.getDescription());
        System.out.printf("Total: $%.2f%n", custom.getCost());
    }
}
abstract class ToppingDecorator implements Pizza {
    private final Pizza pizza;
    private final String name;
    private final double price;

    protected ToppingDecorator(Pizza pizza, String name, double price) {
        this.pizza = pizza;
        this.name = name;
        this.price = price;
    }

    @Override
    public String getDescription() {
        return pizza.getDescription() + ", " + name;
    }

    @Override
    public double getCost() {
        return pizza.getCost() + price;
    }
}
class Cheese extends ToppingDecorator {
    public Cheese(Pizza pizza) {
        super(pizza, "Cheese", 2.0);
    }
}

class Olives extends ToppingDecorator {
    public Olives(Pizza pizza) {
        super(pizza, "Olives", 1.0);
    }
}

class Pineapple extends ToppingDecorator {
    public Pineapple(Pizza pizza) {
        super(pizza, "Pineapple", 1.5);
    }
}

interface Pizza {
    String getDescription();
    double getCost();
}

class PlainPizza implements Pizza {
    @Override
    public String getDescription() {
        return "Plain Dough & Sauce";
    }

    @Override
    public double getCost() {
        return 5.0;
    }
}


