package Startup;

import java.awt.EventQueue;

import javax.swing.JFrame;

import Werkzeuge.DeteiWerkzeug;
import Werkzeuge.Guthaben.GuthabenWerkzeug;
import Werkzeuge.ProfilManager.ProfilWerkzeug;

public class Startup
{
    private static ProfilWerkzeug _profilWerkzeug;
    private static GuthabenWerkzeug _guthabenWerkzeug;

    private JFrame frame;

    /**
     * Launch the application.
     */
    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                try
                {
                    Startup window = new Startup();
                    window.frame.setVisible(true);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Erstellt die Services und lädt die Daten.
     */
    public Startup()
    {
        erstelleServices();
        if (DeteiWerkzeug.bereitsInitialisiert(_profilWerkzeug))
        {
            DeteiWerkzeug.leseBewohnerEin();
            DeteiWerkzeug.leseEinstellungenEin();
            _guthabenWerkzeug.berechneGuthaben();
            _guthabenWerkzeug.erzeugeGuthabenServiceUI();
            DeteiWerkzeug.speichereInDatei();
        }
    }

    private void erstelleServices()
    {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        _profilWerkzeug = new ProfilWerkzeug();
        //        _profilManager.erzeugeProfilManagerUI(new Profil("1/515", "Dominick", "Test", 1245, "Email", "handy", 10, 2018, 12, 2099, false));
        _guthabenWerkzeug = new GuthabenWerkzeug(_profilWerkzeug);
    }

}

/*
 * Bereich für ToDo Liste:
 * TODO Datenbank implementierung
 * TODO Maybe Übertragung auf eine Webanwendung (Javascript)
 * TODO Verschlüsselung
 * 
 */
