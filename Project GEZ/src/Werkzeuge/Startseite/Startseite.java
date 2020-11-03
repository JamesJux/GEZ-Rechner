package Werkzeuge.Startseite;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import java.util.GregorianCalendar;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Fachwerte.Errors;
import Fachwerte.Geldbetrag;
import Materialien.Profil;
import Werkzeuge.DateiWerkzeug;
import Werkzeuge.ErrorOutputWerkzeug;
import Werkzeuge.Guthaben.GuthabenWerkzeug;
import Werkzeuge.ProfilManager.ProfilWerkzeug;

/**
 * Die Startseite des GEZ-Rechners.
 *  
 * @author Dominick Labatz
 * @version 02.04.2020
 */
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
        DateiWerkzeug.bereitsInitialisiert(PW);
        DateiWerkzeug.leseBewohnerEin();
        DateiWerkzeug.leseEinstellungenEin();
        if (DateiWerkzeug.sindEinstellungenVollständig())
        {
            _ui = new StartseiteUI();
            aktiv = true;
            setzeBewohnerChoice();
            GW.berechneGuthaben();
            registriereUIAktionen();
        }
        else
        {
            ErrorOutputWerkzeug.ErrorOutput(Errors.EinstellungenEinlesenError);
            System.exit(1);
        }
    }

    private static void setzeBewohnerChoice()
    {
        _ui.get_bewohnerChoice().removeAll();
        for (Profil profil : PW.getProfile())
        {
            _ui.get_bewohnerChoice().add(profil.getVorname() + " " + profil.getNachname());
        }
        _ui.get_bewohnerChoice().add("Neuer Bewohner");
    }

    public static void setzeAktiv()
    {
        aktiv = true;
        GW.berechneGuthaben();
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
                    DateiWerkzeug.speichereInDatei();
                    System.exit(0);
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
                    GW.berechneGuthaben();
                    _ui.get_bewohnerBearbeitenButton().setText("Bewohner bearbeiten");
                    _ui.get_guthabenButton().setEnabled(true);
                }
            }
        });

        _ui.get_uebersichtButton().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (aktiv)
                {
                    Profil Beitragszahler = PW.getBeitragszahler();
                    int ZahlendeBewohner = PW.getAnzahlZahlendeBewohner(new GregorianCalendar());
                    String Text1 = "Infos zum Beitragsbezahlenden:\n" + Beitragszahler.getName() + " (" + Beitragszahler.getGeburtstag()
                            + ")\nBeitragsnummer: " + Beitragszahler.getBeitragsnummer() + "\n\n";

                    DecimalFormat df = new DecimalFormat("###.##");
                    String Text2 = "Aktuell zahlende Bewohner: " + ZahlendeBewohner + "\nAktuell pro Monat: "
                            + df.format(17.50 / ZahlendeBewohner) + "\n\n";

                    String TextBeiträge = "Noch offene Beträge: \n";
                    for (Profil profil : PW.getProfile())
                    {
                        Geldbetrag momentanesGuthaben = profil.getMomentanesGuthaben();
                        if (!momentanesGuthaben.istBetragNull() && momentanesGuthaben.istBetragNegativ())
                        {
                            TextBeiträge += profil.getName() + ": " + momentanesGuthaben.toFormattedString() + "\n";
                        }
                    }
                    JOptionPane.showMessageDialog(null, Text1 + Text2 + TextBeiträge, "Übersicht", JOptionPane.PLAIN_MESSAGE);
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