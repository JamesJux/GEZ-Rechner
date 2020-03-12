package Materialien;

import Fachwerte.Datum;
import Fachwerte.Geldbetrag;
import Fachwerte.Name;
import Fachwerte.Zimmer;

public class Profil
{
    private Name _name;
    private Zimmer _zimmer;
    private Datum _einzugsdatum;
    private Datum _auszugsdatum;

    private Geldbetrag _guthaben;
    private String _email;
    private String _handynummer;
    private boolean _bezahler;
    private Geldbetrag _momentanesGuthaben;

    /**
     * Erstellung eines Profils für eine neue Person
     * 
     * @param zimmer Das Zimmer der Person
     * @param name Der Name der Person
     * @param guthaben Der bisher eingezahlte Betrag in Eurocent
     * @param email Die Email der Person
     * @param handynummer Die Handynummer der Person
     * @param EinMonat Der Einzugsmonat der Person
     * @param EinJahr Das Einzugsjahr der Person
     * @param AusMonat Der Auszugsmonat der Person
     * @param AusMonat Das Auszugsjahr der Person
     */

    public Profil(String zimmer, String vorname, String name, int guthaben, String email, String handynummer,
            int EinMonat, int EinJahr, int AusMonat, int AusJahr, boolean bezahler)
    {
        _zimmer = new Zimmer(zimmer);
        _name = new Name(vorname, name);
        _einzugsdatum = new Datum(1, EinMonat, EinJahr);
        _auszugsdatum = new Datum(30, AusMonat, AusJahr);
        _guthaben = new Geldbetrag(guthaben, false);
        _email = email;
        _handynummer = handynummer;
        _bezahler = bezahler;
        _momentanesGuthaben = new Geldbetrag(guthaben, false);
    }

    @Override
    public int hashCode()
    {
        String hashString = _name.toFormattedString() + _zimmer.toFormattedString() + _einzugsdatum.toFormattedString();

        char hashArray[] = hashString.toCharArray();

        int test[] = new int[hashArray.length];
        for (int i = 0; i < hashArray.length - 1; i++)
        {
            test[i] = (int) hashArray[i];
        }

        int hashCode = 0;
        for (int i : test)
        {
            hashCode += i;
        }

        return hashCode;
    }

    @Override
    public boolean equals(Object p)
    {
        return this.hashCode() == p.hashCode();
    }

    public Name getName()
    {
        return _name;
    }

    public Zimmer getZimmer()
    {
        return _zimmer;
    }

    public Datum getEinzugsdatum()
    {
        return _einzugsdatum;
    }

    public Datum getAuszugsdatum()
    {
        return _auszugsdatum;
    }

    public String getEmail()
    {
        return _email;
    }

    public void setEmail(String email)
    {
        this._email = email;
    }

    public String getHandynummer()
    {
        return _handynummer;
    }

    public void setHandynummer(String handynummer)
    {
        _handynummer = handynummer;
    }

    public Geldbetrag getGuthaben()
    {
        return _guthaben;
    }

    public void setEinzahlGuthaben(int betrag)
    {
        _guthaben = new Geldbetrag(_guthaben.getBetragInCent() + betrag, false);
    }

    public boolean istAuszahlenMoeglich(int betrag)
    {
        System.out.println("Prüfung Auszahlen möglich");
        return (_guthaben.getBetragInCent() >= betrag);
    }

    public void setAuszahlGuthaben(int betrag)
    {
        System.out.println("auszahlen..." + _guthaben.getBetragInCent());
        _guthaben = new Geldbetrag(_guthaben.getBetragInCent() - betrag, false);
        System.out.println("nach abziehen: " + _guthaben.getBetragInCent());
    }

    public Geldbetrag getMomentanesGuthaben()
    {
        return _momentanesGuthaben;
    }

    public void setMomentanesGuthaben(Geldbetrag restBetrag)
    {
        _momentanesGuthaben = restBetrag;
    }

    public boolean getBezahler()
    {
        return _bezahler;
    }

    public void setBezahler()
    {
        _bezahler = true;
    }
}
