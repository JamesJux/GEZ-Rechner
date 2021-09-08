package Werkzeuge.Guthaben;

import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JOptionPane;

import Fachwerte.Errors;
import Fachwerte.Geldbetrag;
import Materialien.Profil;
import Werkzeuge.ErrorOutputWerkzeug;
import Werkzeuge.ProfilManager.ProfilWerkzeug;

/**
 * Das GuthabenWerkzeug des GEZ-Rechners.
 *  
 * @author Dominick Labatz
 * @version 02.04.2020
 **/
public class GuthabenWerkzeug
{
    private static Integer _beginnBerechnungMonat;
    private static Integer _beginnBerechnungJahr;
    ProfilWerkzeug PW;
    GuthabenWerkzeugUI _ui;
    Profil profil;
    private int EinAusBetrag;

    public GuthabenWerkzeug(ProfilWerkzeug profilWerkzeug)
    {
        PW = profilWerkzeug;
        berechneGuthaben();
    }

    /**
     * Öffnet ein GuthabenWerkzeugUI Instanz mit dem übergebenem Profil.
     * Zum editieren oder angucken der hinterlegten Informationen.
     *  
     *  @param aktProfil das Profil das bearbeitet/angeguckt werden soll.
     **/
    public void erzeugeGuthabenWerkzeugUI(Profil aktProfil)
    {
        profil = aktProfil;
        _ui = new GuthabenWerkzeugUI(profil);
        EinAusBetrag = 0;
        if (profil.getMomentanesGuthaben().istBetragNegativ())
        {
            _ui.get_AuszahlenButton().setEnabled(false);
            _ui.getTextVorrausGuthabenLabel().setForeground(SystemColor.control);
        }
        if (profil.istBeitragszahler())
        {
            _ui.get_textGuthabenLabel().setText(profil.getVorname() + " " + profil.getNachname() + " ist der Bezahler.");
            _ui.getNurTextGuthabenLabel().setForeground(SystemColor.control);
            _ui.getTextVorrausGuthabenLabel().setForeground(SystemColor.control);
            _ui.get_AuszahlenButton().setEnabled(false);
            _ui.get_EinzahlenButton().setEnabled(false);
        }
        registriereUIAktionen();
    }

    /**
     * Registriert den Berechnungs Monat im BezahlWerkzeug.
     * 
     * @apiNote Notwendig für die Funktionalität.
     * @param seitMonat Der Monat des Beginn der Berechnung der Guthaben.
     **/
    public static void registriereBezahlMonat(String seitMonat)
    {
        _beginnBerechnungMonat = Integer.valueOf(seitMonat) - 1;
    }

    /**
     * Registriert das Berechnungs Jahr im BezahlWerkzeug.
     * 
     * @apiNote Notwendig für die Funktionalität.
     * @param seitJahr Das Jahr des Beginn der Berechnung der Guthaben.
     **/
    public static void registriereBezahlJahr(String seitJahr)
    {
        _beginnBerechnungJahr = Integer.valueOf(seitJahr);
    }

    /**
     * Berechnet die aktuellen Guthaben aller Profile und schreibt diese "momentanen Guthaben" in die Profile.
     **/
    public void berechneGuthaben()
    {
        for (Profil profil : PW.getProfile())
        {
            if (!profil.istBeitragszahler())
            {
                int betragCent = 0;

                int monat = _beginnBerechnungMonat - 1;
                int jahr = _beginnBerechnungJahr;

                GregorianCalendar zeitpunkt;

                for (int i = 0; i <= getAnzahlMonate(); i++)
                {
                    monat++;
                    zeitpunkt = new GregorianCalendar(jahr, monat, 15);
                    if (PW.wohntImHaus(profil, zeitpunkt))
                    {
                        int diesenMonat = Math.round(getBetragshoehe(zeitpunkt)
                                / PW.getAnzahlZahlendeBewohner(new GregorianCalendar(jahr, monat, 15)));
                        betragCent -= diesenMonat;
                    }
                    if (monat == 12)
                    {
                        monat = 0;
                        jahr++;
                    }
                }
                int DiffBetragCent = betragCent + profil.getGuthaben().getBetragInCent();
                Geldbetrag momentan = new Geldbetrag(Math.abs(DiffBetragCent), (DiffBetragCent < 0));
                profil.setMomentanesGuthaben(momentan);
                int RestMonate;
                if (momentan.istBetragNegativ())
                {
                    RestMonate = 0;
                }
                else
                {
                    RestMonate = momentan.getBetragInCent()
                            / (Math.round(getBetragshoehe(new GregorianCalendar())
                                    / PW.getAnzahlZahlendeBewohner(new GregorianCalendar())));
                }
                profil.setVorraussichtlicheDauer(RestMonate);
            }
        }
    }

    /**
     * Gibt den aktuell gültigen Beitragsbetrag aus.
     * 
     * @return Gibt die Höhe des Aktuellen Beitrages in Cent aus.
     **/
    public static float getBetragshoehe()
    {
        return getBetragshoehe(new GregorianCalendar());
    }

    /**
     * Gibt den für den Monat gültigen Betrag aus.
     * 
     * @param zeitpunkt Der Zeitpunkt der Abfrage.
     * @return Gibt die Höhe des zu dem Zeitpunkt gültigem Beitrages in Cent aus.
     **/
    private static float getBetragshoehe(GregorianCalendar zeitpunkt)
    {
        GregorianCalendar stichtag = new GregorianCalendar(2021, 05, 15);
        if (zeitpunkt.before(stichtag))
        {
            return 1750;
        }
        else
        {
            return 1836;
        }
    }

    //    public void setzeMonatHinauf()
    //    {
    //        System.out.println("Vorher: " + _beginnBerechnungMonat + "." + _beginnBerechnungJahr);
    //        for (Profil profil : PW.getProfile())
    //        {
    //            System.out.println(profil.getName() + " - " + profil.getGuthaben().getBetragInCent() + "- Alt");
    //        }
    //        System.out.println("---------------------------------------------");
    //        int monat = _beginnBerechnungMonat - 1;
    //        int jahr = _beginnBerechnungJahr;
    //
    //        int diesenMonat = -Math.round((float) getBetragshoehe(new GregorianCalendar(jahr, monat, 15))
    //                / PW.getAnzahlZahlendeBewohner(new GregorianCalendar(jahr, monat, 15)));
    //        System.out.println("Diesen Monat: " + diesenMonat);
    //        for (Profil profil : PW.getProfile())
    //        {
    //            if (!profil.istBeitragszahler())
    //            {
    //                if (PW.wohntImHaus(profil, new GregorianCalendar(jahr, monat, 15)))
    //                {
    //
    //                    PW.speichereGuthaben(profil, diesenMonat);
    //                    System.out.println(profil.getName() + "- " + diesenMonat);
    //                }
    //
    //            }
    //        }
    //
    //        _beginnBerechnungMonat++;
    //        if (_beginnBerechnungMonat == 13)
    //        {
    //            _beginnBerechnungMonat = 0;
    //            _beginnBerechnungJahr++;
    //        }
    //        System.out.println("---------------------------------------------");
    //        System.out.println("Nachher: " + _beginnBerechnungMonat + "." + _beginnBerechnungJahr);
    //        for (Profil profil : PW.getProfile())
    //        {
    //            System.out.println(profil.getName() + " - "
    //                    + profil.getGuthaben().getBetragInCent() + "- Neu");
    //        }
    //    }

    /**
     * Berechnet die Anzahl an Monaten seit Beginn der Berechnungen (in den Einstellungen angegeben) vergangen sind.
     * 
     * @return Die Anzahl an Monate.
     **/
    private static int getAnzahlMonate()
    {
        GregorianCalendar beginn = new GregorianCalendar(_beginnBerechnungJahr, _beginnBerechnungMonat, 01);
        GregorianCalendar heute = new GregorianCalendar();
        int y = heute.get(Calendar.YEAR) - beginn.get(Calendar.YEAR);
        int m = heute.get(Calendar.MONTH) - beginn.get(Calendar.MONTH);
        int Monate = y * 12 + m;
        return Monate;
    }

    /**
     * Fragt den Benutzer welcher Betrag ausgezahlt werden soll, und übergibt diesen Wert an das ProfilWerkzeug.
     * 
     * Bei falscher Eingabe wird noch mal gefragt, ausser man beendet den Dialog mit 'WindowClosing'.
     **/
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

    /**
     * Fragt den Benutzer welcher Betrag eingezahlt werden soll, und übergibt diesen Wert an das ProfilWerkzeug.
     * 
     * Bei falscher Eingabe wird noch mal gefragt, ausser man beendet den Dialog mit 'WindowClosing'.
     **/
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
                EinAusBetrag += Integer.valueOf(eingabe);
                PW.MomentanesGuthabenEinzahlen(profil, Integer.valueOf(EinAusBetrag));
            }
        }
    }

    /**
     * Gibt den Namen des Monates des übergebenen Monates als String. 
     * 
     * @return Den Monat als String.
     **/
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
        default:
            ErrorOutputWerkzeug.ErrorOutput(Errors.MonatAusgabeError);
        }
        return monat;
    }

    /**
     * Prüft ob der UI- Aktionen ausgelöst werden und reagiert entsprechend.
     * 
     **/
    private void registriereUIAktionen()
    {
        _ui.get_EinzahlenButton().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Einzahlen();
                _ui.get_textGuthabenLabel().setText(profil.getMomentanesGuthaben().toFormattedString());
                _ui.get_AuszahlenButton().setEnabled(false);
                _ui.get_EinzahlenButton().setEnabled(false);
            }
        });

        _ui.get_AuszahlenButton().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Auszahlen();
                _ui.get_textGuthabenLabel().setText(profil.getMomentanesGuthaben().toFormattedString());
                _ui.get_AuszahlenButton().setEnabled(false);
                _ui.get_EinzahlenButton().setEnabled(false);
            }
        });

        _ui.get_OKButton().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                PW.speichereGuthaben(profil, EinAusBetrag);
                GuthabenWerkzeugUI.schliessen();
            }
        });
    }
}