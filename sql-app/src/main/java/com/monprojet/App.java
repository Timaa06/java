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
            System.out.println("3 - Lister les utilisateurS");
            System.out.println("4 - Quitter");
            System.out.print("Choix : ");
            int choix = scanner.nextInt();
            scanner.nextLine(); // Éviter les erreurs de buffer

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
            } else {
                System.out.println("Choix invalide !");
            }

            // Afficher les utilisateurs après modification
            Statement stmt = connexion.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, nom, email FROM utilisateurs");

            System.out.println("\nListe des utilisateurs après modification :");
            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String email = rs.getString("email");
                System.out.println("ID : " + id + ", Nom : " + nom + ", Email : " + email);
            }

            rs.close();
            stmt.close();
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
