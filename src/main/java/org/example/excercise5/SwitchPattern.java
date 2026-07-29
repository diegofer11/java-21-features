package org.example.excercise5;

import static org.example.utils.OrderUtils.LOG;

import org.example.model.OrderData;

public class SwitchPattern {
	public static void main(String[] args) {
		LOG.info(processPayment(new OrderData.Crypto("0x123...abc", "BTC")));
		LOG.info(processPayment(new OrderData.BankTransfer("ES9121000418450200051332")));
		LOG.info(processPayment(null));
	}

	private static String processPayment(OrderData.PaymentMethod payment) {
		return switch (payment) {
			case OrderData.CreditCard(String cardNumber, String holder) when cardNumber.startsWith("4") ->
					"Tarjeta Visa de " + holder;
			case OrderData.CreditCard(String cardNumber, String holder) when !cardNumber.startsWith("4") ->
					"Tarjeta bancaria " + cardNumber + " de " + holder;
			case OrderData.Crypto(String walletAddress, String coin) when coin.equals("BTC") || coin.equals("ETH") ->
					"Pago seguro en " + coin + " a la billetera " + walletAddress;
			case OrderData.Crypto(String walletAddress, String coin) ->
					"Criptomoneda no prioritaria " + coin + " en la billetera " + walletAddress;
			case OrderData.BankTransfer bt -> "Transferencia bancaria al IBAN: " + bt.iban();
			case null -> "Método de pago no provisto";
			default -> throw new IllegalStateException("Unexpected value: " + payment);
		};
	}
}
