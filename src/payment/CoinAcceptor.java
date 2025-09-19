package payment;


import java.util.Scanner;

public class CoinAcceptor implements PaymentAcceptor{
    private int amount;

    public CoinAcceptor(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean canPay(int price) {
        return amount>=price;
    }

    public void addCoins() {
        System.out.print("Введите сумму для пополнения: ");
        try {
            Scanner scanner = new Scanner(System.in);
            int amountToAdd = Integer.parseInt(scanner.nextLine());

            if (amountToAdd > 0) {
                this.amount += amountToAdd;
                System.out.println("Баланс успешно пополнен на: " + amountToAdd);
            } else {
                System.out.println("Сумма пополнения должна быть положительной.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Введено не число. Пополнение отменено.");
        }
    }

    @Override
    public boolean pay(int price) {
        if (canPay(price)) {
            this.amount -= price;
            System.out.println("Платеж принят. Списано: " + price);
            return true;
        }
        System.out.println("Недостаточно средств.");
        return false;
    }

    @Override
    public void displayStatus() {
        System.out.println("Монет на сумму: " + amount);
    }
}

