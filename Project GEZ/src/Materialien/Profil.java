package Materialien;

import java.util.GregorianCalendar;

import Fachwerte.Geldbetrag;

/**
 * Erzeugt das Profil eines jeden Bewohners.
 *  
 * @author Dominick Labatz
 * @version 02.04.2020
 */
public class Profil
{
    private final String _vorname;
    private final String _nachname;
    private String _zimmer;
    private GregorianCalendar _einzugsdatum;
    private GregorianCalendar _auszugsdatum;

    private Geldbetrag _guthaben;
    private String _email;
    private String _handynummer;
    private boolean _bezahler;
    private Geldbetrag _momentanesGuthaben;
    private int _VorraussichtlicheDauer;

    /**
     * Erstellung eines Profils für eine neue Person
     * 
     * @param zimmer Das Zimmer der Person
     * @param vorname Der Vorame der Person
     * @param nachname Der Nachname der Person
     * @param guthaben Der bisher eingezahlte Betrag in Eurocent
     * @param email Die Email der Person
     * @param handynummer Die Handynummer der Person
     * @param EinMonat Der Einzugsmonat der Person
     * @param EinJahr Das Einzugsjahr der Person
     * @param AusMonat Der Auszugsmonat der Person
     * @param AusMonat Das Auszugsjahr der Person
     */

    public Profil(String zimmer, String vorname, String name, int guthaben, String email, String handynummer,
            int EinMonat, int EinJahr, int AusMonat, int AusJahr)
    {
        _zimmer = zimmer;
        _vorname = vorname;
        _nachname = name;
        _einzugsdatum = new GregorianCalendar(EinJahr, EinMonat - 1, 2);
        _auszugsdatum = new GregorianCalendar(AusJahr, AusMonat - 1, 20);
        _guthaben = new Geldbetrag(guthaben, false);
        _email = email;
        _handynummer = handynummer;
        _bezahler = false;
        _momentanesGuthaben = new Geldbetrag(guthaben, false);
        _VorraussichtlicheDauer = 0;
    }

    public String getVorname()
    {
        return _vorname;
    }

    public String getNachname()
    {
        return _nachname;
    }

    public String getZimmer()
    {
        return _zimmer;
    }

    public GregorianCalendar getEinzugsdatum()
    {
        return _einzugsdatum;
    }

    public GregorianCalendar getAuszugsdatum()
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

    public void setGuthaben(Geldbetrag guthaben)
    {
        _guthaben = guthaben;
    }

    public boolean istAuszahlenMoeglich(int betrag)
    {
        return (_guthaben.getBetragInCent() >= betrag);
    }

    public Geldbetrag getMomentanesGuthaben()
    {
        return _momentanesGuthaben;
    }

    public void setMomentanesGuthaben(Geldbetrag betrag)
    {
        _momentanesGuthaben = betrag;
    }

    public boolean istBezahler()
    {
        return _bezahler;
    }

    public void setBezahler()
    {
        _bezahler = true;
    }

    public int getVorrsichtlicheDauer()
    {
        return _VorraussichtlicheDauer;
    }

    public void setVorraussichtlicheDauer(int monate)
    {
        _VorraussichtlicheDauer = monate;
    }
}
