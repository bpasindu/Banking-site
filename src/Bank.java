import java.util.Scanner;

public class Bank {

    static Scanner scan = new Scanner(System.in);
    
    public static void main(String[] args) {


        int choice;
        int[] accountNumber = new int[50];
        String[] holderName = new String[50];
        double[] accountBalance = new double[50];
        String[] accountType =  new String[50];
        String[] loanDescription = new String[50];
        double[] loanAmount = new double[50];

        int accountCreatedCount = 0;

        do {
            //clearConsole();
            System.out.println("========================================");
            System.out.println("         WELCOME TO BANK SYSTEM         ");
            System.out.println("========================================");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Check Balance");
            System.out.println("5. Apply for Loan");
            System.out.println("6. View Loan Details");
            System.out.println("7. Transfer Funds");
            System.out.println("8. Exit");
            System.out.println("========================================");
            System.out.print("Enter your choice (1-8): ");

            // Handle invalid input (non-integer)
            while (!scan.hasNextInt()) {
                System.out.println("Invalid input! Please enter a number between 1-8.");
                System.out.print("Enter your choice again: ");
                scan.next(); // discard invalid input
            }

            choice = scan.nextInt();
            //clearConsole();

            switch (choice) {
                case 1:
                    System.out.println("Create Account selected.");
                    accountNumber = getAccountNumber(accountNumber,accountCreatedCount);
                    holderName = getHolderName(holderName,accountCreatedCount);
                    accountType = getAccountType(accountType,accountCreatedCount);
                    accountBalance = initialDeposit(accountBalance,accountCreatedCount);
                    System.out.println("Account created successfully!!!");
                    accountCreatedCount++;
                    break;
                case 2:
                    System.out.println("Deposit Money selected.");
                    int accountNumberIndex = matchAccountNumber(accountNumber);
                    accountBalance = deposit(accountNumberIndex,accountBalance);
                    break;
                case 3:
                    System.out.println("Withdraw Money selected.");
                    accountNumberIndex = matchAccountNumber(accountNumber);
                    accountBalance = withdraw(accountNumberIndex,accountBalance);
                    break;
                case 4:
                    System.out.println("Check Balance selected.");
                    accountNumberIndex = matchAccountNumber(accountNumber);
                    checkAccountBalance(accountNumberIndex,accountBalance);
                    break;
                case 5:
                    System.out.println("Apply for Loan selected.");
                    accountNumberIndex = matchAccountNumber(accountNumber);
                    loanDescription = addLoanDescription(accountNumberIndex,loanDescription);
                    loanAmount = addLoanAmount(accountNumberIndex,loanAmount);
                    break;
                case 6:
                    System.out.println("View Loan Details selected.");
                    accountNumberIndex = matchAccountNumber(accountNumber);
                    showLoanAmount(accountNumberIndex,loanAmount);
                    showLoanDescription(accountNumberIndex,loanDescription);
                    break;
                case 7:
                    System.out.println("Transfer Funds selected.");
                    accountBalance = transferFund(accountNumber,accountBalance);
                    break;
                case 8:
                    System.out.println("Exiting the application...");
                    break;
                default:
                    System.out.println("Invalid choice! Please select a number between 1 and 8.");
                    break;
            }

        } while (choice != 8);

    }

    public static void clearConsole() {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public static int[] getAccountNumber(int[] accountNumbersArr, int createdCount) {
        while (true) {
            System.out.print("Enter New Account Number: ");

            while (!scan.hasNextInt()) {
                System.out.println("Invalid input! Please enter a correct number.");
                System.out.print("Enter New Account Number: ");
                scan.next(); // discard invalid input
            }

            int newAccountNumber = scan.nextInt();
            scan.nextLine();
            boolean exists = false;

            // Check if number already exists
            for (int i = 0; i < createdCount; i++) {
                if (newAccountNumber == accountNumbersArr[i]) {
                    exists = true;
                    break;
                }
            }

            if (exists) {
                System.out.println("Number already exists! Please enter a new number.\n");
            } else {
                accountNumbersArr[createdCount] = newAccountNumber;
                break;
            }
        }

        return accountNumbersArr;
    }

    public static String[] getHolderName(String[] holderNameArr, int createdCount) {
        System.out.print("Enter account holder name : ");
        holderNameArr[createdCount] = scan.next();
        return holderNameArr;
    }

    public static String[] getAccountType(String[] accountTypeArr, int createdCount){
        System.out.println(" 1.Saving Account \n 2.Current Account \n 3.Wanitha wasana Account");
        System.out.print("Enter Account type : ");
        accountTypeArr[createdCount] = scan.next();
        return accountTypeArr;
    }

    public static double[] initialDeposit(double[] accountBalanceArr,int createdCount){
        System.out.print("Enter account balance to deposit : ");

        while (!scan.hasNextDouble()) {
            System.out.print("Invalid input! Please enter a valid amount : ");
            scan.next();
        }

        accountBalanceArr[createdCount] = scan.nextDouble();

        return accountBalanceArr;
    }

    public static int matchAccountNumber(int[] accountNumbers){
        System.out.print("Enter account holder name : ");
        String name = scan.next();

        int currentAccountNumber =0;
        while(true) {
            System.out.print("Enter account number : ");
            currentAccountNumber = scan.nextInt();
            boolean valid = checkValidAccount(currentAccountNumber,accountNumbers);

            if(valid){
                break;
            }else{
                System.out.println("Wrong account number! Retry!!!");
            }
        }

        int accountNumberIndex = 0;

        for(int i=0; i<accountNumbers.length; i++){
            if(currentAccountNumber == accountNumbers[i]){
                accountNumberIndex = i;
            }
        }

        return accountNumberIndex;
    }

    public static boolean checkValidAccount(int accountNumber, int[] accountNumbers){
        boolean value = false;
        for(int i=0; i<accountNumbers.length;i++){
            if(accountNumbers[i] == accountNumber){
                value = true;
                break;
            }
        }
        return value;
    }

    public static double[] deposit(int index, double[] balanceArr){

        System.out.print("Enter amount to deposit : ");

        while (!scan.hasNextDouble()) {
            System.out.print("Invalid input! Please enter a valid amount : ");
            scan.next();
        }
        double amount = scan.nextDouble();

        balanceArr[index] += amount;
        System.out.println("Deposit succesfully! New Balance is " +balanceArr[index]);
        return balanceArr;
    }

    public static double[] withdraw(int index, double[] balanceArr){
        double amount=0;
        do {
            System.out.print("Enter amount to withdraw : ");
            while (!scan.hasNextDouble()) {
                System.out.println("Invalid input! Please enter a valid amount");
                scan.next();
            }
            amount = scan.nextDouble();

            if(amount<balanceArr[index]){
                break;
            }else{
                System.out.println("amount is insufficient! Retry with new amount.");
            }
        }while(true);
        balanceArr[index] -= amount;
        System.out.println("Withdraw succesfully! New Balance is " +balanceArr[index]);
        return balanceArr;
    }

    public static void checkAccountBalance(int index, double[] balanceArr){
        System.out.println("Your account balance is " +balanceArr[index]);
    }

    public static String[] addLoanDescription(int index, String[] loanDescription){
        System.out.print("Enter loan description : ");
        loanDescription[index] = scan.next();
        return loanDescription;
    }

    public static double[] addLoanAmount(int index, double[] loanAmount){
        System.out.print("Enter loan amount : ");
        while (!scan.hasNextDouble()) {
            System.out.println("Invalid input! Please enter a valid amount");
            scan.next();
        }
        loanAmount[index] = scan.nextDouble();
        return loanAmount;
    }

    public static void showLoanAmount(int index,double[] loanAmount){
        System.out.print("Your loan amount is " +loanAmount[index]);
    }

    public static void showLoanDescription(int index, String[] loanDescription){
        System.out.println("Your loan description is : " +loanDescription[index]);
    }

    public static double[] transferFund(int[] accountNumberArr, double[] accountBalanceArr){
        System.out.print("Enter sender name : ");
        String name = scan.next();
        int senderNumber =0;
        while(true) {
            System.out.print("Enter sender account : ");
            senderNumber= scan.nextInt();
            if(checkValidAccount(senderNumber,accountNumberArr)){
                break;
            }else{
                System.out.println("Account number is incorrect. Retry!");
            }
        }
        System.out.print("Enter reciever name : ");
        String recieverName = scan.next();
        int recieverNumber=0;
        do {
            System.out.print("Enter reciever account : ");
            recieverNumber = scan.nextInt();
            boolean value = false;
            if(recieverNumber == senderNumber){
                value = true;
                System.out.println("Reciever and sender acount number con not be same");
            }else{
                break;
            }
        }while(true);
        while(true) {
            if(checkValidAccount(recieverNumber,accountNumberArr)){
                break;
            }else{
                System.out.println("Account number is incorrect. Retry!");
                System.out.print("Enter reciever account number : ");
                recieverNumber = scan.nextInt();
            }
        }



        int senderAccountNumberIndex = 0;

        for(int i=0; i<accountNumberArr.length; i++){
            if(senderNumber == accountNumberArr[i]){
                senderAccountNumberIndex = i;
            }
        }

        int recieverAccountNumberIndex = 0;

        for(int i=0; i<accountNumberArr.length; i++){
            if(recieverNumber == accountNumberArr[i]){
                recieverAccountNumberIndex = i;
            }
        }

        double amount =0;
        do{
            System.out.print("Enter amount to transfer : ");
            while (!scan.hasNextDouble()) {
                System.out.print("Invalid input! Please enter a valid amount : ");
                scan.next();
            }
            amount = scan.nextDouble();

            if(amount<accountBalanceArr[senderAccountNumberIndex]){
                break;
            }else{
                System.out.println("Amount is insufficient! Retry with new value");
            }
        }while(true);
        accountBalanceArr[senderAccountNumberIndex] -= amount;
        accountBalanceArr[recieverAccountNumberIndex] += amount;

        return accountBalanceArr;
    }
}