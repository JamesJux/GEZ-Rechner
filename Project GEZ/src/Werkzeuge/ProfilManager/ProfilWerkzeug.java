package Werkzeuge.ProfilManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.GregorianCalendar;
import java.util.HashSet;

import javax.swing.JOptionPane;

import Fachwerte.Errors;
import Fachwerte.Geldbetrag;
import Materialien.Profil;
import Werkzeuge.DateiWerkzeug;
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
        benutzerBearbeiten = true;
        registriereUIAktionen();
    }

    public HashSet<Profil> getProfile()
    {
        return profile;
    }

    public void erstelleProfil(String vorname, String nachname, int guthaben, int EinMonat, int EinJahr, String email,
            String handynummer, int AusMonat, int AusJahr)
    {
        Profil neuesProfil = new Profil(vorname, nachname, guthaben, email, handynummer, EinMonat, EinJahr, AusMonat, AusJahr);

        profile.add(neuesProfil);
    }

    /**
     * Speichert einen Bewohner anhand der in dem UI eingegebenen Informationen.
     * 
     * @apiNote Prüft ob Fehler in der Eingabe sind.
     * TODO: Fehler in den Eingaben abfangen.
     * 
     **/
    public void neuenBenutzerSpeichern()
    {
        String vorname = _ui.get_textFieldVorname().getText();
        String nachname = _ui.get_textFieldNachname().getText();
        if (vorname.equals("") || nachname.equals(""))
        {
            ErrorOutputWerkzeug.ErrorOutput(Errors.NamenEingabeError);
        }
        int Guthaben = 0;
        String einzug = _ui.getTextField(_ui.get_spinnerEinzug()).getText();
        int EinMonat = Integer.parseInt(einzug.substring(0, 2));
        int EinJahr = Integer.parseInt(einzug.substring(3, 7));
        ;
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
        int AusMonat;
        int AusJahr;
        if (_ui.get_AuszugCheckbox().isSelected())
        {
            AusMonat = 12;
            AusJahr = 2099;
        }
        else
        {
            String auszug = _ui.getTextField(_ui.get_spinnerAuszug()).getText();
            AusMonat = Integer.parseInt(auszug.substring(0, 2));
            AusJahr = Integer.parseInt(auszug.substring(3, 7));
        }

        System.out.println("Einzug: " + EinMonat + "/" + EinJahr + "\nAuszug: " + AusMonat + "/" + AusJahr);

        erstelleProfil(vorname, nachname, Guthaben, EinMonat, EinJahr, Email, Handynummer, AusMonat, AusJahr);
    }

    /**
     * Prüft wie viele Bewohner/Profil zu einem gewissen Zeitpunkt im Haus gewohnt hat.
     * 
     * @param datum Das Datum an dem geprüft werden soll.
     * @return die Anzahl an zahlenden Bewohner zu dem Zeitpunkt.
     **/
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

    /**
     * Prüft ob ein Bewohner/Profil zu einem gewissen Zeitpunkt im Haus gewohnt hat.
     * 
     * @param profil Das Profil bzw. Der Bewohner der überprüft werden soll.
     * @param datum Das Datum an dem geprüft werden soll.
     * @return true wenn der Profil an dem Zeitpunkt im haus gewohnt hat.
     **/
    public boolean wohntImHaus(Profil profil, GregorianCalendar datum)
    {
        if ((profil.getEinzugsdatum().getTimeInMillis() <= datum.getTimeInMillis())
                && (datum.getTimeInMillis() <= profil.getAuszugsdatum().getTimeInMillis()))
        {
            return true;
        }
        return false;
    }

    /**
     * Gibt zu einem String das Profil.
     * 
     * @return Das gesuchte Profil, ruturnt 'null' wenn kein passendes Profil gefunden werden kann.
     **/
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

    /**
     * Gibt den Bezahler aus.
     * 
     * @return Das Profil des Bezahlers, ruturnt 'null' wenn das Profil nicht gefunden werden kann.
     **/
    public Profil getBeitragszahler()
    {
        for (Profil profil : profile)
        {
            if (profil.istBeitragszahler())
            {
                return profil;
            }
        }
        return null;
    }

    /**
     * Registriert im Bezahlprofil seinen Status.
     * 
     * @apiNote Notwendig für die Funktionalität
     * @param Bezahler Namens-String des Beitragszahlers.
     * @param Beitragsnummer Beitragsnummer-String des Beitragszahlers.
     * @param Geburtstag Geburtstag-String des Beitragszahlers.
     **/
    public void registriereBeitragszahler(String Bezahler, String Beitragsnummer, String Geburtstag)
    {
        Profil temp = getProfil(Bezahler);
        if (temp != null)
        {
            temp.setBeitragszahler(Beitragsnummer, Geburtstag);
        }
        else
        {
            ErrorOutputWerkzeug.ErrorOutput(Errors.BezahlerRegistrierenError);
        }
    }

    /**
     * Löscht ein Profil mit dem übergebendem Namen, wenn dieses nicht der Beitragszahler ist.
     * 
     *  @param loeschendesProfil Das Profil das gelöscht werden soll.
     **/
    public void loescheProfil(String loeschendesProfil)
    {
        Profil temp = getProfil(loeschendesProfil);
        if (temp != null && !temp.istBeitragszahler())
        {
            profile.remove(temp);
        }
        else
        {
            ErrorOutputWerkzeug.ErrorOutput(Errors.ProfilEntfernenError);
        }
    }

    /**
     * Zahlt den übergebenen Betrag nur temporär aus, sodass ein neues Momentanes Guthaben ausgegeben wird.
     * 
     * @apiNote Erst mit {@link ProfilWerkzeug.speichereGuthaben()} speichert er das Guthaben endgültig.
     * 
     * @param profil Das betreffende Profil von dem ausgezahlt werden soll.
     * @param betrag Der Betrag der Auszahlung.
     **/
    public void MomentanesGuthabenAuszahlen(Profil profil, int betrag)
    {
        profil.setMomentanesGuthaben(new Geldbetrag(profil.getMomentanesGuthaben().getBetragInCent() - betrag, false));
        DateiWerkzeug.loggeEinAuszahlung("Auszahlung von " + profil.getVorname() + ": " + betrag + " Cent.");
    }

    /**
     * Zahlt den übergebenen Betrag nur temporär ein, sodass ein neues Momentanes Guthaben ausgegeben wird.
     * 
     * @apiNote Erst mit {@link ProfilWerkzeug.speichereGuthaben()} speichert er das Guthaben endgültig.
     * 
     * @param profil Das betreffende Profil auf das eingezahlt werden soll.
     * @param betrag Der Betrag der Einzahlung.
     **/
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
        if (neu < 0)
        {
            neuNegativ = true;
            neu = -neu;
        }
        profil.setMomentanesGuthaben(new Geldbetrag(neu, neuNegativ));
        DateiWerkzeug.loggeEinAuszahlung("Einzahlung von " + profil.getVorname() + ": " + betrag + " Cent.");
    }

    /**
     * Speichert den übergebenen Betrag, sodass ein neues Guthaben im Profil gespeichert wird.
     * 
     * @param profil Das betreffende Profil auf das ein- oder ausgezahlt werden soll.
     * @param betrag Der Betrag der Ein- bzw. Auszahlung.
     **/
    public void speichereGuthaben(Profil profil, int betrag)
    {
        profil.setGuthaben(new Geldbetrag(profil.getGuthaben().getBetragInCent() + betrag, false));
    }

    /**
     * Prüft ob der übergebene Betrag von dem Profil auszahlbar ist.
     * 
     * @param profil Das betreffende Profil von dem ausgezahlt werden soll.
     * @param betrag Der Betrag der angefragten Auszahlung.
     * @return true wenn das auszahlen möglich ist.
     **/
    public boolean istAuszahlenMoeglich(Profil profil, int betrag)
    {
        return profil.istAuszahlenMoeglich(betrag);
    }

    /**
     * Prüft ob der UI- Aktionen ausgelöst werden und reagiert entsprechend.
     * 
     **/
    private void registriereUIAktionen()
    {
        _ui.getAbbrechenButton().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                ProfilWerkzeugUI.schliessen();
            }
        });

        _ui.get_AuszugCheckbox().addItemListener(new ItemListener()
        {
            public void itemStateChanged(ItemEvent e)
            {
                if (e.getStateChange() == ItemEvent.SELECTED)
                {
                    _ui.get_spinnerAuszug().setEnabled(true);
                    _ui.getTextField(_ui.get_spinnerAuszug()).setText("01/2021");
                }
                else
                {
                    _ui.get_spinnerAuszug().setEnabled(false);
                    _ui.getTextField(_ui.get_spinnerAuszug()).setText("12/2099");
                }
            }
        });
        //        _ui.get_InfoButton().addActionListener(new ActionListener()
        //        {
        //            public void actionPerformed(ActionEvent e)
        //            {
        //                JOptionPane.showMessageDialog(null, "Falls der Bewohner noch nicht ausgezogen ist, wählen sie bitte als Auszugsdatum 12, 2099", "Auszugs Info ", JOptionPane.PLAIN_MESSAGE);
        //            }
        //        });

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
