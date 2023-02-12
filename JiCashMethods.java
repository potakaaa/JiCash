public class JiCashMethods {

    double DepositMoney(double currentBalance, double addedMoney) {
        currentBalance = currentBalance + addedMoney;
        return currentBalance;
    }

    double SendMoney(double currentBalance, double addedMoney, String moneyReceiver) {
        currentBalance = currentBalance - addedMoney;
        return currentBalance;
    }

    double BillsPayment(double currentBalance, double paidMoney, String billerName) {
        currentBalance = currentBalance - paidMoney;
        return currentBalance;
    }

    double BankTransfer(double currentBalance, double paidMoney, String bankName) {
        currentBalance = currentBalance - paidMoney;
        return currentBalance;
    }

    void LoadingAnim(String startingWord) {
        System.out.print(startingWord);
        for (int i = 0; i < 3; i++) {
            try {
                Thread.sleep(500);
                System.out.print(".");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    // ERROR CODES
    void ErrorCode01(String errorName) {
        System.out.println();
        System.out.println("ERROR 01: Code is Invalid!");
        System.out.println("Error found on " + errorName);
        System.out.println();
    }
    void ErrorCode02(String errorName) {
        System.out.println();
        System.out.println("ERROR 02: Failed to create connection!");
        System.out.println("Error found on " + errorName);
        System.out.println();
    }
    void ErrorCode03(String errorName) {
        System.out.println();
        System.out.println("ERROR 03: Input should only contain alphabetical characters!");
        System.out.println("Error found on " + errorName);
        System.out.println();
    }
    void ErrorCode04(String errorName) {
        System.out.println();
        System.out.println("ERROR 04: Input should only contain numerical characters!");
        System.out.println("Error found on " + errorName);
        System.out.println();
    }
    void ErrorCode05(String errorName) {
        System.out.println();
        System.out.println("ERROR 05: Input should only contain 11 numerical characters");
        System.out.println("Error found on " + errorName);
        System.out.println();
    }
    void ErrorCode06(String errorName) {
        System.out.println();
        System.out.println("ERROR 06: Input should only contain 6 numerical characters");
        System.out.println("Error found on " + errorName);
        System.out.println();
    }


}
