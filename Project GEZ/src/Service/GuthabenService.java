package Service;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.GregorianCalendar;

import javax.swing.JOptionPane;

import Fachwerte.Datum;
import Fachwerte.Geldbetrag;
import Material.Profil;
import Startup.GuthabenServiceUI;

public class GuthabenService
{
    ProfilManagerService PMS;
    GuthabenServiceUI _ui;
    Profil profil;

    public GuthabenService(ProfilManagerService ProfilManager)
    {
        PMS = ProfilManager;
        berechneGuthaben();
    }

    public void erzeugeGuthabenServiceUI()
    {
        for (Profil profil2 : PMS.getProfile())
        {
            if (profil2.getName().toFormattedString().equals("Dominick Labatz"))
            {
                profil = profil2;
            }
        }
        _ui = new GuthabenServiceUI(profil);
        registriereUIAktionen();
    }

    public void berechneGuthaben()
    {
        new GregorianCalendar();
        for (Profil profil : PMS.getProfile())
        {
            int betragCent = 0;

            int jahr = 2018;
            int monat = 10;

            if (!(profil.getBezahler()))
            {
                for (int i = 0; i < (getAnzahlMonate()); i++)
                {
                    monat++;

                    if (PMS.wohntImHaus(profil, new Datum(15, monat, jahr)))
                    {
                        int diesenMonat = runden(17500 / PMS.getAnzahlZahlendeBewohner(new Datum(15, monat, jahr)));
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
    }

    private int getAnzahlMonate()
    {
        GregorianCalendar beginn = new GregorianCalendar(2018, 10, 01);
        GregorianCalendar heute = new GregorianCalendar();
        long difference = heute.getTimeInMillis() - beginn.getTimeInMillis();
        int Monate = (int) (difference / (1000 * 60 * 60 * 24 * 30.4167));
        return Monate + 1;
    }

    private void registriereUIAktionen()
    {
        _ui.get_EinzahlenButton().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Einzahlen();
                berechneGuthaben();

                _ui.get_textGuthabenLabel().setText(profil.getMomentanesGuthaben().toFormattedString());
            }
        });

        _ui.get_AuszahlenButton().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Auszahlen();
                berechneGuthaben();
                _ui.get_textGuthabenLabel().setText(profil.getMomentanesGuthaben().toFormattedString());
            }
        });

        _ui.get_OKButton().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("ok");
            }
        });
    }

    private void Auszahlen()
    {
        String eingabe = JOptionPane.showInputDialog(_ui, "Geben Sie den Betrag in Cent ein den Sie auszahlen möchten", "Auszahlung", JOptionPane.PLAIN_MESSAGE);
        if (!eingabe.matches("[0-9]{1,}"))
        {
            JOptionPane.showMessageDialog(null, "Bitte geben Sie einen Betrag ein", "Fehlerhafte Eingabe", JOptionPane.WARNING_MESSAGE);
            Auszahlen();
        }
        else
        {
            int betrag = Integer.valueOf(eingabe);
            if (profil.istAuszahlenMoeglich(betrag))
            {
                profil.setAuszahlGuthaben(betrag);
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Bitte geben Sie einen Betrag ein, der dem Restguthaben oder weniger entspricht", "Fehlerhafte Eingabe", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private void Einzahlen()
    {
        String eingabe = JOptionPane.showInputDialog(_ui, "Geben Sie den Betrag in Cent ein den Sie einzahlen möchten", "Einzahlung", JOptionPane.PLAIN_MESSAGE);
        if (!eingabe.matches("[0-9]{1,}"))
        {
            JOptionPane.showMessageDialog(null, "Bitte geben Sie einen Betrag ein", "Fehlerhafte Eingabe", JOptionPane.WARNING_MESSAGE);
            Einzahlen();
        }
        else
        {
            int betrag = Integer.valueOf(eingabe);
            profil.setEinzahlGuthaben(betrag);
        }
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