import java.util.Scanner;

public class Age {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Entrez votre âge : ");
    
        Integer age = scanner.nextInt();
        if (age < 18) {
            System.out.println("Bonjour, vous avez " + age + " ans " + "et vous êtes mineur");
        }
        else {
            System.out.println("Bonjour, vous avez " + age + " ans " + "et vous êtes majeur");
        }
       
    }
    
}
