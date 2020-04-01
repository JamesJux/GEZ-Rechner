package Werkzeuge.Guthaben;

import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.GregorianCalendar;

import javax.swing.JOptionPane;

import Fachwerte.Geldbetrag;
import Materialien.Profil;
import Werkzeuge.ProfilManager.ProfilWerkzeug;

public class GuthabenWerkzeug
{
    ProfilWerkzeug PW;
    GuthabenWerkzeugUI _ui;
    Profil profil;
    private int EinAusBetrag;

    public GuthabenWerkzeug(ProfilWerkzeug profilWerkzeug)
    {
        PW = profilWerkzeug;
        berechneGuthaben();
    }

    public void erzeugeGuthabenWerkzeugUI(Profil aktProfil)
    {
        profil = aktProfil;
        _ui = new GuthabenWerkzeugUI(profil);
        EinAusBetrag = 0;
        _ui.get_AuszahlenButton().setEnabled(!profil.getMomentanesGuthaben().istBetragNegativ());
        if (aktProfil.istBezahler())
        {
            _ui.get_textGuthabenLabel().setText(profil.getName().toFormattedString() + " ist der Bezahler.");
            _ui.getNurTextGuthabenLabel().setForeground(SystemColor.control);
            _ui.get_AuszahlenButton().setEnabled(false);
            _ui.get_EinzahlenButton().setEnabled(false);
        }
        registriereUIAktionen();
    }

    public void berechneGuthaben()
    {
        new GregorianCalendar();
        for (Profil profil : PW.getProfile())
        {
            int betragCent = 0;

            int jahr = 2018;
            int monat = 10;

            for (int i = 0; i < (getAnzahlMonate()); i++)
            {
                monat++;

                if (PW.wohntImHaus(profil, new GregorianCalendar(jahr, monat, 15)))
                {
                    int diesenMonat = runden(17500 / PW.getAnzahlZahlendeBewohner(new GregorianCalendar(jahr, monat, 15)));
                    betragCent -= diesenMonat;
                }
                if (monat == 12)
                {
                    monat = 0;
                    jahr++;
                }
            }
            int DiffBetragCent = betragCent + profil.getGuthaben().getBetragInCent();
            profil.setMomentanesGuthaben(new Geldbetrag(Math.abs(DiffBetragCent), (DiffBetragCent <= 0)));
        }
    }

    private int getAnzahlMonate()
    {
        GregorianCalendar beginn = new GregorianCalendar(2018, 10, 01);
        GregorianCalendar heute = new GregorianCalendar();
        long difference = heute.getTimeInMillis() - beginn.getTimeInMillis();
        int Monate = (int) (difference / (1000 * 60 * 60 * 24 * 30.4167));
        return Monate + 1;
    }

    private void Auszahlen()
    {
        String eingabe = JOptionPane.showInputDialog(_ui, "Geben Sie den Betrag in Cent ein den Sie auszahlen möchten", "Auszahlung", JOptionPane.PLAIN_MESSAGE);
        if (eingabe != null)
        {
            if (!eingabe.matches("[0-9]{1,}"))
            {
                JOptionPane.showMessageDialog(null, "Bitte geben Sie einen Betrag ein", "Fehlerhafte Eingabe", JOptionPane.WARNING_MESSAGE);
                Auszahlen();
            }
            else
            {
                if (PW.istAuszahlenMoeglich(profil, Integer.valueOf(eingabe)))
                {
                    PW.MomentanesGuthabenAuszahlen(profil, Integer.valueOf(eingabe));
                    EinAusBetrag -= Integer.valueOf(eingabe);
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Bitte geben Sie einen Betrag ein, der dem Restguthaben oder weniger entspricht", "Fehlerhafte Eingabe", JOptionPane.WARNING_MESSAGE);
                    Auszahlen();
                }
            }
        }
    }

    private void Einzahlen()
    {
        String eingabe = JOptionPane.showInputDialog(_ui, "Geben Sie den Betrag in Cent ein den Sie einzahlen möchten", "Einzahlung", JOptionPane.PLAIN_MESSAGE);
        if (eingabe != null)
        {
            if (!eingabe.matches("[0-9]{1,}"))
            {
                JOptionPane.showMessageDialog(null, "Bitte geben Sie einen Betrag ein", "Fehlerhafte Eingabe", JOptionPane.WARNING_MESSAGE);
                Einzahlen();
            }
            else
            {
                PW.MomentanesGuthabenEinzahlen(profil, Integer.valueOf(eingabe));
                EinAusBetrag += Integer.valueOf(eingabe);
            }
        }
    }

    private void registriereUIAktionen()
    {
        _ui.get_EinzahlenButton().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Einzahlen();
                _ui.get_textGuthabenLabel().setText(profil.getMomentanesGuthaben().toFormattedString());
            }
        });

        _ui.get_AuszahlenButton().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Auszahlen();
                _ui.get_textGuthabenLabel().setText(profil.getMomentanesGuthaben().toFormattedString());
            }
        });

        _ui.get_OKButton().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                PW.speichereGuthaben(profil, EinAusBetrag);
                berechneGuthaben();
                GuthabenWerkzeugUI.schliessen();
            }
        });
    }

    static int runden(int zahl)
    {

        if (zahl % 10 < 5)
        {
            return zahl / 10;
        }
        else
        {
            return (zahl + 5) / 10;
        }
    }

    static String findeMonat(int rechnendeMonate)
    {
        String monat = "";
        switch (rechnendeMonate)
        {
        case 11:
            monat = "Dezember";
            break;
        case 10:
            monat = "November";
            break;
        case 9:
            monat = "Oktober";
            break;
        case 8:
            monat = "September";
            break;
        case 7:
            monat = "August";
            break;
        case 6:
            monat = "Juli";
            break;
        case 5:
            monat = "Juni";
            break;
        case 4:
            monat = "Mai";
            break;
        case 3:
            monat = "April";
            break;
        case 2:
            monat = "März";
            break;
        case 1:
            monat = "Februar";
            break;
        case 0:
            monat = "Januar";
            break;
        }
        return monat;
    }

}