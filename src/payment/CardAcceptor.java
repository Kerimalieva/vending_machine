package payment;

import java.util.Scanner;

public class CardAcceptor implements PaymentAcceptor{
    private int money = 1000;

    @Override
    public boolean canPay(int price) {
        return price <= money;
    }

    @Override
    public boolean pay(int price) {
        if (!canPay(price)) {
            return false;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("--- Оплата картой ---");
        System.out.print("Введите номер карты (например, 1234-5678): ");
        String cardNumber = scanner.nextLine();
        System.out.print("Введите PIN-код (например, 123): ");
        String pin = scanner.nextLine();

        if (cardNumber.trim().isEmpty() || pin.trim().isEmpty()) {
            System.out.println("Оплата отменена. Данные не были введены.");
            return false;
        }

        System.out.println("Платеж на сумму " + price + " по карте " + cardNumber + " одобрен.");
        System.out.println("---------------------");
        return true;
    }

    @Override
    public void displayStatus() {
        System.out.println("Автомат принимает банковские карты.");
    }
}
