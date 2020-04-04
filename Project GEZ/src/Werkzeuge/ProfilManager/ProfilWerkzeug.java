package Werkzeuge.ProfilManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.GregorianCalendar;
import java.util.HashSet;

import javax.swing.JOptionPane;

import Fachwerte.Errors;
import Fachwerte.Geldbetrag;
import Materialien.Profil;
import Werkzeuge.ErrorOutputWerkzeug;

/**
 * Das ProfilWerkzeug des GEZ-Rechners.
 *  
 * @author Dominick Labatz
 * @version 02.04.2020
 */
public class ProfilWerkzeug
{
    private static HashSet<Profil> profile;
    ProfilWerkzeugUI _ui;
    private boolean benutzerBearbeiten;

    public ProfilWerkzeug()
    {
        profile = new HashSet<Profil>();
    }

    public void erzeugeProfilWerkzeugUI()
    {
        _ui = new ProfilWerkzeugUI();
        benutzerBearbeiten = false;
        registriereUIAktionen();
    }

    public void erzeugeProfilWerkzeugUI(Profil profil)
    {
        _ui = new ProfilWerkzeugUI(profil);
        _ui.get_textFieldNachname().setEditable(false);
        _ui.get_textFieldVorname().setEditable(false);
        _ui.get_textFieldZimmer().setEditable(false);
        benutzerBearbeiten = true;
        registriereUIAktionen();
    }

    public HashSet<Profil> getProfile()
    {
        return profile;
    }

    public void erstelleProfil(String zimmer, String vorname, String nachname, int guthaben, int EinMonat, int EinJahr, String email,
            String handynummer, int AusMonat, int AusJahr)
    {
        Profil neuesProfil = new Profil(zimmer, vorname, nachname, guthaben, email, handynummer, EinMonat, EinJahr, AusMonat, AusJahr);

        profile.add(neuesProfil);
    }

    public void neuenBenutzerSpeichern()
    {
        String zimmer = _ui.get_textFieldZimmer().getText();
        String vorname = _ui.get_textFieldVorname().getText();
        String nachname = _ui.get_textFieldNachname().getText();
        if (vorname.equals("") || nachname.equals(""))
        {
            ErrorOutputWerkzeug.ErrorOutput(Errors.EingabeError);
        }
        int Guthaben = 0;
        int EinMonat = Integer.parseInt(_ui.get_choiceMonat().getSelectedItem());
        int EinJahr = Integer.parseInt(_ui.get_choiceJahr().getSelectedItem());
        String Email = _ui.get_textFieldEmail().getText();
        if (Email.equals(""))
        {
            Email = " ";
        }
        String Handynummer = _ui.get_textFieldTelefon().getText();
        if (Handynummer.equals(""))
        {
            Handynummer = " ";
        }
        int AusMonat = Integer.parseInt(_ui.get_choiceMonatAus().getSelectedItem());
        int AusJahr = Integer.parseInt(_ui.get_choiceJahrAus().getSelectedItem());
        erstelleProfil(zimmer, vorname, nachname, Guthaben, EinMonat, EinJahr, Email, Handynummer, AusMonat, AusJahr);
    }

    public int getAnzahlZahlendeBewohner(GregorianCalendar datum)
    {
        int temp = 0;
        for (Profil profil : profile)
        {
            if (wohntImHaus(profil, datum))
            {
                temp++;
            }
        }
        return temp;

    }

    public boolean wohntImHaus(Profil profil, GregorianCalendar datum)
    {
        if ((profil.getEinzugsdatum().getTimeInMillis() <= datum.getTimeInMillis())
                && (datum.getTimeInMillis() <= profil.getAuszugsdatum().getTimeInMillis()))
        {
            return true;
        }
        return false;
    }

    public Profil getProfil(String gesProfil)
    {
        for (Profil profil : profile)
        {
            if (gesProfil.equals(profil.getVorname() + " " + profil.getNachname()))
            {
                return profil;
            }
        }
        return null;
    }

    public Profil getBezahler()
    {
        for (Profil profil : profile)
        {
            if (profil.istBezahler())
            {
                return profil;
            }
        }
        return null;
    }

    public void registriereBezahler(String Bezahler)
    {
        getProfil(Bezahler).setBezahler();
    }

    public void loescheProfil(String loeschendesProfil)
    {
        profile.remove(getProfil(loeschendesProfil));
    }

    public void MomentanesGuthabenAuszahlen(Profil profil, int betrag)
    {
        profil.setMomentanesGuthaben(new Geldbetrag(profil.getMomentanesGuthaben().getBetragInCent() - betrag, false));
    }

    public void MomentanesGuthabenEinzahlen(Profil profil, int betrag)
    {
        int momentanCent = profil.getMomentanesGuthaben().getBetragInCent();
        int neu;
        boolean neuNegativ = false;
        if (profil.getMomentanesGuthaben().istBetragNegativ())
        {
            momentanCent = -momentanCent;
        }
        neu = momentanCent + betrag;
        if (neu <= 0)
        {
            neuNegativ = true;
        }
        profil.setMomentanesGuthaben(new Geldbetrag(neu, neuNegativ));
    }

    public void speichereGuthaben(Profil profil, int betrag)
    {
        profil.setGuthaben(new Geldbetrag(profil.getGuthaben().getBetragInCent() + betrag, false));
    }

    public boolean istAuszahlenMoeglich(Profil profil, int betrag)
    {
        return profil.istAuszahlenMoeglich(betrag);
    }

    private void registriereUIAktionen()
    {
        _ui.getAbbrechenButton().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                ProfilWerkzeugUI.schliessen();
            }
        });

        _ui.get_InfoButton().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                JOptionPane.showMessageDialog(null, "Falls der Bewohner noch nicht ausgezogen ist, wählen sie bitte als Auszugsdatum 12, 2099", "Auszugs Info ", JOptionPane.PLAIN_MESSAGE);
            }
        });

        _ui.get_speichernButton().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (benutzerBearbeiten)
                {
                    loescheProfil(_ui.get_textFieldVorname().getText() + " " + _ui.get_textFieldNachname().getText());
                    neuenBenutzerSpeichern();
                }
                else
                {
                    neuenBenutzerSpeichern();
                }
                ProfilWerkzeugUI.schliessen();
            }
        });

        _ui.get_bewohnerLöschenButton().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (JOptionPane.showConfirmDialog(_ui, "Sind Sie sich sicher dass sie den Bewohner löschen möchten?", "Bewohner löschen", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
                {
                    loescheProfil(_ui.get_textFieldVorname().getText() + " " + _ui.get_textFieldNachname().getText());
                    ProfilWerkzeugUI.schliessen();
                }
            }
        });
    }
}
