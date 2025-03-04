package com.monprojet;

import java.sql.*;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        // Informations de connexion
        String url = "jdbc:mysql://localhost:3306/maBase"; // Remplace "maBase" par le nom de ta base
        String utilisateur = "root";
        String motDePasse = "";
        Connection connexion = null;
        Scanner scanner = new Scanner(System.in); // Scanner pour la saisie utilisateur

        try {
            // Établir la connexion
            connexion = DriverManager.getConnection(url, utilisateur, motDePasse);
            System.out.println("Connexion réussie !");

            // Afficher le menu
            System.out.println("Que voulez-vous faire ?");
            System.out.println("1 - Ajouter un utilisateur");
            System.out.println("2 - Supprimer un utilisateur");
            System.out.println("3 - Lister les utilisateurs");
            System.out.println("4 - Éditer un utilisateur");
            System.out.println("5 - Rechercher un utilisateur");
            System.out.println("6 - Quitter");
            System.out.print("Choix : ");
            int choix = scanner.nextInt();
            scanner.nextLine(); // Consomme le saut de ligne après l'entier

            if (choix == 1) {
                // Ajouter un utilisateur
                System.out.print("Entrez le nom : ");
                String nom = scanner.nextLine();
                System.out.print("Entrez l'email : ");
                String email = scanner.nextLine();

                String sqlInsert = "INSERT INTO utilisateurs (nom, email) VALUES (?, ?)";
                PreparedStatement pstmt = connexion.prepareStatement(sqlInsert);
                pstmt.setString(1, nom);
                pstmt.setString(2, email);
                int rowsInserted = pstmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Utilisateur ajouté avec succès !");
                }
                pstmt.close();
            } else if (choix == 2) {
                // Supprimer un utilisateur
                System.out.print("Entrez l'ID de l'utilisateur à supprimer : ");
                int idSuppr = scanner.nextInt();

                String sqlDelete = "DELETE FROM utilisateurs WHERE id = ?";
                PreparedStatement pstmt = connexion.prepareStatement(sqlDelete);
                pstmt.setInt(1, idSuppr);
                int rowsDeleted = pstmt.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("Utilisateur supprimé avec succès !");
                } else {
                    System.out.println("Aucun utilisateur trouvé avec cet ID.");
                }
                pstmt.close();
            } else if (choix == 3) {
                // Lister les utilisateurs
                Statement stmt = connexion.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT id, nom, email FROM utilisateurs");

                System.out.println("\nListe des utilisateurs :");
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nom = rs.getString("nom");
                    String email = rs.getString("email");
                    System.out.println("ID : " + id + ", Nom : " + nom + ", Email : " + email);
                }

                rs.close();
                stmt.close();
            } else if (choix == 4) {
                // Éditer un utilisateur
                System.out.print("Entrez l'ID de l'utilisateur à éditer : ");
                int idEdit = scanner.nextInt();
                scanner.nextLine(); // Consomme le saut de ligne

                // Vérifier si l'utilisateur existe
                String sqlSelect = "SELECT id, nom, email FROM utilisateurs WHERE id = ?";
                PreparedStatement pstmtSelect = connexion.prepareStatement(sqlSelect);
                pstmtSelect.setInt(1, idEdit);
                ResultSet rs = pstmtSelect.executeQuery();

                if (rs.next()) {
                    // L'utilisateur existe, afficher ses informations
                    String nomActuel = rs.getString("nom");
                    String emailActuel = rs.getString("email");

                    System.out.println("Utilisateur trouvé :");
                    System.out.println("Nom actuel : " + nomActuel);
                    System.out.println("Email actuel : " + emailActuel);

                    // Demander les nouvelles informations
                    System.out.print("Entrez le nouveau nom (laisser vide pour ne pas modifier) : ");
                    String nouveauNom = scanner.nextLine();
                    if (nouveauNom.isEmpty()) {
                        nouveauNom = nomActuel; // Garder l'ancien nom si aucun nouveau nom n'est entré
                    }

                    System.out.print("Entrez le nouveau email (laisser vide pour ne pas modifier) : ");
                    String nouvelEmail = scanner.nextLine();
                    if (nouvelEmail.isEmpty()) {
                        nouvelEmail = emailActuel; // Garder l'ancien email si aucun nouveau email n'est entré
                    }

                    // Mettre à jour l'utilisateur
                    String sqlUpdate = "UPDATE utilisateurs SET nom = ?, email = ? WHERE id = ?";
                    PreparedStatement pstmtUpdate = connexion.prepareStatement(sqlUpdate);
                    pstmtUpdate.setString(1, nouveauNom);
                    pstmtUpdate.setString(2, nouvelEmail);
                    pstmtUpdate.setInt(3, idEdit);

                    int rowsUpdated = pstmtUpdate.executeUpdate();
                    if (rowsUpdated > 0) {
                        System.out.println("Utilisateur modifié avec succès !");
                    } else {
                        System.out.println("Erreur lors de la modification.");
                    }

                    pstmtUpdate.close();
                } else {
                    System.out.println("Aucun utilisateur trouvé avec l'ID " + idEdit);
                }

                rs.close();
                pstmtSelect.close();
            } else if (choix == 5) {
                // Rechercher un utilisateur
                System.out.println("Voulez-vous rechercher par :");
                System.out.println("1 - Nom");
                System.out.println("2 - Email");
                System.out.print("Choix : ");
                int critereRecherche = scanner.nextInt();
                scanner.nextLine(); // Consomme le saut de ligne

                if (critereRecherche == 1) {
                    // Recherche par nom
                    System.out.print("Entrez le nom de l'utilisateur à rechercher : ");
                    String nomRecherche = scanner.nextLine();
                    String sqlRechercheNom = "SELECT id, nom, email FROM utilisateurs WHERE nom LIKE ?";
                    PreparedStatement pstmtRechercheNom = connexion.prepareStatement(sqlRechercheNom);
                    pstmtRechercheNom.setString(1, "%" + nomRecherche + "%");
                    ResultSet rsNom = pstmtRechercheNom.executeQuery();

                    if (!rsNom.next()) {
                        System.out.println("Aucun utilisateur trouvé avec ce nom.");
                    } else {
                        System.out.println("\nUtilisateurs trouvés :");
                        do {
                            int id = rsNom.getInt("id");
                            String nom = rsNom.getString("nom");
                            String email = rsNom.getString("email");
                            System.out.println("ID : " + id + ", Nom : " + nom + ", Email : " + email);
                        } while (rsNom.next());
                    }

                    rsNom.close();
                    pstmtRechercheNom.close();
                } else if (critereRecherche == 2) {
                    // Recherche par email
                    System.out.print("Entrez l'email de l'utilisateur à rechercher : ");
                    String emailRecherche = scanner.nextLine();
                    String sqlRechercheEmail = "SELECT id, nom, email FROM utilisateurs WHERE email LIKE ?";
                    PreparedStatement pstmtRechercheEmail = connexion.prepareStatement(sqlRechercheEmail);
                    pstmtRechercheEmail.setString(1, "%" + emailRecherche + "%");
                    ResultSet rsEmail = pstmtRechercheEmail.executeQuery();

                    if (!rsEmail.next()) {
                        System.out.println("Aucun utilisateur trouvé avec cet email.");
                    } else {
                        System.out.println("\nUtilisateurs trouvés :");
                        do {
                            int id = rsEmail.getInt("id");
                            String nom = rsEmail.getString("nom");
                            String email = rsEmail.getString("email");
                            System.out.println("ID : " + id + ", Nom : " + nom + ", Email : " + email);
                        } while (rsEmail.next());
                    }

                    rsEmail.close();
                    pstmtRechercheEmail.close();
                } else {
                    System.out.println("Choix invalide.");
                }
            } else if (choix == 6) {
                System.out.println("Au revoir!");
                return;
            } else {
                System.out.println("Choix invalide !");
            }

        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        } finally {
            // Toujours fermer la connexion
            if (connexion != null) {
                try {
                    connexion.close();
                    System.out.println("Connexion fermée avec succès.");
                } catch (SQLException e) {
                    System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
                }
            }
            scanner.close();
        }
    }
}
