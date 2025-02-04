import java.util.Scanner;

public class Ask {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); //création de l'objet
        System.out.print("Entrez votre nom :");
        String nom = scanner.nextLine(); // lecture de la saisie
        System.out.println("Bonjour, " + nom + "!");
        scanner.close(); // fermeture du scanner
    }
}
