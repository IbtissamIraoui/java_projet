import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GenerateurEmploiDuTemps {

    public static void main(String[] args) {
        List<Professeur> professeurs = saisieProfesseurs();
        genererEmploiDuTemps(professeurs);
    }

    private static List<Professeur> saisieProfesseurs() {
        List<Professeur> professeurs = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Saisie des informations des professeurs (entrez 'fin' pour terminer) :");
        while (true) {
            System.out.print("Nom du professeur (ou 'fin' pour terminer) : ");
            String nom = scanner.nextLine();
            if (nom.equalsIgnoreCase("fin")) {
                break;
            }

            System.out.print("Matière enseignée : ");
            String matiere = scanner.nextLine();

            System.out.print("Jour de cours : ");
            String jour = scanner.nextLine();

            System.out.print("Heure de cours : ");
            String heure = scanner.nextLine();

            professeurs.add(new Professeur(nom, matiere, jour, heure));
        }

        return professeurs;
    }

    private static void genererEmploiDuTemps(List<Professeur> professeurs) {
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream("EmploiDuTemps.pdf"));
            document.open();

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);

            // En-tête du tableau
            table.addCell(createCell("Nom du professeur", true));
            table.addCell(createCell("Matière enseignée", true));
            table.addCell(createCell("Jour de cours", true));
            table.addCell(createCell("Heure de cours", true));

            // Ajout des professeurs au tableau
            for (Professeur professeur : professeurs) {
                table.addCell(createCell(professeur.getNom()));
                table.addCell(createCell(professeur.getMatiere()));
                table.addCell(createCell(professeur.getJour()));
                table.addCell(createCell(professeur.getHeure()));
            }

            document.add(table);
            System.out.println("Emploi du temps généré avec succès : EmploiDuTemps.pdf");
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }

    private static PdfPCell createCell(String text) {
        return createCell(text, false);
    }

    private static PdfPCell createCell(String text, boolean isHeader) {
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        if (isHeader) {
            cell.setBackgroundColor(new com.itextpdf.text.BaseColor(200, 200, 200));
        }
        return cell;
    }
}

class Professeur {
    private String nom;
    private String matiere;
    private String jour;
    private String heure;

    public Professeur(String nom, String matiere, String jour, String heure) {
        this.nom = nom;
        this.matiere = matiere;
        this.jour = jour;
        this.heure = heure;
    }

    public String getNom() {
        return nom;
    }

    public String getMatiere() {
        return matiere;
    }

    public String getJour() {
        return jour;
    }

    public String getHeure() {
        return heure;
    }
}
