package EXO2;

public class CompteBancaire {
    private String titulaire;
    private double solde;

    public CompteBancaire (String titulaire, double solde) {
        this.titulaire = titulaire;
        this.solde = solde;
    }

    public void deposer (double montant) {
        if (montant > 0) {
            this.solde += montant;
            System.out.println(this.titulaire + "a déposé" + montant);
        }

    }
}
