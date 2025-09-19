import enums.ActionLetter;
import model.*;
import payment.CardAcceptor;
import payment.CoinAcceptor;
import payment.PaymentAcceptor;
import util.UniversalArray;
import util.UniversalArrayImpl;

import java.util.Scanner;

public class AppRunner {

    private final UniversalArray<Product> products = new UniversalArrayImpl<>();

    private final PaymentAcceptor paymentAcceptor;

    private static boolean isExit = false;

    private AppRunner() {
        products.addAll(new Product[]{
                new Water(ActionLetter.B, 20),
                new CocaCola(ActionLetter.C, 50),
                new Soda(ActionLetter.D, 30),
                new Snickers(ActionLetter.E, 80),
                new Mars(ActionLetter.F, 80),
                new Pistachios(ActionLetter.G, 130)
        });
        this.paymentAcceptor = selectPaymentMethod();
    }

    private PaymentAcceptor selectPaymentMethod() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("-------------------------");
            System.out.println("Выберите способ оплаты:");
            System.out.println("  1 -> Оплата Монетами");
            System.out.println("  2 -> Оплата Картой");
            System.out.print("Ваш выбор: ");

            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                return new CoinAcceptor(100);
            } else if (choice.equals("2")) {
                return new CardAcceptor();
            } else {
                System.out.println("\nНеверный ввод. Пожалуйста, введите цифру 1 или 2.\n");
            }
        }
    }

    public static void run() {
        AppRunner app = new AppRunner();
        while (!isExit) {
            app.startSimulation();
        }
    }

    private void startSimulation() {
        print("В автомате доступны:");
        showProducts(products);

        paymentAcceptor.displayStatus();

        UniversalArray<Product> allowProducts = new UniversalArrayImpl<>();
        allowProducts.addAll(getAllowedProducts().toArray());
        chooseAction(allowProducts);

    }

    private UniversalArray<Product> getAllowedProducts() {
        UniversalArray<Product> allowProducts = new UniversalArrayImpl<>();
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            if (paymentAcceptor.canPay(p.getPrice())) {
                allowProducts.add(products.get(i));
            }
        }
        return allowProducts;
    }

    private void chooseAction(UniversalArray<Product> products) {
        showActions(products);
        print(" h - Выйти");
        String action = fromConsole();
        if(action.trim().isEmpty()){
            System.out.println("Вы ничего не ввели, попробуйте снова!!!");
            return;
        }

        if ("h".equalsIgnoreCase(action)) {
            isExit = true;
            return;
        }

        try {
            ActionLetter selectedLetter = ActionLetter.valueOf(action.toUpperCase());
            Product chosenProduct = null;
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getActionLetter().equals(selectedLetter)) {
                    chosenProduct = products.get(i);
                    break;
                }
            }

            if (chosenProduct != null) {
                if (paymentAcceptor.pay(chosenProduct.getPrice())) {
                    print("Вы купили " + chosenProduct.getName());
                } else {
                    print("Оплата не удалась. Попробуйте еще раз.");
                }
            } else {
                print("Товар с такой буквой не найден среди доступных.");
            }

        } catch (IllegalArgumentException e) {
            print("Недопустимая буква. Попробуйте еще раз.");
        }
    }

    private void showActions(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(String.format(" %s - %s", products.get(i).getActionLetter().getValue(), products.get(i).getName()));
        }
    }

    private String fromConsole() {
        return new Scanner(System.in).nextLine();
    }

    private void showProducts(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(products.get(i).toString());
        }
    }

    private void print(String msg) {
        System.out.println(msg);
    }
}
