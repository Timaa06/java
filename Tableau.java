public class Tableau {
    public static void main(String[] args) {
        int[] nombre = {1,2,3,4,5};
        // moyenne, médiane, écart type
       //int s = 1+2+3+4+5;
       // double moyenne = s/5;
       double sum = 0;
       for (int value :nombre){
        sum += value;
       }
double mean = sum / nombre.length;
        System.out.println("La moyenne est :" + mean);
    }
    
}
// tri et médiane
Arrays.sort(nombre);
double median;
if (nombre.length % 2 ==0){
    
}