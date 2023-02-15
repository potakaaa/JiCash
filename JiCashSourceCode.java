import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class JiCash {
    // Declare final variables for SQL connection
    static final String databaseURL = "jdbc:mysql://localhost:3306/jicash-database";
    static final String username = "root";
    static final String password = "toor";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner sc2 = new Scanner(System.in);
        Scanner sc3 = new Scanner(System.in);
        Scanner sc4 = new Scanner(System.in);
        Scanner sc5 = new Scanner(System.in);
        Scanner sc6 = new Scanner(System.in);
        Scanner scHomePage = new Scanner(System.in);
        Scanner scPIN = new Scanner(System.in);

        JiCashMethods methods = new JiCashMethods();
        System.out.println("                   JiCash");
        System.out.println("'Make your transactions cashless and convenient'");
        System.out.println();
        methods.LoadingAnim("Opening App");

        System.out.println();
        System.out.println("        ----------------------");
        System.out.println("        | CODE |    OPTION   |");
        System.out.println("        ----------------------");
        System.out.println("        |  SU  |    SIGNUP   |");
        System.out.println("        |  LN  |    LOGIN    |");
        System.out.println("        ----------------------");
        System.out.println("Get 50 PHP when signing up for a new account!");
        System.out.println();

        // For signup variables
        String newFirstName = ""; String newLastName = ""; String AccountNumber = ""; String newAccountPIN = "";
        double newAccountMoney = 50; int accountId = 0;
        // For Homepage variables
        String AccFirstName = ""; String AccLastName = ""; String AccPIN = ""; double AccCash = 0;

        boolean boolSuLn = false;
        while (!boolSuLn) {
            System.out.print("Enter Code: ");
            String SuLn = sc.nextLine();
            if (SuLn.equalsIgnoreCase("su")) {
                try {
                    Connection con = DriverManager.getConnection(databaseURL, username, password);
                    Statement st = con.createStatement();
                    ResultSet rs = st.executeQuery("select * from jicashaccount");
                    // Enter new Account details
                    System.out.println();
                    methods.LoadingAnim("Preparing");
                    System.out.println();

                    boolean boolFirstName = false;
                    while (!boolFirstName) {
                        System.out.print("First Name: ");
                        newFirstName = sc.nextLine().toUpperCase();
                        if (newFirstName.matches(".*\\d.*")) {
                            methods.ErrorCode03("First Name");
                        } else {
                            boolFirstName = true;
                        }
                    }

                    boolean boolLastName = false;
                    while (!boolLastName) {
                        System.out.print("Last Name: ");
                        newLastName = sc.nextLine().toUpperCase();
                        if (newLastName.matches(".*\\d.*")) {
                            methods.ErrorCode03("Last Name");
                        } else {
                            boolLastName = true;
                        }
                    }

                    String[] accNumberList = new String[100]; int id = 0;
                    while(rs.next()) {
                        accNumberList[id] = rs.getString("AccountNumber");
                        id++;
                    }

                    boolean boolAccountNumber = false;
                    while (!boolAccountNumber) {
                        System.out.print("Mobile Number: ");
                        AccountNumber = sc.nextLine();
                        if (AccountNumber.matches(".*[a-zA-Z].*")) {
                            methods.ErrorCode04("Mobile Number");
                        } else if (AccountNumber.length() == 11 && AccountNumber.matches(".*[0-9].*")) {
                            if (AccountNumber.startsWith("09")) {
                                boolean boolNewNumberValidator = false;
                                for (int i = 0; i < accNumberList.length; i++) {
                                    if (AccountNumber.equals(accNumberList[i])) {
                                        boolNewNumberValidator = true;
                                    }
                                } if (boolNewNumberValidator) {
                                    System.out.println("Number already exists!");
                                    System.out.println();
                                } else {
                                    boolAccountNumber = true;
                                }
                            } else {
                                System.out.println("Mobile Number should start with '09'!");
                                System.out.println();
                            }
                        } else {
                            methods.ErrorCode05("Mobile Number");
                        }
                    }

                    boolean boolAccountPIN = false;
                    while (!boolAccountPIN) {
                        System.out.print("Account PIN (6 Digits): ");
                        newAccountPIN = sc.nextLine();
                        if (newAccountPIN.matches(".*[0-9].*") && newAccountPIN.length() == 6) {
                            boolAccountPIN = true;
                        } else {
                            methods.ErrorCode04("Account PIN");
                        }
                    }

                    // Store to database new account
                    String storeAccount = "INSERT INTO jicashaccount VALUES (\"" + accountId + "\",\"" + newFirstName + "\"," +
                            "\"" + newLastName + "\",\"" + AccountNumber + "\",\"" + newAccountPIN + "\",\"" + newAccountMoney + "\")";
                    int val = st.executeUpdate(storeAccount);
                    boolSuLn = true;
                    methods.LoadingAnim("Loading");
                    System.out.println();
                    System.out.println("Account Created!");
                    System.out.println();
                    System.out.println("Hello " + newFirstName + "!");

                    for (int attempts = 0; attempts <= 3; attempts++) {
                        System.out.print("Enter PIN: ");
                        String enterPIN = sc2.nextLine();
                        if (enterPIN.equals(newAccountPIN)) {
                            break;
                        } else if (attempts == 3) {
                            System.out.println("Contact Support");
                            System.exit(0);
                        } else {
                            int remAttempt = 3 - attempts;
                            System.out.println();
                            System.out.println("WRONG PIN! Try Again");
                            System.out.println("Attempts remaining: " + remAttempt);
                            System.out.println();
                        }
                    }

                } catch (SQLException e) {
                    methods.ErrorCode02("SQL Database");
                    e.printStackTrace();
                }
            } else if (SuLn.equalsIgnoreCase("ln")) {
                try {
                    Connection con = DriverManager.getConnection(databaseURL, username, password);
                    Statement st = con.createStatement();
                    // Declaration of Arrays
                    String[] firstNameList = new String[100]; String[] lastNameList = new String[100]; String[] accNumberList = new String[100];
                    String[] accountPINList = new String[100]; double[] accountCash = new double[100]; int id = 0;
                    ResultSet rs = st.executeQuery("select * from jicashaccount");
                    // Store account informations to arrays
                    while (rs.next()) {
                        firstNameList[id] = rs.getString("FirstName");
                        lastNameList[id] = rs.getString("LastName");
                        accNumberList[id] = rs.getString("AccountNumber");
                        accountPINList[id] = rs.getString("AccountPIN");
                        accountCash[id] = rs.getDouble("AccountCash");
                        id++;
                    }
                    boolean boolLogAccNum = false;
                    while (!boolLogAccNum) {
                        System.out.print("Enter Mobile Number: ");
                        AccountNumber = sc2.nextLine();
                        if (AccountNumber.matches(".*[a-zA-Z].*")) {
                            methods.ErrorCode04("Mobile Number");
                        } else if (AccountNumber.length() == 11 && AccountNumber.matches(".*[0-9].*")) {
                            boolean boolAccNumberValidator = false;
                            for (int i = 0; i < accNumberList.length; i++) {
                                if (AccountNumber.equals(accNumberList[i])) {
                                    boolLogAccNum = true;
                                    boolAccNumberValidator = true;
                                }
                            } if (boolAccNumberValidator) {
                                break;
                            } else {
                                System.out.println("No existing credentials");
                            }
                        } else {
                            methods.ErrorCode05("Mobile Number");
                        }
                    }

                    boolean boolLogAccPIN = false;
                    int attempts = 0;
                    while (!boolLogAccPIN) {
                        System.out.print("Enter PIN: ");
                        int currAttempt = 3 - attempts;
                        String loginAccPIN = sc2.nextLine();
                        if (loginAccPIN.length() == 6) {
                            boolean boolAccPINValidator = false;
                            for (int i = 0; i < accountPINList.length; i++) {
                                if (AccountNumber.equals(accNumberList[i]) && loginAccPIN.equals(accountPINList[i])) {
                                    boolLogAccPIN = true;
                                    boolAccPINValidator = true;
                                }
                            } if (boolAccPINValidator) {
                                break;
                            } else if (attempts == 3) {
                                System.out.println("Contact Support");
                                System.exit(0);
                            } else {
                                System.out.println();
                                System.out.println("Invalid PIN! Try Again");
                                attempts++;
                                System.out.println("Attempts Remaining: " + currAttempt);
                                System.out.println();
                            }
                        } else if (loginAccPIN.matches(".*[a-zA-Z]")) {
                            methods.ErrorCode04("Account PIN");
                        } else {
                            methods.ErrorCode06("Account PIN");
                        }
                    }
                    boolSuLn = true;
                    methods.LoadingAnim("Logging In");

                } catch (SQLException e) {
                    methods.ErrorCode02("SQL Database");
                    e.printStackTrace();
                }
            } else {
                methods.ErrorCode01("Login or Signup");
            }
        }
        try {
            Connection con = DriverManager.getConnection(databaseURL, username, password);
            Statement st = con.createStatement();
            String[] firstNameList = new String[100]; String[] lastNameList = new String[100]; String[] accNumberList = new String[100];
            String[] accountPINList = new String[100]; double[] accountCash = new double[100]; int id = 0;
            ResultSet rs = st.executeQuery("select * from jicashaccount");
            while (rs.next()) {
                firstNameList[id] = rs.getString("FirstName");
                lastNameList[id] = rs.getString("LastName");
                accNumberList[id] = rs.getString("AccountNumber");
                accountPINList[id] = rs.getString("AccountPIN");
                accountCash[id] = rs.getDouble("AccountCash");
                id++;
            }

            for (int i = 0; i < accNumberList.length; i++) {
                if (AccountNumber.equals(accNumberList[i])) {
                    AccFirstName = firstNameList[i];
                    AccLastName = lastNameList[i];
                    AccountNumber = accNumberList[i];
                    AccPIN = accountPINList[i];
                    AccCash = accountCash[i];
                }
            }

            boolean boolHomePage = false;
            while (!boolHomePage) {
                System.out.println();
                System.out.println();
                System.out.println("Hello " + AccFirstName + "!");
                System.out.println("Available JiCash: ₱" + AccCash);
                System.out.println("-----------------------------");
                System.out.println("| CODE |       OPTION        |");
                System.out.println("------------------------------");
                System.out.println("|  SM  |     SEND MONEY      |");
                System.out.println("|  DM  |    DEPOSIT MONEY    |");
                System.out.println("|  BP  |    BILLS PAYMENT    |");
                System.out.println("|  BT  |    BANK TRANSFER    |");
                System.out.println("|  EX  |        EXIT         |");
                System.out.println("------------------------------");
                System.out.println();
                System.out.print("Enter Code: ");
                String homePageCode = scHomePage.nextLine();
                if (homePageCode.equalsIgnoreCase("sm")) {
                    // Declare variables in advance to call them later
                    boolean boolSendMoneyName = false; double sendMoney = 0; boolean boolRecMobNumValidator = false;
                    String recFirstName = ""; String recLastName = ""; String recMobNum = ""; double recCurrentBalance = 0;

                    while (!boolSendMoneyName) {
                        System.out.println();
                        System.out.print("Enter Receiver Mobile Number: ");
                        recMobNum = sc3.nextLine();
                        if (recMobNum.matches(".*[a-zA-Z].*")) {
                            methods.ErrorCode04("Mobile Number");
                        } else if (recMobNum.equals(AccountNumber)) {
                            System.out.println("Invalid Number!");
                        } else if (recMobNum.length() == 11 && recMobNum.matches(".*[0-9].*")) {
                            for (int i = 0; i < accNumberList.length; i++) {
                                if (recMobNum.equals(accNumberList[i])) {
                                    recFirstName = firstNameList[i];
                                    recLastName = lastNameList[i];
                                    recCurrentBalance = accountCash[i];
                                    boolRecMobNumValidator = true;
                                    boolSendMoneyName = true;
                                }
                            }
                            if (boolRecMobNumValidator) {
                                break;
                            } else {
                                System.out.println("No existing credentials!");
                                System.out.println();
                            }
                        } else {
                            methods.ErrorCode05("Receiver Mobile Number");
                        }
                    }
                    boolean boolSendMoney = false;
                    while (!boolSendMoney) {
                        System.out.println();
                        System.out.print("Enter Money To Be Sent: ");
                        try {
                            sendMoney = sc3.nextDouble();
                            if (sendMoney > AccCash) {
                                System.out.println("Insufficient Funds!");
                            } else {
                                boolSendMoney = true;
                            }
                        } catch (InputMismatchException e) {
                            methods.ErrorCode04("Send Money");
                            sc3.nextLine();
                        }
                    }
                    System.out.println();
                    System.out.println("Receiver: " + recLastName + ", " + recFirstName);
                    System.out.println("Amount: " + sendMoney);
                    boolean boolPINConfirm = false;
                    while (!boolPINConfirm) {
                        System.out.print("Enter PIN to Confirm: ");
                        String confirmPIN = scPIN.nextLine();
                        if (confirmPIN.equals(AccPIN)) {
                            boolPINConfirm = true;
                        } else {
                            System.out.println("WRONG PIN! Try Again");
                            System.out.println();
                        }
                    }
                    AccCash = methods.SendMoney(AccCash, sendMoney, recFirstName);
                    double recNewBalance = recCurrentBalance + sendMoney;

                    String updateRecBalance = "UPDATE jicashaccount SET AccountCash = " + recNewBalance + "WHERE AccountNumber = " + recMobNum;
                    String updateMyBalance = "UPDATE jicashaccount SET AccountCash = " + AccCash + "WHERE AccountNumber = " + AccountNumber;
                    int val = st.executeUpdate(updateRecBalance);
                    int val2 = st.executeUpdate(updateMyBalance);

                    methods.LoadingAnim("Updating Balance");
                    System.out.println();
                    System.out.println("Successfuly sent!");
                } else if (homePageCode.equalsIgnoreCase("dm")) {
                    double depMoney = 0; boolean boolDepMoney = false;
                    while(!boolDepMoney) {
                        System.out.print("Enter Money to Deposit (Up to ₱5000): ");
                        try {
                            depMoney = sc4.nextDouble();
                            if (depMoney > 5000 || depMoney < 1) {
                                System.out.println("Invalid Amount! Try Again");
                                System.out.println();
                            } else {
                                boolDepMoney = true;
                            }
                        } catch (InputMismatchException e) {
                            methods.ErrorCode04("Deposit Money");
                            sc4.nextLine();
                        }
                    }
                    System.out.println();
                    System.out.println("Account Name: " + AccLastName + ", " + AccFirstName);
                    System.out.println("Money to be Deposited: " + depMoney);
                    boolean boolConfirmPIN = false;
                    while(!boolConfirmPIN) {
                        System.out.print("Enter PIN to Confirm: ");
                        String confirmPIN = scPIN.nextLine();
                        if (confirmPIN.equals(AccPIN)) {
                            boolConfirmPIN = true;
                        } else {
                            System.out.println("WRONG PIN! Try Again");
                            System.out.println();
                        }
                    }
                    AccCash = methods.DepositMoney(AccCash, depMoney);
                    String updateAcc = "UPDATE jicashaccount SET AccountCash = " + AccCash + "WHERE AccountNumber = " + AccountNumber;
                    int val = st.executeUpdate(updateAcc);
                    methods.LoadingAnim("Updating Balance");
                    System.out.println();
                    System.out.println("Successfuly Deposited!");
                } else if (homePageCode.equalsIgnoreCase("bp")) {
                    System.out.println();
                    System.out.println("          BILLERS");
                    System.out.println("--------------------------");
                    System.out.println("| CODE |       OPTION     |");
                    System.out.println("--------------------------");
                    System.out.println("|  CO  |      CEPALCO     |");
                    System.out.println("|  PT  |       PLDT       |");
                    System.out.println("|  MD  |     Medicard     |");
                    System.out.println("|  AA  |     AirAsia      |");
                    System.out.println("|  PC  |    PHINMA COC    |");
                    System.out.println("--------------------------");
                    System.out.println();

                    String biller = "";
                    double billAmount = 0;
                    boolean boolBillCode = false;
                    while(!boolBillCode) {
                        System.out.print("Enter Code: ");
                        String billPayCode = sc5.nextLine();

                        if (billPayCode.equalsIgnoreCase("co")) {
                            biller = "CEPALCO";
                            boolBillCode = true;
                        } else if (billPayCode.equalsIgnoreCase("pt")) {
                            biller = "PLDT";
                            boolBillCode = true;
                        } else if (billPayCode.equalsIgnoreCase("md")) {
                            biller = "Medicard";
                            boolBillCode = true;
                        } else if (billPayCode.equalsIgnoreCase("aa")) {
                            biller = "AirAsia";
                            boolBillCode = true;
                        } else if (billPayCode.equalsIgnoreCase("pc")) {
                            biller = "PHINMA COC";
                            boolBillCode = true;
                        } else {
                            methods.ErrorCode01("Biller Name");
                        }
                    }
                    boolean boolBillAmount = false;
                    System.out.println();
                    while (!boolBillAmount) {
                        System.out.print("Enter Amount to Pay (Must not be greater than 10,000): ");
                        try {
                            billAmount = sc5.nextDouble();
                            if (billAmount > AccCash) {
                                System.out.println("Insufficient Funds!");
                                System.out.println();
                            } else if (billAmount > 10000) {
                                System.out.println("Invalid Amount! Try Again");
                                System.out.println();
                            } else {
                                boolBillAmount = true;
                            }
                        } catch (InputMismatchException e) {
                            methods.ErrorCode04("Bill Amount");
                            sc5.nextLine();
                        }
                    }
                    System.out.println();
                    System.out.println("Biller Name: " + biller);
                    System.out.println("Bill Amount: " + billAmount);
                    System.out.println();

                    boolean boolConfirmPIN = false;
                    while(!boolConfirmPIN) {
                        System.out.print("Enter PIN to Confirm: ");
                        String confirmPIN = scPIN.nextLine();
                        if (confirmPIN.equals(AccPIN)) {
                            boolConfirmPIN = true;
                        } else {
                            System.out.println("WRONG PIN! Try Again");
                            System.out.println();
                        }
                    }

                    AccCash = methods.BillsPayment(AccCash, billAmount, biller);
                    String updateAcc = "UPDATE jicashaccount SET AccountCash = " + AccCash + "WHERE AccountNumber = " + AccountNumber;
                    int val2 = st.executeUpdate(updateAcc);
                    methods.LoadingAnim("Updating Balance");
                    System.out.println();
                    System.out.println("Successfuly Paid!");

                } else if (homePageCode.equalsIgnoreCase("bt")) {
                    System.out.println();
                    System.out.println("          BANKS");
                    System.out.println("------------------------");
                    System.out.println("| CODE |     OPTION    |");
                    System.out.println("------------------------");
                    System.out.println("|  BI  |      BPI      |");
                    System.out.println("|  BO  |      BDO      |");
                    System.out.println("|  MK  |   Metrobank   |");
                    System.out.println("|  LK  |   Landbank    |");
                    System.out.println("|  PB  |      PNB      |");
                    System.out.println("------------------------");
                    System.out.println();

                    String bankName = ""; String bankCode = ""; String swiftCode = ""; double bankAmount = 0;
                    boolean boolBankOption = false;
                    while (!boolBankOption) {
                        System.out.print("Enter Code: ");
                        String bankOption = sc6.nextLine();

                        if (bankOption.equalsIgnoreCase("bi")) {
                            bankName = "Bank of the Philippine Islands";
                            bankCode = "010040018";
                            swiftCode = "BOPIPHMMXXX";
                            boolBankOption = true;
                        } else if (bankOption.equalsIgnoreCase("bo")) {
                            bankName = "Banco de Oro";
                            bankCode = "010530667";
                            swiftCode = "BNORPHMMXXX";
                            boolBankOption = true;
                        } else if (bankOption.equalsIgnoreCase("mk")) {
                            bankName = "Metropolitan Bank & Trust Company";
                            bankCode = "010269996";
                            swiftCode = "MBTCPHMMXXX";
                            boolBankOption = true;
                        } else if (bankOption.equalsIgnoreCase("lk")) {
                            bankName = "Landbank";
                            bankCode = "010350025";
                            swiftCode = "TLBPPHMMXXX";
                            boolBankOption = true;
                        } else if (bankOption.equalsIgnoreCase("pb")) {
                            bankName = "The Philippine National Bank";
                            bankCode = "010080010";
                            swiftCode = "PNBMPHMMXXX";
                            boolBankOption = true;
                        } else {
                            methods.ErrorCode01("Bank Options");
                        }
                    }
                    boolean boolBankAmount = false;
                    System.out.println();
                    while (!boolBankAmount) {
                        System.out.println("Bank Transfer has ₱15 convenience fee!");
                        System.out.print("Enter Amount to Transfer (Up to ₱10,000 only): ");
                        try {
                            bankAmount = sc6.nextDouble();
                            if ((bankAmount + 15) > AccCash) {
                                System.out.println();
                                System.out.println("Insufficient Funds!");
                                System.out.println();
                            } else if (bankAmount > 10000) {
                                System.out.println();
                                System.out.println("Invalid Amount!");
                                System.out.println();
                            } else {
                                boolBankAmount = true;
                            }
                        } catch (InputMismatchException e) {
                            methods.ErrorCode04("Amount to Transfer");
                            sc6.nextLine();
                        }
                    }
                    double totalBankAmount = bankAmount + 15;
                    System.out.println();
                    System.out.println("Bank Name: " + bankName);
                    System.out.println("Bank Code: " + bankCode);
                    System.out.println("Swift Code: " + swiftCode);
                    System.out.println("Amount to Transfer: " + bankAmount);
                    System.out.println("Convenience Fee: ₱15");
                    System.out.println("Total: " + totalBankAmount);
                    System.out.println();

                    boolean boolConfirmPIN = false;
                    while(!boolConfirmPIN) {
                        System.out.print("Enter PIN to Confirm: ");
                        String confirmPIN = scPIN.nextLine();
                        if (confirmPIN.equals(AccPIN)) {
                            boolConfirmPIN = true;
                        } else {
                            System.out.println("WRONG PIN! Try Again");
                            System.out.println();
                        }
                    }
                    AccCash = methods.BankTransfer(AccCash, bankAmount, bankName);
                    String updateAcc = "UPDATE jicashaccount SET AccountCash = " + AccCash + "WHERE AccountNumber = " + AccountNumber;
                    int val3 = st.executeUpdate(updateAcc);
                    methods.LoadingAnim("Updating Balance");
                    System.out.println();
                    System.out.println("Successfuly Transferred!");
                } else if (homePageCode.equalsIgnoreCase("ex")) {
                    System.out.println();
                    System.out.println("Thank you for using JiCash!");
                    System.exit(0);
                } else {
                    methods.ErrorCode01("Home Page Code");
                }
            }
        } catch (SQLException e) {
            methods.ErrorCode02("SQL Database");
        }

    }
}
