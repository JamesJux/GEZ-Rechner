package Service;

import java.util.HashSet;

import javax.swing.JOptionPane;

import Enums.Errors;
import Fachwerte.Datum;
import Fachwerte.Geldbetrag;
import Material.Profil;

public class ProfilManagerService
{
    //TODO hier sollte das eigentliche Programm stehen

    private HashSet<Profil> profile;

    /*
     *TODO Speicherfunktion des ProfilManagers 
     * 
     * Der Konstruktor soll später ermitteln ob es eine Speicherdatei gibt und dann
     * entweder den Gespeicherten Profilmanager laden, oder einen neuen erstellen
     */
    public ProfilManagerService()
    {
        profile = new HashSet<Profil>();
    }

    public void createProfil(String zimmer, String vorname, String name, int guthaben, int EinMonat, int EinJahr, String email,
            String handynummer, int AusMonat, int AusJahr)
    {
        Profil newProfile = new Profil(zimmer, vorname, name, guthaben, email, handynummer, EinMonat, EinJahr, AusMonat, AusJahr);

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

    public void NormalBetrieb()
    {
        String RechnendeMonate = JOptionPane.showInputDialog(null, "Für welchen Monat sollen das Guthaben ausgerechnet werden?", "GEZ-Rechner 2020", JOptionPane.PLAIN_MESSAGE);
        if (!(RechnendeMonate.matches("/d")))
        {

            int AnzahlMonate = Integer.valueOf(RechnendeMonate);
            int jetztJahr = 2020;
            while (AnzahlMonate > 12)
            {
                AnzahlMonate -= 12;
                jetztJahr++;
            }
            System.out.println("Bis einschließlich " + findeMonat(AnzahlMonate - 1) + " " + jetztJahr + " sind die Guthaben:");

            for (Profil profil : profile)
            {
                int betragCent = 0;

                int jahr = 2018;
                int monat = 10;

                if (!(profil.getName().toFormattedString().equals("Dominick Labatz")))
                {
                    for (int i = 0; i < (14 + AnzahlMonate); i++)
                    {
                        monat++;

                        if (wohntImHaus(profil, new Datum(15, monat, jahr)))
                        {
                            int diesenMonat = runden(17500 / AnzahlZahlendeBewohner(new Datum(15, monat, jahr)));
                            betragCent -= diesenMonat;
                        }
                        if (monat == 12)
                        {
                            monat = 0;
                            jahr++;
                        }
                    }

                    int DiffBetragCent = betragCent + profil.getGuthaben().getBetragInCent();
                    if (DiffBetragCent >= 0)
                    {
                        // Noch Guthaben vorhanden
                        Geldbetrag RestBetrag = new Geldbetrag(DiffBetragCent);
                        if (DiffBetragCent != 0)
                        {
                            System.out.println(profil.getName().toFormattedString() + ": " + RestBetrag.toFormattedString());
                        }

                    }
                    else
                    {
                        // Kein Guthaben mehr vorhanden
                        Geldbetrag RestBetrag = new Geldbetrag(-DiffBetragCent);
                        System.out.println(profil.getName().toFormattedString() + ": -" + RestBetrag.toFormattedString());
                    }
                }
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Bitte versuchen sie es erneut mit einer Gültigen Eingabe", "Fehlerhafte Eingabe", JOptionPane.WARNING_MESSAGE);
        }
    }

    private static String findeMonat(int rechnendeMonate)
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

    public int AnzahlZahlendeBewohner(Datum datum)
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

    private boolean wohntImHaus(Profil profil, Datum datum)
    {
        if ((profil.getEinzugsdatum()).getTagZahl() < datum.getTagZahl() && datum.getTagZahl() < (profil.getAuszugsdatum()).getTagZahl())
        {
            return true;
        }
        return false;
    }

    private int runden(int zahl)
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

    public HashSet<Profil> getProfile()
    {
        return profile;
    }
}
