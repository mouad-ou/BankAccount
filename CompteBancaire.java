import java.util.Scanner;

public class CompteBancaire {
    String titulaire;
    double solde;
    int nc;

    public CompteBancaire(String titulaire, double solde, int nc) {
        this.titulaire = titulaire;
        this.solde = solde;
        this.nc = nc;
    }

    public static CompteBancaire authentification(CompteBancaire[] cb) {
        Scanner sc = new Scanner(System.in);
        boolean accountFound = false;
        CompteBancaire foundAccount = null;

        while (!accountFound) {
            System.out.println("Give your name: ");
            String t = sc.next();
            System.out.println("Give your account number: ");
            int nc = sc.nextInt();
            for (CompteBancaire account : cb) {
                if (t.equals(account.titulaire) && nc == account.nc) {
                    foundAccount = account;
                    accountFound = true;
                    break;
                }
            }

            if (!accountFound) {
                System.out.println("Account not found. Do you want to try again? (Y/N): ");
                String tryAgain = sc.next();
                if (!tryAgain.equalsIgnoreCase("Y")) {
                    break;
                }
            }
        }

        return foundAccount;
    }

    public static void depot(CompteBancaire c) {
        double d = CompteBancaire.askforamountPlus(c);
        c.solde += d;
    }

    public static void retrait(CompteBancaire c) {
        double d = CompteBancaire.askforamount(c);
        c.solde -= d;
    }

    public static void virement(CompteBancaire c, CompteBancaire[] cb) {
        Scanner sc = new Scanner(System.in);
        double amountToTransfer = CompteBancaire.askforamount(c);
        System.out.println("Give the account's name that you want to transfer to:");
        String recipientName = sc.next();

        CompteBancaire recipient = null;

        for (CompteBancaire account : cb) {
            if (recipientName.equals(account.titulaire)) {
                recipient = account;
                break;
            }
        }

        if (recipient != null) {
            if (amountToTransfer <= c.solde) {
                c.solde -= amountToTransfer;
                recipient.solde += amountToTransfer;
                System.out.println("Transfer successful.");
            } else {
                System.out.println("You don't have enough money to transfer this amount.");
            }
        } else {
            System.out.println("Recipient account not found.");
        }
    }

    public void afficher() {
        System.out.println("************************************************************************************");
        System.out.println("titulaire:" + this.titulaire + " | Solde:" + this.solde + " | Numéro de compte:" + this.nc);
        System.out.println("************************************************************************************");

    }
    public static double askforamountPlus(CompteBancaire c) {
        Scanner sc = new Scanner(System.in);
        System.out.println("How much money do you want to deal with?");
        double amount;
        do {
            amount = sc.nextDouble();
            if (amount < 0) {
                System.out.println("You can't deal with a negative amount of money. Try again: ");
            } else if (amount == 0) {
                System.out.println("You can't deal with 0 amount of money. Try again: ");
            }
        } while (amount < 0 || amount == 0 );
        return amount;
    }
    public static double askforamount(CompteBancaire c) {
        Scanner sc = new Scanner(System.in);
        System.out.println("How much money do you want to deal with?");
        double amount;
        do {
            amount = sc.nextDouble();
            if (amount < 0) {
                System.out.println("You can't deal with a negative amount of money. Try again: ");
            } else if (amount == 0) {
                System.out.println("You can't deal with 0 amount of money. Try again: ");
            } else if (amount > c.solde) {
                System.out.println("You don't have enough money to deal with this amount. Try again: ");
            }
        } while (amount < 0 || amount == 0 || amount > c.solde);
        return amount;
    }

    public static void DisplayNewBalance(CompteBancaire c) {
        System.out.println("Your new Balance is: " + c.solde);
    }

    public static void main(String[] argv) {
        System.out.println("___________________________Welcome to Mouaad's Bank___________________________");
        CompteBancaire[] cb = new CompteBancaire[3];
        cb[0] = new CompteBancaire("Mouaad", 1000, 123);
        cb[1] = new CompteBancaire("Youssef", 3000, 456);
        cb[2] = new CompteBancaire("Amal", 500, 789);

        Scanner sc = new Scanner(System.in); // Create a single Scanner for the entire program

        CompteBancaire CB = CompteBancaire.authentification(cb);

        if (CB != null) {
            int choice;
            while (true) {
                System.out.println("How can we help you, client?");
                System.out.println("1• Dépôt Argent: Pour déposer un montant au compte courant");
                System.out.println("2• Retrait Argent: Pour le retrait d’un montant tout en vérifiant le solde");
                System.out.println("3• Virement: Pour effectuer un virement entre deux comptes");
                System.out.println("4• Affichage: Pour afficher les informations de compte");
                System.out.println("5• Exit: To exit the program");
                choice = sc.nextInt();

                if (choice == 5) {
                    System.out.println("Exiting the program. Thank you!");
                    break; // Exit the loop
                }
                switch (choice) {
                    case 1:
                        CompteBancaire.depot(CB);
                        CompteBancaire.DisplayNewBalance(CB);
                        break;

                    case 2:
                        CompteBancaire.retrait(CB);
                        CompteBancaire.DisplayNewBalance(CB);
                        break;

                    case 3:
                        CompteBancaire.virement(CB, cb);
                        CompteBancaire.DisplayNewBalance(CB);
                        break;

                    case 4:
                        CB.afficher();
                        break;

                    default:
                        System.out.println("Invalid choice");
                        break;
                }
            }
        }
    }
}
