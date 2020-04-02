package Werkzeuge;

import java.io.FileNotFoundException;
import java.io.PrintStream;

import Fachwerte.Errors;

public class ErrorOutputWerkzeug
{
    /**
     *  Das Werkzeug zum Verarbeiten von Fehler und zum protokollieren dieser.
     *  
     * @author Dominick Labatz
     * @author Marvin Taube
     * @version 02.04.2020
     */
    public static void ErrorOutput(Errors error)
    {
        String output = null;

        switch (error)
        {
        case BewohnerEinlesenError:
            output = "Einlesen der Bewohner Fehlerhaft bitte überfrüfen sie die Korrektheit der Werte in der Bewohner.txt.";
            break;
        case EingabeError:
            output = "InputError: Eingabe ist Fehlerhaft.";
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
            output = "ErrorOutputService Error: Fehler bei der Fehlererkennung. Fehler wurde noch nicht im System hinzugefügt.";
        }
        System.out.println(output);
        ErrorOutputTextfile(output);
    }

    public static void ErrorOutputTextfile(String ErrorString)
    {
        PrintStream printer;
        try
        {
            printer = new PrintStream("./Textdatein/Error_Log.txt");
            printer.println(ErrorString);
            printer.close();
        }
        catch (FileNotFoundException e)
        {
        }
    }

    /*
     * TODO eine Methode die zur Überprüfung dienen kann um zu schauen ob bis zu einem gewissen Punkt Errors entstanden sind
     */
    public static void ErrorCheck()
    {
        ErrorOutputWerkzeug.ErrorOutput(Errors.UnfertigeMethode);
    }
}
