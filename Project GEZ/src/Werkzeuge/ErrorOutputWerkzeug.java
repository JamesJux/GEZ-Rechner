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
        case EingabeError:
            output = "InputError: Eingabe ist fehlerhaft.";
            break;
        case UnfertigeMethode:
            output = "Die angeforderte Methode wurde nur teilweise oder noch garnicht implementiert.";
            break;
        case ProfilSpeichernError:
            output = "ProfileSetAddError: Problem beim Hinzufügen des Profils";
            break;
        case ProfilEntfernenError:
            output = "ProfileSetRemoveError: Problem beim Entfernen des Profils";
            break;
        case DateiLesenError:
            output = "Die Bewohner konnte nicht eingelesen werden, da die Datei nicht gefunden wurde.";
            break;
        case DateiSchreibenError:
            output = "Die Bewohner konnte nicht eingelesen werden, da die Datei nicht gelesen werden konnte.";
            break;
        default:
            output = "ErrorOutputWerkzeug Error: Fehler bei der Fehlererkennung. Fehler wurde noch nicht im System hinzugefügt.";
        }
        System.out.println(output);
        ErrorOutputTextfile(output);
    }

    private static void ErrorOutputTextfile(String ErrorString)
    {
        JOptionPane.showMessageDialog(null, ErrorString, "Fehler", JOptionPane.WARNING_MESSAGE);
    }

    /*
     * TODO eine Methode die zur Überprüfung dienen kann um zu schauen ob bis zu einem gewissen Punkt Errors entstanden sind
     */
    public static void ErrorCheck()
    {
        ErrorOutputWerkzeug.ErrorOutput(Errors.UnfertigeMethode);
    }
}
