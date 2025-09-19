package payment;


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

