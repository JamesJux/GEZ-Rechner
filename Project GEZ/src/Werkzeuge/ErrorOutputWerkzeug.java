package Werkzeuge;

import java.io.FileNotFoundException;
import java.io.PrintStream;

import Fachwerte.Errors;

public class ErrorOutputWerkzeug
{

    /*
     * Funktioniert nach folgendem Beispiel: 		
     * ErrorOutputService.ErrorOutput(Errors.inputError);
     */
    public static void ErrorOutputConsole(Errors error)
    {
        String output = null;

        switch (error)
        {
        case inputError:
            output = "InputError: Eingabe ist Fehlerhaft.";
            break;
        case uncompledMethode:
            output = "UncompledMethode: Die Methode wurde nur Teilweise oder noch garnicht implementiert.";
            break;
        case profileAddError:
            output = "ProfileSetAddError: Problem beim Hinzufügen des Profils";
            break;
        case profileRemoveError:
            output = "ProfileSetRemoveError: Problem beim Entfernen des Profils";
            break;
        case fileNotFoundError:
            output = "Die Bewohner konnte nicht eingelesen werden, da die Datei nicht gefunden wurde.";
            break;
        case fileNotReadError:
            output = "Die Bewohner konnte nicht eingelesen werden, da die Datei nicht gelesen werden konnte.";
            break;
        default:
            output = "ErrorOutputService Error: Fehler bei der Fehlererkennung. Fehler wurde noch nicht im System Hinzugefügt.";
        }
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
        ErrorOutputWerkzeug.ErrorOutputConsole(Errors.uncompledMethode);
    }
}
