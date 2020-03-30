package Werkzeuge;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.StringTokenizer;

import Fachwerte.Errors;
import Materialien.Profil;
import Werkzeuge.ProfilManager.ProfilWerkzeug;

/**
 * 
 *  Das Werkzeug zum Verarbeiten der Bewohner und anderer Dateien.
 *  
 * @author Dominick
 * @version 28.03.2020
 */
public class DeteiWerkzeug
{
    private static final String PATH = "./Textdateien";
    private static final File BEWOHNER_DATEI = new File(PATH + "/Bewohner.txt");
    private static final File OUTPUT = new File(PATH + "/Output Bewohner.txt");
    private static final File EINSTELLUNGEN = new File(PATH + "/Einstellungen.txt");

    static ProfilWerkzeug PW;

    public static boolean bereitsInitialisiert(ProfilWerkzeug _profilWerkzeug)
    {
        PW = _profilWerkzeug;
        new File(PATH).mkdir();
        try (BufferedReader reader = new BufferedReader(new FileReader(EINSTELLUNGEN)))
        {
            String line = null;
            line = reader.readLine();
            StringTokenizer tokenizer = new StringTokenizer(line, "=");
            tokenizer.nextToken();
            String option = tokenizer.nextToken();
            if (option.equals("true"))
            {
                return true;
            }
        }
        catch (FileNotFoundException e)
        {
            erstInitialisierung();
        }
        catch (IOException e)
        {
        }
        return false;
    }

    /**
     * Hilfsmethode zum initialisieren.
     * 
     */
    private static void erstInitialisierung()
    {
        PrintStream printer;
        try
        {
            printer = new PrintStream(EINSTELLUNGEN);
            printer.println("Bereits_Initialisiert=false");
            printer.print("Bitte ändern sie in der Ersten Zeile das 'false' auf 'true' damit die aktuellen Einstellungen gespeichert werden.");
            printer.close();
            printer = new PrintStream(BEWOHNER_DATEI);
            printer.println("A/BCC;VORNAME;NACHNAME;EMAIL;HANDYNR;GUTHABENINCENT;EINZUGSMONAT;EINZUGS_JAHR;AUSZUGSMONAT;AUSZUGS_JAHR");
            printer.print("1/515;Dominick;Labatz;d.jamesjux@gmail.com;017696588507;0;12;2018;0;0");
            printer.close();
            printer = new PrintStream(OUTPUT);
            printer.close();
        }
        catch (IOException a)
        {
        }
    }

    /**
    * Liest Bewohner aus einer Textdatei ein 
    * 
    * Die Bewohnerinformationen müssen mit
    * einem ";" getrennt sein.
    * 
    * Die Reihenfolge der Kundeninformationen:
    * 
    * Zimmernummer(Haus/Flur+Zimmernr); Vorname; Nachname; EMail; Handynummer; Eingezahltes Geld; Einzug(Monat;Jahr); Auszug(Monat;Jahr)
    * 
    * @param profilManager Der Profil Manager, der die Bewohner erstellt.
    */
    public static void leseBewohnerEin()
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(BEWOHNER_DATEI)))
        {
            String line = null;
            // liest Datei Zeile für Zeile
            while ((line = reader.readLine()) != null)
            {
                StringTokenizer tokenizer = new StringTokenizer(line, ";");
                String ZimmerID = tokenizer.nextToken();
                String Vorname = tokenizer.nextToken();
                String Nachname = tokenizer.nextToken();
                String EMail = tokenizer.nextToken();
                String Handynr = tokenizer.nextToken();
                int Guthaben = Integer.valueOf(tokenizer.nextToken());
                int EinMonat = Integer.valueOf(tokenizer.nextToken());
                int EinJahr = Integer.valueOf(tokenizer.nextToken());
                int AusMonat = Integer.valueOf(tokenizer.nextToken());
                int AusJahr = Integer.valueOf(tokenizer.nextToken());

                PW.erstelleProfil(ZimmerID, Vorname, Nachname, Guthaben, EinMonat, EinJahr, EMail, Handynr, AusMonat, AusJahr);
            }
        }
        catch (FileNotFoundException e)
        {
            ErrorOutputWerkzeug.ErrorOutputConsole(Errors.fileNotFoundError);
        }
        catch (IOException e)
        {
            ErrorOutputWerkzeug.ErrorOutputConsole(Errors.fileNotReadError);
        }
    }

    public static void speichereInDatei()
    {
        try (PrintStream printer = new PrintStream(OUTPUT))
        {
            for (Profil p : PW.getProfile())
            {
                String aa = p.getZimmer().toFormattedString();
                String ab = p.getName().getVorname();
                String ac = p.getName().getNachname();
                String ad = p.getEmail();
                String ae = p.getHandynummer();
                int af = p.getGuthaben().getBetragInCent();
                String ag = p.getEinzugsdatum().toInstant().toString().substring(5, 7);
                String ah = p.getEinzugsdatum().toInstant().toString().substring(0, 4);
                String ai = p.getAuszugsdatum().toInstant().toString().substring(5, 7);
                String aj = p.getAuszugsdatum().toInstant().toString().substring(0, 4);

                printer.println(aa + ";" + ab + ";" + ac + ";" + ad + ";" + ae + ";" + af + ";" + ag + ";" + ah + ";" + ai + ";" + aj);
            }
            printer.close();
        }
        catch (FileNotFoundException e)
        {
        }
    }

    public static void leseEinstellungenEin()
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(EINSTELLUNGEN)))
        {
            String line = null;
            while ((line = reader.readLine()) != null)
            {
                StringTokenizer tokenizer = new StringTokenizer(line, "=");
                String eingelesenerString = tokenizer.nextToken();
                if (eingelesenerString.equals("Bezahlt"))
                {
                    String Bezahler = tokenizer.nextToken();
                    PW.registriereBezahler(Bezahler);
                }
            }
        }
        catch (IOException e)
        {
            ErrorOutputWerkzeug.ErrorOutputConsole(Errors.fileNotReadError);
        }
    }
}
