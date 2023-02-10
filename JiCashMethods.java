public class JiCashMethods {

    double DepositMoney(double currentBalance, double addedMoney) {
        currentBalance = currentBalance + addedMoney;
        return currentBalance;
    }

    double SendMoney(double currentBalance, double addedMoney, String moneyReceiver) {
        currentBalance = currentBalance - addedMoney;
        if (currentBalance < addedMoney) {
            System.out.println("ERROR");
        } else {
            System.out.println("Total: " + addedMoney);
            System.out.println("Money sent to: " + moneyReceiver);
        }
        return currentBalance;
    }

    double BillsPayment(double currentBalance, double paidMoney, String billerName) {
        currentBalance = currentBalance - paidMoney;
        if (currentBalance < paidMoney) {
            System.out.println("ERROR");
        } else {
            System.out.println("Total: " + paidMoney);
            System.out.println("Paid to: " + billerName);
        }
        return currentBalance;
    }

    double BankTransfer(double currentBalance, double paidMoney, String bankName) {
        currentBalance = currentBalance - paidMoney;
        if (currentBalance < paidMoney) {
            System.out.println("ERROR");
        } else {
            System.out.println("Total: " + paidMoney);
            System.out.println("Bank Name: " + bankName);
        }
        return currentBalance;
    }

}
