package Startup;

import Service.FileService;
import Service.ProfilManagerService;

public class Startup
{
    private static ProfilManagerService _profilManager;

    public static void main(String[] args)
    {
        erstelleServices();
        if (FileService.bereitsInitialisiert())
        {
            FileService.leseEinstellungenEin();
            FileService.leseBewohnerEin(_profilManager);
            _profilManager.NormalBetrieb();
            FileService.schreibeInDatei(_profilManager);
        }
    }

    /**
     * Erstellt die Services und lädt die Daten.
     */
    private static void erstelleServices()
    {
        _profilManager = new ProfilManagerService();

    }

    /*
     * Bereich für ToDo Liste:
     * TODO Datenbank implementierung
     * TODO Maybe Übertragung auf eine Webanwendung (Javascript)
     * TODO Verschlüsselung
     * 
     */
}