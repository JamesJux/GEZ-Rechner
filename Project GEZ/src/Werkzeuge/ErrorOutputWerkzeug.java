package Werkzeuge;

import javax.swing.JOptionPane;

import Fachwerte.Errors;

/**
 *  Das Werkzeug zum Verarbeiten von Fehler und zum protokollieren dieser.
 *  
 * @author Dominick Labatz
 * @author Marvin Taube
 * @version 02.04.2020
 */
public class ErrorOutputWerkzeug
{
    /**
     *  Dient zum Verarbeiten von Fehler und zum protokollieren dieser.
     *  
     */
    public static void ErrorOutput(Errors error)
    {
        String output = null;

        switch (error)
        {
        case EinstellungenEinlesenError:
            output = "Einlesen der Einstellungen fehlerhaft, bitte überprüfen sie die Korrektheit der der Einstellungen.txt.";
            break;
        case BewohnerEinlesenError:
            output = "Einlesen der Bewohner fehlerhaft, bitte überprüfen sie die Korrektheit der Werte in der Bewohner.txt.";
            break;
        case EinAuszugBewohnerEinlesenError:
            output = "Daten des Ein- bzw. Auszuges fehlerhaft,vielleicht vertauscht? Bitte überprüfen sie die Korrektheit der Werte in der Bewohner.txt.";
            break;
        case EinzugBewohnerEinlesenError:
            output = "Einzugs Datum des Bewohner fehlerhaft, bitte überprüfen sie die Korrektheit der Werte in der Bewohner.txt.";
            break;
        case AuszugBewohnerEinlesenError:
            output = "Auszugs Datum des Bewohner fehlerhaft, bitte überprüfen sie die Korrektheit der Werte in der Bewohner.txt.";
            break;
        case DateiNichtGefundenError:
            output = "Die Bewohner konnte nicht eingelesen werden, da die Datei nicht gefunden wurde.";
            break;
        case DateiLesenError:
            output = "Die Bewohner konnte nicht eingelesen werden, da die Datei nicht gelesen werden konnte.";
            break;
        case UnfertigeMethode:
            output = "Die angeforderte Methode wurde nur teilweise oder noch garnicht implementiert.";
            break;
        case ProfilSpeichernError:
            output = "ProfileSetAddError: Problem beim Hinzufügen des Profils";
            break;
        case NamenEingabeError:
            output = "Problem beim Hinzufügen des Profils, der Name enthält leere Strings.";
            break;
        case MonatAusgabeError:
            output = "Ein Problem bei der Monatausgabe ist aufgetreten.";
            break;
        case ProfilEntfernenError:
            output = "ProfileSetRemoveError: Problem beim Entfernen des Profils";
            break;
        case BezahlerRegistrierenError:
            output = "BezahlerError: Problem beim Einlesen des Bezahlers";
            break;
        default:
            output = "ErrorOutputWerkzeug Error: Fehler bei der Fehlererkennung. Fehler wurde noch nicht im System hinzugefügt.";
        }
        String trace = "\nTrace:" +
                "\nDatei: " + new Throwable().getStackTrace()[1].getFileName() +
                "\nClass: " + new Throwable().getStackTrace()[1].getClassName() +
                "\nMethode: " + new Throwable().getStackTrace()[1].getMethodName() +
                "\nZeile: " + new Throwable().getStackTrace()[1].getLineNumber();
        System.out.println(output + trace);
        DateiWerkzeug.loggeFehler(output + trace);
        ErrorOutputTextfile(output);
        System.exit(1);
    }

    private static void ErrorOutputTextfile(String ErrorString)
    {
        JOptionPane.showMessageDialog(null, ErrorString
                + "\nFür mehr Informationen lesen Sie bitte die Log-Datei.\nDas Programm wurde beendet...", "Ein Fehler ist aufgetreten...", JOptionPane.WARNING_MESSAGE);
    }

    /*
     * TODO eine Methode die zur Überprüfung dienen kann um zu schauen ob bis zu einem gewissen Punkt Errors entstanden sind
     */
    public static void ErrorCheck()
    {
        ErrorOutputWerkzeug.ErrorOutput(Errors.UnfertigeMethode);
    }
}
