import java.util.Scanner;

public class Compteur {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println( "Les nombres pairs compris entre 0 et 20 sont :");
        for (int i=0; i <= 20; i++)
       if (i%2 == 0)
       System.out.println(i);
      


    }
}
