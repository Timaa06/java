package com.monprojet;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GestionEtudiant {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/maBase"; // Remplace "maBase" par le nom de ta base
        String utilisateur = "root";
        String motDePasse = "";
        Connection connexion = null;
        Scanner scanner = new Scanner(System.in);

        try {
            connexion = DriverManager.getConnection(url, utilisateur, motDePasse);
            System.out.println("Connexion réussie !");
            
            while (true) {
                System.out.println("\nQue voulez-vous faire ?");
                System.out.println("1 - Ajouter un utilisateur");
                System.out.println("2 - Supprimer un utilisateur");
                System.out.println("3 - Lister les utilisateurs");
                System.out.println("4 - Quitter");
                System.out.print("Choix : ");
                int choix = scanner.nextInt();
                scanner.nextLine(); // Éviter les erreurs de buffer

                if (choix == 1) {
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
                    afficherUtilisateurs(connexion);
                } else if (choix == 4) {
                    System.out.println("Fermeture du programme...");
                    break;
                } else {
                    System.out.println("Choix invalide !");
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        } finally {
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

    public static void afficherUtilisateurs(Connection connexion) {
        try {
            Statement stmt = connexion.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, nom, email FROM utilisateurs");

            List<String> utilisateurs = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String email = rs.getString("email");
                utilisateurs.add("ID : " + id + ", Nom : " + nom + ", Email : " + email);
            }

            if (utilisateurs.isEmpty()) {
                System.out.println("Aucun utilisateur trouvé.");
            } else {
                System.out.println("\nListe des utilisateurs :");
                for (String utilisateur : utilisateurs) {
                    System.out.println(utilisateur);
                }
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'affichage des utilisateurs : " + e.getMessage());
        }
    }
}
