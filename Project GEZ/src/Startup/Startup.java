package Startup;

import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.UIManager;

import Werkzeuge.DateiWerkzeug;
import Werkzeuge.Guthaben.GuthabenWerkzeug;
import Werkzeuge.ProfilManager.ProfilWerkzeug;
import Werkzeuge.Startseite.Startseite;

/**
 * Das StartUp des GEZ-Rechners.
 *  
 * @author Dominick Labatz
 * @version 02.04.2020
 */
public class Startup
{
    private static ProfilWerkzeug _profilWerkzeug;
    private static GuthabenWerkzeug _guthabenWerkzeug;
    private JFrame frame;

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
                    window.frame.addWindowListener(new WindowAdapter()
                    {
                        public void windowClosing(WindowEvent e)
                        {
                            DateiWerkzeug.speichereInDatei();
                            System.exit(0);
                        }
                    });
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Erstellt die notwendige Werkzeuge und lädt die Daten.
     */
    public Startup()
    {

        erstelleServices();
        new Startseite(frame, _profilWerkzeug, _guthabenWerkzeug);
    }

    private void erstelleServices()
    {
        frame = new JFrame();
        frame.setResizable(false);
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        _profilWerkzeug = new ProfilWerkzeug();
        _guthabenWerkzeug = new GuthabenWerkzeug(_profilWerkzeug);
    }
}

/*
 * Bereich für mögliche ToDo Liste:
 * TODO Datenbank implementierung
 * TODO Maybe Übertragung auf eine Webanwendung (Javascript)
 * TODO Verschlüsselung
 */
