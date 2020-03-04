package Startup;

import Service.FileService;
import Service.GuthabenService;
import Service.ProfilManagerService;

public class Startup
{
    private static ProfilManagerService _profilManager;
    private static GuthabenService _guthabenService;

    public static void main(String[] args)
    {
        erstelleServices();
        if (FileService.bereitsInitialisiert(_profilManager))
        {
            FileService.leseBewohnerEin();
            FileService.leseEinstellungenEin();
            _guthabenService.berechneGuthaben();
            _guthabenService.erzeugeGuthabenServiceUI();
            FileService.speichereInDatei();
        }
    }

    /**
     * Erstellt die Services und lädt die Daten.
     */
    private static void erstelleServices()
    {
        _profilManager = new ProfilManagerService();
        //        _profilManager.erzeugeProfilManagerUI(new Profil("1/515", "Dominick", "Test", 1245, "Email", "handy", 10, 2018, 12, 2099, false));
        _guthabenService = new GuthabenService(_profilManager);

    }

    /*
     * Bereich für ToDo Liste:
     * TODO Datenbank implementierung
     * TODO Maybe Übertragung auf eine Webanwendung (Javascript)
     * TODO Verschlüsselung
     * 
     */
}