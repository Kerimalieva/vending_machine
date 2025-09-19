package payment;

public interface PaymentAcceptor {
    boolean canPay(int price);
    boolean pay(int price);
    void displayStatus();

}
