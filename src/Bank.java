import java.util.Scanner;

public class Bank {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

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
            scan.nextLine();  //clear buffer

            clearConsole();

            switch (choice) {
                case 1:
                    System.out.println("Create Account selected.");
                    accountNumber = getAccountNumber(accountNumber,accountCreatedCount,scan);
                    holderName = getHolderName(holderName,accountCreatedCount,scan);
                    accountType = getAccountType(accountType,accountCreatedCount,scan);
                    accountBalance = initialDeposit(accountBalance,accountCreatedCount,scan);
                    accountCreatedCount++;
                    break;
                case 2:
                    System.out.println("Deposit Money selected.");
                    int accountNumberIndex = matchAccountNumber(accountNumber,scan);
                    accountBalance = deposit(accountNumberIndex,accountBalance,scan);
                    break;
                case 3:
                    System.out.println("Withdraw Money selected.");
                    accountNumberIndex = matchAccountNumber(accountNumber,scan);
                    accountBalance = withdraw(accountNumberIndex,accountBalance,scan);
                    break;
                case 4:
                    System.out.println("Check Balance selected.");
                    accountNumberIndex = matchAccountNumber(accountNumber,scan);
                    checkAccountBalance(accountNumberIndex,accountBalance);
                    break;
                case 5:
                    System.out.println("Apply for Loan selected.");
                    accountNumberIndex = matchAccountNumber(accountNumber,scan);
                    addLoanDescription(accountNumberIndex,loanDescription,scan);
                    addLoanAmount(accountNumberIndex,loanAmount,scan);
                    break;
                case 6:
                    System.out.println("View Loan Details selected.");
                    accountNumberIndex = matchAccountNumber(accountNumber,scan);
                    showLoanAmount(accountNumberIndex,loanAmount);
                    showLoanDescription(accountNumberIndex,loanDescription);
                    break;
                case 7:
                    System.out.println("Transfer Funds selected.");
                    System.out.println("Add sender details below : ");
                    accountNumberIndex = matchAccountNumber(accountNumber,scan);
                    System.out.println("Add reciever details below : ");
                    int accountNumberReciverIndex = matchAccountNumber(accountNumber,scan);
                    accountBalance = withdraw(accountNumberIndex,accountBalance,scan);
                    accountBalance = deposit(accountNumberReciverIndex,accountBalance,scan);
                    break;
                case 8:
                    System.out.println("Exiting the application...");
                    break;
                default:
                    System.out.println("Invalid choice! Please select a number between 1 and 8.");
                    break;
            }


            if (choice != 8) {
                System.out.println("\nPress Enter to return to the main menu..");
                scan.nextLine(); // wait for user
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

    public static int[] getAccountNumber(int[] accountNumbersArr, int createdCount,Scanner scan) {
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

    public static String[] getHolderName(String[] holderNameArr, int createdCount,Scanner scan) {
        System.out.print("Enter account holder name : ");
        holderNameArr[createdCount] = scan.next();
        return holderNameArr;
    }

    public static String[] getAccountType(String[] accountTypeArr, int createdCount, Scanner scan){
        System.out.println(" 1.Saving Account \n 2.Current Account \n 3.Wanitha wasana Account");
        System.out.print("Enter Account type : ");
        accountTypeArr[createdCount] = scan.next();

        return accountTypeArr;
    }

    public static double[] initialDeposit(double[] accountBalanceArr,int createdCount,Scanner scan){
        System.out.print("Enter account balance to deposit : ");
        accountBalanceArr[createdCount] = scan.nextDouble();

        return accountBalanceArr;
    }

    public static int matchAccountNumber(int[] accountNumbers, Scanner scan){
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
            }
        }
        return value;
    }

    public static double[] deposit(int index, double[] balanceArr, Scanner scan){
        System.out.print("Enter amount to deposit : ");
        double amount = scan.nextDouble();

        balanceArr[index] += amount;
        System.out.println("Deposit succesfully! New Balance is " +balanceArr[index]);
        return balanceArr;
    }

    public static double[] withdraw(int index, double[] balanceArr, Scanner scan){
        System.out.println("Enter amount to withdraw : ");
        double amount = scan.nextDouble();

        balanceArr[index] -= amount;
        System.out.println("Withdraw succesfully! New Balance is " +balanceArr[index]);
        return balanceArr;
    }

    public static void checkAccountBalance(int index, double[] balanceArr){
        System.out.println("Your account balance is " +balanceArr[index]);
    }

    public static String[] addLoanDescription(int index, String[] loanDescription, Scanner scan){
        System.out.print("Enter loan description : ");
        loanDescription[index] = scan.next();
        return loanDescription;
    }

    public static double[] addLoanAmount(int index, double[] loanAmount, Scanner scan){
        System.out.print("Enter loan amount : ");
        loanAmount[index] = scan.nextDouble();
        return loanAmount;
    }

    public static void showLoanAmount(int index,double[] loanAmount){
        System.out.print("Your loan amount is " +loanAmount[index]);
    }

    public static void showLoanDescription(int index, String[] loanDescription){
        System.out.println("Your loan description is : " +loanDescription[index]);
    }
}
