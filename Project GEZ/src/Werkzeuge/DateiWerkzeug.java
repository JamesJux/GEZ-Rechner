package Werkzeuge;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

import Fachwerte.Errors;
import Materialien.Profil;
import Werkzeuge.Guthaben.GuthabenWerkzeug;
import Werkzeuge.ProfilManager.ProfilWerkzeug;

/**
 *  Das Werkzeug zum Verarbeiten der Bewohner, der Einstellungen und anderer Dateien.
 *  
 * @author Dominick Labatz
 * @version 02.04.2020
 */
public class DateiWerkzeug
{
    private static final String PATH = "./Textdateien";
    private static final File BEWOHNER_DATEI = new File(PATH + "/Bewohner.txt");
    private static final File OUTPUT = new File(PATH + "/Output Bewohner.txt");
    private static final File EINSTELLUNGEN = new File(PATH + "/Einstellungen.txt");
    private static final File LOGDATEI = new File(PATH + "/Log-Datei.txt");

    static ProfilWerkzeug PW;
    private static boolean _einstellungenVollständig;

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
     * Methode zur Erstinitialisierung.
     * Sie erstellt die notwendigen Textdateien um beim nächsten mal erfolgreich zu starten und schreibt notwendige Informationen in die Textdateien. 
     * 
     * Danach ist der Benutzer angeweiesen die notwendigen Einstellungen zu treffen.
     */
    private static void erstInitialisierung()
    {
        PrintStream printer;
        try
        {
            printer = new PrintStream(EINSTELLUNGEN);
            printer.println("Bereits_Initialisiert=false");
            printer.println("Bitte ändern sie in der Ersten Zeile das 'false' auf 'true' damit die aktuellen Einstellungen gespeichert werden.");
            printer.println("Bezahler=");
            printer.println("SeitJahr=");
            printer.println("SeitMonat=");
            printer.println("Beitragsnummer='9-stellige Zahl'");
            printer.println("Geburtstag=TT.MM.JJJJ");
            //printer.print("Sprache='de/en'");
            printer.close();
            printer = new PrintStream(BEWOHNER_DATEI);
            printer.println("'Zimmernr';VORNAME;NACHNAME;EMAIL;HANDY_NR;GUTHABEN_IN_CENT;EINZUGS_MONAT;EINZUGS_JAHR;AUSZUGS_MONAT;AUSZUGS_JAHR");
            printer.print("1/515;Dominick;Labatz;<EMAIL>;<Handynr.>;0;12;2018;0;0");
            printer.close();
            printer = new PrintStream(OUTPUT);
            printer.close();
            printer = new PrintStream(LOGDATEI);
            printer.close();
        }
        catch (IOException a)
        {
        }
    }

    /**
    * Liest Bewohner aus einer Textdatei ein 
    * 
    * Die Bewohnerinformationen müssen mit einem ";" getrennt sein.
    * 
    * Die Reihenfolge der Bewohnerinformationen:
    * Zimmernummer; Vorname; Nachname; EMail; Handynummer; Eingezahltes Geld; Einzug(Monat;Jahr); Auszug(Monat;Jahr)
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
                String Zimmer = tokenizer.nextToken();
                String Vorname = tokenizer.nextToken();
                String Nachname = tokenizer.nextToken();
                String EMail = tokenizer.nextToken();
                String Handynr = tokenizer.nextToken();
                int Guthaben = Integer.valueOf(tokenizer.nextToken());
                int EinMonat = Integer.valueOf(tokenizer.nextToken());
                if (EinMonat > 12 || EinMonat < 1)
                {
                    ErrorOutputWerkzeug.ErrorOutput(Errors.BewohnerEinlesenError);
                }
                int EinJahr = Integer.valueOf(tokenizer.nextToken());
                if (EinJahr < 2018 || EinJahr > 2050)
                {
                    ErrorOutputWerkzeug.ErrorOutput(Errors.BewohnerEinlesenError);
                }
                int AusMonat = Integer.valueOf(tokenizer.nextToken());
                if (AusMonat > 12 || AusMonat < 1)
                {
                    ErrorOutputWerkzeug.ErrorOutput(Errors.BewohnerEinlesenError);
                }
                int AusJahr = Integer.valueOf(tokenizer.nextToken());
                if (AusJahr < 2018 || AusJahr > 2100)
                {
                    ErrorOutputWerkzeug.ErrorOutput(Errors.BewohnerEinlesenError);
                }

                PW.erstelleProfil(Zimmer, Vorname, Nachname, Guthaben, EinMonat, EinJahr, EMail, Handynr, AusMonat, AusJahr);
            }
        }
        catch (FileNotFoundException e)
        {
            ErrorOutputWerkzeug.ErrorOutput(Errors.DateiNichtGefundenError);
        }
        catch (IOException e)
        {
            ErrorOutputWerkzeug.ErrorOutput(Errors.DateiLesenError);
        }
    }

    /**
     * Speichert beim Beenden des Programms alle Informationen in die Textdatei "Output Bewohner" Textdatei.
     */
    public static void speichereInDatei()
    {
        try (PrintStream printer = new PrintStream(OUTPUT))
        {
            for (Profil p : PW.getProfile())
            {
                String aa = p.getZimmer();
                String ab = p.getVorname();
                String ac = p.getNachname();
                String ad = p.getEmail();
                String ae = p.getHandynummer();
                int af = p.getGuthaben().getBetragInCent();
                String ag = "" + p.getEinzugsdatum().get(Calendar.MONTH);
                String ah = "" + p.getEinzugsdatum().get(Calendar.YEAR);
                String ai = "" + p.getAuszugsdatum().get(Calendar.MONTH);
                String aj = "" + p.getAuszugsdatum().get(Calendar.YEAR);

                printer.println(aa + ";" + ab + ";" + ac + ";" + ad + ";" + ae + ";" + af + ";" + ag + ";" + ah + ";" + ai + ";" + aj);
            }
            printer.close();
        }
        catch (FileNotFoundException e)
        {
        }
    }

    /**
     * Liest die vorhandenen Einstellungen ein.
     * 
     * Notwendig für die Funktionalität.
     */
    public static void leseEinstellungenEin()
    {
        int eingeleseneEinstellungen = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(EINSTELLUNGEN)))
        {
            String line = null;
            while ((line = reader.readLine()) != null)
            {
                StringTokenizer tokenizer = new StringTokenizer(line, "=");
                String eingelesenerString = tokenizer.nextToken();

                switch (eingelesenerString)
                {
                case "Bezahler":
                    eingeleseneEinstellungen++;
                    String Bezahler = tokenizer.nextToken();
                    PW.registriereBezahler(Bezahler);
                    break;
                case "SeitJahr":
                    eingeleseneEinstellungen++;
                    String SeitJahr = tokenizer.nextToken();
                    GuthabenWerkzeug.registriereBezahlJahr(SeitJahr);
                    break;
                case "SeitMonat":
                    eingeleseneEinstellungen++;
                    String SeitMonat = tokenizer.nextToken();
                    GuthabenWerkzeug.registriereBezahlMonat(SeitMonat);
                    break;
                case "Beitragsnummer":
                    eingeleseneEinstellungen++;
                    String Beitragsnummer = tokenizer.nextToken();
                    System.out.println(Beitragsnummer);
                    //TODO: registriere(Beitragsnummer);
                    //ErrorOutputWerkzeug.ErrorOutput(Errors.UnfertigeMethode);
                    break;
                case "Geburtstag":
                    eingeleseneEinstellungen++;
                    String Geburtstag = tokenizer.nextToken();
                    System.out.println(Geburtstag);
                    //TODO: registriere(Geburtstag);
                    //ErrorOutputWerkzeug.ErrorOutput(Errors.UnfertigeMethode);
                    break;
                //                case "Sprache":
                //                    String Sprache = tokenizer.nextToken();
                //                    System.out.println(Sprache);
                //                    //TODO: registriere(Sprache);
                //                    ErrorOutputWerkzeug.ErrorOutput(Errors.UnfertigeMethode);
                //                    break;
                }
                if (eingeleseneEinstellungen == 5)
                {
                    _einstellungenVollständig = true;
                }
            }
        }
        catch (IOException e)
        {
            ErrorOutputWerkzeug.ErrorOutput(Errors.DateiLesenError);
        }
    }

    public static void loggeEinAuszahlung(String text)
    {
        try (PrintWriter printer = new PrintWriter(new FileWriter(LOGDATEI, true), true))
        {
            GregorianCalendar heute = new GregorianCalendar();
            printer.println(heute.get(Calendar.YEAR) + "-" + heute.get(Calendar.MONTH) + "-"
                    + heute.get(Calendar.DAY_OF_MONTH) + " - " + text);
        }
        catch (IOException e)
        {
        }
    }

    /**
     * Gibt aus ob alle notwendigen Einstellungen geladen werden konnten.
     * 
     * @return true wenn alle eingelesen werden konnten, anderfalls false.
     */
    public static boolean sindEinstellungenVollständig()
    {
        return _einstellungenVollständig;
    }
}
