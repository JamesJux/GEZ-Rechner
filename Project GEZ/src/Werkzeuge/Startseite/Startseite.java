package Werkzeuge.Startseite;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import Materialien.Profil;
import Werkzeuge.DeteiWerkzeug;
import Werkzeuge.Guthaben.GuthabenWerkzeug;
import Werkzeuge.ProfilManager.ProfilWerkzeug;

public class Startseite
{

    private static ProfilWerkzeug PW;
    private static StartseiteUI _ui;
    private static GuthabenWerkzeug GW;
    public static JFrame _frame;
    private static boolean aktiv;

    public Startseite(JFrame frame, ProfilWerkzeug profilWerkzeug, GuthabenWerkzeug guthabenWerkzeug)
    {
        _frame = frame;
        PW = profilWerkzeug;
        GW = guthabenWerkzeug;
        start();
        registriereUIAktionen();
    }

    private static Profil getSelectedProfil()
    {
        return PW.getProfil(_ui.get_bewohnerChoice().getSelectedItem());
    }

    private static boolean istNeuerBewohnerGewählt()
    {
        return _ui.get_bewohnerChoice().getSelectedItem().equals("Neuer Bewohner");
    }

    public static void start()
    {
        if (DeteiWerkzeug.bereitsInitialisiert(PW))
        {
            DeteiWerkzeug.leseBewohnerEin();
            DeteiWerkzeug.leseEinstellungenEin();
            _ui = new StartseiteUI();
            aktiv = true;
            setzeBewohnerChoice();
            GW.berechneGuthaben();

        }
    }

    private static void setzeBewohnerChoice()
    {
        _ui.get_bewohnerChoice().removeAll();
        for (Profil profil : PW.getProfile())
        {
            _ui.get_bewohnerChoice().add(profil.getName().toFormattedString());
        }
        _ui.get_bewohnerChoice().add("Neuer Bewohner");
    }

    public static void setzeAktiv()
    {
        aktiv = true;
        setzeBewohnerChoice();
    }

    private static void registriereUIAktionen()
    {
        _frame.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                if (aktiv)
                {
                    DeteiWerkzeug.speichereInDatei();
                    System.exit(1);
                }
            }
        });

        _ui.get_bewohnerChoice().addItemListener(new ItemListener()
        {
            public void itemStateChanged(ItemEvent e)
            {
                if (istNeuerBewohnerGewählt())
                {
                    _ui.get_bewohnerBearbeitenButton().setText("Erstellen");
                    _ui.get_guthabenButton().setEnabled(false);
                }
                else
                {
                    _ui.get_bewohnerBearbeitenButton().setText("Bewohner bearbeiten");
                    _ui.get_guthabenButton().setEnabled(true);
                }

            }
        });

        _ui.get_bewohnerBearbeitenButton().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (aktiv)
                {
                    aktiv = false;
                    if (istNeuerBewohnerGewählt())
                    {
                        PW.erzeugeProfilWerkzeugUI();
                    }
                    else
                    {
                        PW.erzeugeProfilWerkzeugUI(getSelectedProfil());
                    }
                }
            }
        });

        _ui.get_uebersichtButton().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (aktiv)
                {
//                    aktiv = false;
                    // TODO: Übersichtsfenster gestalten. 
                    System.out.println("Übersicht button");
                }
            }
        });

        _ui.get_guthabenButton().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (aktiv)
                {
                    aktiv = false;
                    GW.erzeugeGuthabenWerkzeugUI(getSelectedProfil());
                    setzeBewohnerChoice();
                    GW.berechneGuthaben();
                    _ui.setEnabled(true);
                }
            }
        });
    }
}