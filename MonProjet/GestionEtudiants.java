package MonProjet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GestionEtudiants {
    private List<Etudiant> listeEtudiants;

    public GestionEtudiants() {
        this.listeEtudiants = new ArrayList<>();
    }

    public void ajouterEtudiant(String nom, String prenom, String classe) {
        listeEtudiants.add(new Etudiant(nom, prenom, classe));
    }

    public void afficherEtudiants() {
        if (listeEtudiants.isEmpty()) {
            System.out.println("Aucun étudiant enregistré.");
        } else {
            for (Etudiant etudiant : listeEtudiants) {
                System.out.println(etudiant);
            }
        }
    }

    public void supprimerEtudiant(String nom) {
        Iterator<Etudiant> iterator = listeEtudiants.iterator();
        while (iterator.hasNext()) {
            Etudiant etudiant = iterator.next();
            if (etudiant.getNom().equalsIgnoreCase(nom)) {
                iterator.remove();
                System.out.println("Étudiant " + nom + " supprimé.");
                return;
            }
        }
        System.out.println("Étudiant " + nom + " non trouvé.");
    }
}