package Service;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;

import javax.swing.JOptionPane;

import Enums.Errors;
import Fachwerte.Datum;
import Material.Profil;
import Startup.BewohnerEditorUI;

public class ProfilManagerService
{
    //TODO hier sollte das eigentliche Programm stehen

    private HashSet<Profil> profile;
    BewohnerEditorUI _ui;

    public ProfilManagerService()
    {
        profile = new HashSet<Profil>();
    }

    public void erzeugeProfilManagerUI()
    {
        _ui = new BewohnerEditorUI();
        registriereUIAktionen();
    }

    public void erzeugeProfilManagerUI(Profil profil)
    {
        _ui = new BewohnerEditorUI(profil);
        registriereUIAktionen();
    }

    public void createProfil(String zimmer, String vorname, String name, int guthaben, int EinMonat, int EinJahr, String email,
            String handynummer, int AusMonat, int AusJahr, boolean bezahler)
    {
        Profil newProfile = new Profil(zimmer, vorname, name, guthaben, email, handynummer, EinMonat, EinJahr, AusMonat, AusJahr, bezahler);

        profile.add(newProfile);
    }

    public void deleteProfil(Profil profil)
    {
        if (profile.contains(profil))
        {
            profile.remove(profil);
        }
        else
        {
            ErrorOutputService.ErrorOutputConsole(Errors.profileRemoveError);
        }
    }

    public void neuenBenutzerSpeichern()
    {
        String zimmer = _ui.get_choiceHaus().getSelectedItem() + "/" + _ui.get_choiceEtage().getSelectedItem()
                + _ui.get_choiceZimmer().getSelectedItem();
        String vorname = _ui.get_textFieldVorname().getText();
        String name = _ui.get_textFieldNachname().getText();
        if (vorname.equals("") || name.equals(""))
        {
            ErrorOutputService.ErrorOutputConsole(Errors.inputError);
        }
        int Guthaben = 12345;
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
        //        System.out.println(zimmer + ";" + vorname + ";" + name + ";" + Email + ";" + Handynummer + ";" + Guthaben + ";" + EinMonat + ";"
        //                + EinJahr + ";" + AusMonat + ";" + AusJahr);
        createProfil(zimmer, vorname, name, Guthaben, EinMonat, EinJahr, Email, Handynummer, AusMonat, AusJahr, false);
    }

    public int getAnzahlZahlendeBewohner(Datum datum)
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

    boolean wohntImHaus(Profil profil, Datum datum)
    {
        if ((profil.getEinzugsdatum()).getTagZahl() < datum.getTagZahl() && datum.getTagZahl() < (profil.getAuszugsdatum()).getTagZahl())
        {
            return true;
        }
        return false;
    }

    public HashSet<Profil> getProfile()
    {
        return profile;
    }

    public void registriereBezahler(String Bezahler)
    {
        for (Profil profil : profile)
        {
            if (Bezahler.equals(profil.getName().toFormattedString()))
            {
                profil.setBezahler();
            }
        }
    }

    public final void registriereUIAktionen()
    {
        _ui.getAbbrechenButton().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                _ui.schliesseFenster();
            }
        });

        _ui.get_InfoButton().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                JOptionPane.showMessageDialog(null, "Falls der Bewohner noch nicht ausgezogen ist, wählen sie als Auszugsdatum 12 2099", "Auszugs Info ", JOptionPane.WARNING_MESSAGE);
            }
        });

        _ui.get_speichernButton().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                neuenBenutzerSpeichern();
            }
        });
    }
}
