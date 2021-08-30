package Werkzeuge;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

import Fachwerte.Errors;
import Materialien.Profil;
import Werkzeuge.Guthaben.GuthabenWerkzeug;
import Werkzeuge.ProfilManager.ProfilWerkzeug;

/**
 *  Das Werkzeug zum Verarbeiten der Bewohner, der Einstellungen und anderer Dateien.
 *  
 * @author Dominick Labatz
 * @version 02.04.2020
 **/
public class DateiWerkzeug
{
    private static final String PATH = "./Textdateien";
    private static final File EINSTELLUNGEN = new File(PATH + "/Einstellungen.txt");
    private static final File GUTHABEN_LOG_DATEI = new File(PATH + "/Guthaben-Log-Datei.txt");
    private static final File FEHLER_LOG_DATEI = new File(PATH + "/Fehler-Log-Datei.txt");
    public static Boolean DEBUGMODE = false;
    private static String DATEI_ENDUNG = "data";
    public static String BEWOHNER = "/Bewohner.";
    public static String BEWOHNER_DATEI_NAME = BEWOHNER + DATEI_ENDUNG;
    public static File BEWOHNER_DATEI = new File(PATH + BEWOHNER_DATEI_NAME);
    private static File OUTPUT1;
    private static File OUTPUT2;
    private static boolean _einstellungenVollständig;
    static ProfilWerkzeug PW;

    public static void bereitsInitialisiert(ProfilWerkzeug _profilWerkzeug)
    {
        PW = _profilWerkzeug;
        new File(PATH).mkdir();
        try (BufferedReader reader = new BufferedReader(new FileReader(EINSTELLUNGEN)))
        {
        }
        catch (FileNotFoundException e)
        {
            erstInitialisierung();
        }
        catch (IOException e)
        {
        }
    }

    /**
     * Methode zur Erstinitialisierung.
     * Sie erstellt die notwendigen Textdateien, um beim nächsten mal erfolgreich zu starten und schreibt notwendige Informationen in die Textdateien. 
     * 
     * Da nach ist der Benutzer angewiesen, die notwendigen Einstellungen zu treffen.
     **/
    private static void erstInitialisierung()
    {

        JOptionPane.showMessageDialog(null, "Willkommen beim GEZ-Rechner\nBenutzungshinweise und weitere Hilfe erhälst du beim Programmierer\n"
                +
                "Lizensiert unter GNU General Public License v3.0\n2020, Dominick Labatz", "GEZ-Rechner", JOptionPane.PLAIN_MESSAGE);

        String vornameBezahler = JOptionPane.showInputDialog(null, "Wie heißt der Beitragszahler mit Vornamen?", "Vorname Beitragszahler", JOptionPane.PLAIN_MESSAGE);
        String nachnameBezahler = JOptionPane.showInputDialog(null, "Wie heißt der Beitragszahler mit Nachnamen?", "Nachname Beitragszahler", JOptionPane.PLAIN_MESSAGE);
        String beitragszahler_seit_monat = JOptionPane.showInputDialog(null, "Ab welchem Monat soll die Berechnung stattfinden?", "Berechnungszeitraum Monat", JOptionPane.PLAIN_MESSAGE);
        String beitragszahler_seit_jahr = JOptionPane.showInputDialog(null, "Ab welchem Jahr soll die Berechnung stattfinden?", "Berechnungszeitraum Jahr", JOptionPane.PLAIN_MESSAGE);
        String beitragsnummer = JOptionPane.showInputDialog(null, "Wie lautet die Beitragsnummer?", "Beitragsnummer", JOptionPane.PLAIN_MESSAGE);
        String geburtstag = JOptionPane.showInputDialog(null, "Wann hat der Beitragszahler Geburtstag?\nFormat: TT.MM.JJJJ", "Geburtstag", JOptionPane.PLAIN_MESSAGE);

        PrintStream printer;
        try
        {
            printer = new PrintStream(EINSTELLUNGEN);
            printer.println("Ändern sie hier nur Einstellungen wenn Sie wissen was sie machen...");
            printer.println("Bezahler=" + vornameBezahler + " " + nachnameBezahler);
            printer.println("BeitragszahlerSeitJahr=" + beitragszahler_seit_jahr);
            printer.println("BeitragszahlerSeitMonat=" + beitragszahler_seit_monat);
            printer.println("Beitragsnummer=" + beitragsnummer);
            printer.println("Geburtstag=" + geburtstag);
            printer.println("Debugmodus=false");
            //printer.print("Sprache='de/en'");
            printer.close();
            printer = new PrintStream(BEWOHNER_DATEI);
            printer.println(codiereString("# VORNAME;NACHNAME;EMAIL;HANDY_NR;GUTHABEN_IN_CENT;EINZUGS_MONAT;EINZUGS_JAHR;AUSZUGS_MONAT;AUSZUGS_JAHR"));
            printer.print(codiereString(vornameBezahler + ";" + nachnameBezahler + ";<E-Mail>;<Handynr.>;0;" + beitragszahler_seit_monat
                    + ";" + beitragszahler_seit_jahr + ";12;2099"));
            printer.close();
        }
        catch (IOException a)
        {
        }
    }

    /**
     * Liest Bewohner aus der Bewohner.data ein, falls dies nicht vorhanden ist wird versucht aus der Bewohner.txt einzulesen.
     * 
     * Siehe dort für weitere Informationen.
     **/
    public static void leseBewohnerEin()
    {
        File DATEI = new File(PATH + BEWOHNER + "data");
        try
        {
            leseBewohnerEin(DATEI);
        }
        catch (IOException e)
        {
            DATEI = new File(PATH + BEWOHNER + "txt");
            try
            {
                leseBewohnerEin(DATEI);
            }
            catch (IOException e1)
            {
                e1.printStackTrace();
            }

        }
    }

    /**
    * Liest Bewohner aus der Bewohner.data ein, falls dies nicht vorhanden ist wird versucht aus der Bewohner.txt einzulesen.
    * 
    * Die Bewohnerinformationen müssen mit einem ";" getrennt sein.
    * 
    * Die Reihenfolge der Bewohnerinformationen:
    * Vorname; Nachname; E-Mail; Handynummer; Eingezahltes Geld; Einzug(Monat;Jahr); Auszug(Monat;Jahr)
    **/
    public static void leseBewohnerEin(File datei) throws IOException
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(datei)))
        {
            String line = null;
            // liest Datei Zeile für Zeile
            while ((line = reader.readLine()) != null)
            {
                line = encodiereString(line);
                if (!line.contains("#")) // Überspringt Kommentarzeilen
                {
                    StringTokenizer tokenizer = new StringTokenizer(line, ";");
                    String Vorname = tokenizer.nextToken();
                    String Nachname = tokenizer.nextToken();
                    String EMail = tokenizer.nextToken();
                    String Handynr = tokenizer.nextToken();
                    try
                    {
                        int Guthaben = Integer.valueOf(tokenizer.nextToken());
                        int EinMonat = Integer.valueOf(tokenizer.nextToken());
                        if (EinMonat > 12 || EinMonat < 1)
                        {
                            ErrorOutputWerkzeug.ErrorOutput(Errors.EinzugBewohnerEinlesenError);
                        }
                        int EinJahr = Integer.valueOf(tokenizer.nextToken());
                        if (EinJahr < 2018 || EinJahr > 2050)
                        {
                            ErrorOutputWerkzeug.ErrorOutput(Errors.EinzugBewohnerEinlesenError);
                        }
                        int AusMonat = Integer.valueOf(tokenizer.nextToken());
                        if (AusMonat > 12 || AusMonat < 1)
                        {
                            ErrorOutputWerkzeug.ErrorOutput(Errors.AuszugBewohnerEinlesenError);
                        }
                        int AusJahr = Integer.valueOf(tokenizer.nextToken());
                        if (AusJahr < 2018 || AusJahr > 2100)
                        {
                            ErrorOutputWerkzeug.ErrorOutput(Errors.AuszugBewohnerEinlesenError);
                        }
                        if ((EinJahr * 12) + EinMonat > (AusJahr * 12) + AusMonat)
                        {
                            ErrorOutputWerkzeug.ErrorOutput(Errors.EinAuszugBewohnerEinlesenError);
                        }
                        PW.erstelleProfil(Vorname, Nachname, Guthaben, EinMonat, EinJahr, EMail, Handynr, AusMonat, AusJahr);
                    }
                    catch (NumberFormatException e)
                    {
                        ErrorOutputWerkzeug.ErrorOutput(Errors.BewohnerEinlesenError);
                    }
                }
            }
        }
    }

    /**
     * Speichert beim Beenden des Programms alle Informationen in die Datei "Bewohner.data" und speichert einmal in eine Datei mit Zeitstempel
     **/
    public static void speichereInDatei()
    {
        List<Profil> sortedProfile = new ArrayList<>(PW.getProfile());
        Collections.sort(sortedProfile, new Comparator<Profil>()
        {
            @Override
            public int compare(Profil p1, Profil p2)
            {
                return p1.getName().compareTo(p2.getName());
            }
        });

        try (PrintStream printer = new PrintStream(OUTPUT1))
        {
            if (DEBUGMODE)
            {
                printer.println(codiereString("# Bitte beachte das beim nächsten Start des GEZ-Rechners immer die \"Bewohner.data\" einlesen wird!"));
                printer.println(codiereString("# Um Änderungen die du im Debugmodus vorgenommen hast dauerhaft zu behalten, musst du diese Datei manuell in \"Bewohner.data\" umbennenen, oder ebendiese löschen"));
                printer.println(codiereString("# VORNAME;NACHNAME;EMAIL;HANDY_NR;GUTHABEN_IN_CENT;EINZUGS_MONAT;EINZUGS_JAHR;AUSZUGS_MONAT;AUSZUGS_JAHR"));
            }

            for (Profil p : sortedProfile)
            {
                String output = p.getVorname() + ";";
                output += p.getNachname() + ";";
                output += p.getEmail() + ";";
                output += p.getHandynummer() + ";";
                output += p.getGuthaben().getBetragInCent() + ";";
                output += führendeNull(p.getEinzugsdatum().get(Calendar.MONTH) + 1) + ";";
                output += p.getEinzugsdatum().get(Calendar.YEAR) + ";";
                output += führendeNull(p.getAuszugsdatum().get(Calendar.MONTH) + 1) + ";";
                output += p.getAuszugsdatum().get(Calendar.YEAR);

                printer.println(codiereString(output));
            }
            printer.close();
        }
        catch (FileNotFoundException e)
        {
            ErrorOutputWerkzeug.ErrorOutput(Errors.DateiNichtGefundenError);
        }

        try (PrintStream printer = new PrintStream(OUTPUT2))
        {
            if (DEBUGMODE)
            {
                printer.println(codiereString("# Bitte beachte das beim nächsten Start des GEZ-Rechners immer die \"Bewohner.data\" einlesen wird!"));
                printer.println(codiereString("# Um Änderungen die du im Debugmodus vorgenommen hast dauerhaft zu behalten, musst du diese Datei manuell in \"Bewohner.data\" umbennenen, oder ebendiese löschen."));
                printer.println(codiereString("# VORNAME;NACHNAME;EMAIL;HANDY_NR;GUTHABEN_IN_CENT;EINZUGS_MONAT;EINZUGS_JAHR;AUSZUGS_MONAT;AUSZUGS_JAHR"));
            }

            for (Profil p : sortedProfile)
            {
                String output = p.getVorname() + ";";
                output += p.getNachname() + ";";
                output += p.getEmail() + ";";
                output += p.getHandynummer() + ";";
                output += p.getGuthaben().getBetragInCent() + ";";
                output += führendeNull(p.getEinzugsdatum().get(Calendar.MONTH) + 1) + ";";
                output += p.getEinzugsdatum().get(Calendar.YEAR) + ";";
                output += führendeNull(p.getAuszugsdatum().get(Calendar.MONTH) + 1) + ";";
                output += p.getAuszugsdatum().get(Calendar.YEAR);

                printer.println(codiereString(output));
            }
            printer.close();
        }
        catch (FileNotFoundException e)
        {
            ErrorOutputWerkzeug.ErrorOutput(Errors.DateiNichtGefundenError);
        }
    }

    /**
     * Codiert im Normalfall die zu speichernden Strings in BASE64 um Veränderungen in den Textdateien vorzubeugen.
     * 
     * Wenn die Einstellung Debugmodus "True" ist, wird die zu speichernde Datei nicht codiert.
     **/
    private static String codiereString(String string)
    {
        if (!DEBUGMODE)
        {
            byte[] bytes;
            try
            {
                bytes = string.getBytes("UTF-8");
                string = Base64.getEncoder().encodeToString(bytes);
            }
            catch (UnsupportedEncodingException e)
            {
                ErrorOutputWerkzeug.ErrorOutput(Errors.StringCodierungsError);
            }
        }
        return string;
    }

    /**
     * Encodiert dem zu lesenden Strings aus BASE64 falls er codiert ist.
     **/
    private static String encodiereString(String string)
    {
        if (!string.contains(";") & !string.contains("#"))
        {
            byte[] bytes;
            bytes = Base64.getDecoder().decode(string);
            string = new String(bytes);
        }
        return string;
    }

    /**
     * Liest die vorhandenen Einstellungen ein.
     * 
     * Notwendig für die Funktionalität.
     **/
    public static void leseEinstellungenEin()
    {
        int eingeleseneEinstellungen = 0;
        String Beitragszahler = "";
        String Geburtstag = "";
        String Beitragsnummer = "";
        DEBUGMODE = false;
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
                    Beitragszahler = tokenizer.nextToken();
                    break;
                case "BeitragszahlerSeitJahr":
                    eingeleseneEinstellungen++;
                    String SeitJahr = tokenizer.nextToken();
                    GuthabenWerkzeug.registriereBezahlJahr(SeitJahr);
                    break;
                case "BeitragszahlerSeitMonat":
                    eingeleseneEinstellungen++;
                    String SeitMonat = tokenizer.nextToken();
                    GuthabenWerkzeug.registriereBezahlMonat(SeitMonat);
                    break;
                case "Beitragsnummer":
                    eingeleseneEinstellungen++;
                    Beitragsnummer = tokenizer.nextToken();
                    break;
                case "Geburtstag":
                    eingeleseneEinstellungen++;
                    Geburtstag = tokenizer.nextToken();
                    break;
                case "Debugmodus":
                    eingeleseneEinstellungen++;
                    String option = tokenizer.nextToken();
                    if (option.equals("true"))
                    {
                        DEBUGMODE = true;
                        DATEI_ENDUNG = "txt";
                        BEWOHNER_DATEI = new File(PATH + BEWOHNER + DATEI_ENDUNG);
                        OUTPUT1 = BEWOHNER_DATEI;
                        OUTPUT2 = new File(PATH + "/Debug Bewohner (" + getZeitstempel(0) + " " + getZeitstempel(5) + ")." + DATEI_ENDUNG);

                    }
                    else
                    {
                        BEWOHNER_DATEI = new File(PATH + BEWOHNER + DATEI_ENDUNG);
                        OUTPUT1 = BEWOHNER_DATEI;
                        OUTPUT2 = new File(PATH + "/Bewohner (" + getZeitstempel(0) + " " + getZeitstempel(5) + ")." + DATEI_ENDUNG);
                    }
                    break;
                //                case "Sprache":
                //                    String Sprache = tokenizer.nextToken();
                //                    System.out.println(Sprache);
                //                    //TODO: registriere(Sprache);
                //                    ErrorOutputWerkzeug.ErrorOutput(Errors.UnfertigeMethode);
                //                    break;
                }
                if (eingeleseneEinstellungen == 6)
                {
                    _einstellungenVollständig = true;
                }
            }
        }
        catch (IOException e)
        {
            ErrorOutputWerkzeug.ErrorOutput(Errors.DateiLesenError);
        }
        PW.registriereBeitragszahler(Beitragszahler, Beitragsnummer, Geburtstag);
    }

    public static void loggeEinAuszahlung(String text)
    {
        try (PrintWriter printer = new PrintWriter(new FileWriter(GUTHABEN_LOG_DATEI, true), true))
        {
            printer.println(getZeitstempel(0) + " - " + text);
        }
        catch (IOException e)
        {
            PrintStream printer;
            try
            {
                printer = new PrintStream(GUTHABEN_LOG_DATEI);
                printer.close();
                loggeEinAuszahlung(text);
            }
            catch (FileNotFoundException e1)
            {
            }
        }
    }

    private static String führendeNull(int zahl)
    {
        return (zahl < 10) ? "0" + zahl : "" + zahl;
    }

    /** Gibt eine Zeitstempel als String aus.
     * Genauigkeit: 
     * 0 - JJJJ-MM-DD
     * 1 - DD.MM
     * 2 - hh:mm
     * 3 - hh:mm:ss
     * 4 - hh.mm
     * 5 - hh.mm.ss
     * 
     * @param genauigkeit Gibt die Genauigkeit des Zeitstempels an.
     * 
     **/
    private static String getZeitstempel(int genauigkeit)
    {
        GregorianCalendar heute = new GregorianCalendar();
        String hJahr = "" + heute.get(Calendar.YEAR);
        String hMonat = führendeNull(heute.get(Calendar.MONTH) + 1);
        String hTag = führendeNull(heute.get(Calendar.DAY_OF_MONTH));
        String hStunde = führendeNull(heute.get(Calendar.HOUR_OF_DAY));
        String hMinute = führendeNull(heute.get(Calendar.MINUTE));
        String hSekunde = führendeNull(heute.get(Calendar.SECOND));

        String result = "";
        switch (genauigkeit)
        {
        case 0:
            result = hJahr + "-" + hMonat + "-" + hTag;
            break;
        case 1:
            result = hTag + "." + hMonat;
            break;
        case 2:
            result = hStunde + ":" + hMinute;
            break;
        case 3:
            result = hStunde + ":" + hMinute + ":" + hSekunde;
            break;
        case 4:
            result = hStunde + "." + hMinute;
            break;
        case 5:
            result = hStunde + "." + hMinute + "." + hSekunde;
            break;

        }
        return result;
    }

    public static void loggeFehler(String text)
    {
        try (PrintWriter printer = new PrintWriter(new FileWriter(FEHLER_LOG_DATEI, true), true))
        {
            printer.println(getZeitstempel(0) + " " + getZeitstempel(5) + "\n" + text);
        }
        catch (IOException e)
        {
            PrintStream printer;
            try
            {
                printer = new PrintStream(FEHLER_LOG_DATEI);
                printer.close();
                loggeFehler(text);
            }
            catch (FileNotFoundException e1)
            {
            }
        }
    }

    /**
     * Gibt aus ob alle notwendigen Einstellungen geladen werden konnten.
     * 
     * @return true wenn alle notwendigen Einstellungen eingelesen werden konnten, anderfalls false.
     */
    public static boolean sindEinstellungenVollständig()
    {
        return _einstellungenVollständig;
    }

    /**
    * Kopiert den Text in die Zwischenablage
    * 
    * @param text Den zu kopierenden Text.
    */
    public static void CopyToClipboard(String text)
    {
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(text), null);
    }
}
