package Materialien;

import java.util.GregorianCalendar;

import Fachwerte.Geldbetrag;

/**
 * Erzeugt das Profil eines jeden Bewohners.
 *  
 * @author Dominick Labatz
 * @version 02.04.2020
 **/
public class Profil
{
    private final String _vorname;
    private final String _nachname;
    private GregorianCalendar _einzugsdatum;
    private GregorianCalendar _auszugsdatum;

    private Geldbetrag _guthaben;
    private String _email;
    private String _handynummer;
    private boolean _beitragszahler;
    private Geldbetrag _momentanesGuthaben;
    private int _vorraussichtlicheDauer;
    private String _beitragsnummer;
    private String _geburtstag;

    /**
     * Erstellung eines Profils für eine neue Person
     * 
     * @param Vorname Der Vorame der Person
     * @param Nachname Der Nachname der Person
     * @param guthaben Der bisher eingezahlte Betrag in Eurocent
     * @param Email Die Email der Person
     * @param Handynummer Die Handynummer der Person
     * @param EinMonat Der Einzugsmonat der Person
     * @param EinJahr Das Einzugsjahr der Person
     * @param AusMonat Der Auszugsmonat der Person
     * @param AusMonat Das Auszugsjahr der Person
     **/
    public Profil(String Vorname, String Nachname, int guthaben, String Email, String Handynummer,
            int EinMonat, int EinJahr, int AusMonat, int AusJahr)
    {
        _vorname = Vorname;
        _nachname = Nachname;
        _einzugsdatum = new GregorianCalendar(EinJahr, EinMonat - 1, 2);
        _auszugsdatum = new GregorianCalendar(AusJahr, AusMonat - 1, 20);
        _guthaben = new Geldbetrag(guthaben, false);
        _email = Email;
        _handynummer = Handynummer;
        _beitragszahler = false;
        _beitragsnummer = "";
        _geburtstag = "";
        _momentanesGuthaben = new Geldbetrag(guthaben, false);
        _vorraussichtlicheDauer = 0;
    }

    /**
     * Gibt den String des Vornamen des Profils.
     * 
     * @return Den Vornamen
     **/
    public String getVorname()
    {
        return _vorname;
    }

    /**
     * Gibt den String des Nachnamen des Profils.
     * 
     * @return  Den Nachnamen
     **/
    public String getNachname()
    {
        return _nachname;
    }

    /**
     * Gibt den gesammten Namen aus als formatierten String aus.
     * 
     * @return Den Vor- und Nachnamen
     **/
    public String getName()
    {
        return _vorname + " " + _nachname;
    }

    /**
     * Gibt das Einzugsdateum als Instanz eines GregorianCalender zurück.
     * 
     * @return Das Einzugsdatum
     **/
    public GregorianCalendar getEinzugsdatum()
    {
        return _einzugsdatum;
    }

    /**
     * Gibt das Auszugsdateum als Instanz eines GregorianCalender zurück.
     * 
     * @return Das Auszugsdatum
     **/
    public GregorianCalendar getAuszugsdatum()
    {
        return _auszugsdatum;
    }

    /**
     * Gibt die Email-Adresse aus.
     * 
     * @return Die angegebene E-Mailadresse
     **/
    public String getEmail()
    {
        return _email;
    }

    /**
     * Gibt die Handynummer aus.
     * 
     * @return Die angegebene Handynummer
     **/
    public String getHandynummer()
    {
        return _handynummer;
    }

    /**
     * Gibt das gesammte bereits eingezahlte Guthaben als Geldbetrag zurück.
     * 
     * @return Das eingezahlte Guthaben
     **/
    public Geldbetrag getGuthaben()
    {
        return _guthaben;
    }

    /**
     * Setzt das eingezahlte Guthaben auf den übergebenen Wert.
     * 
     * @param guthaben Das neue Guthaben
     **/
    public void setGuthaben(Geldbetrag guthaben)
    {
        _guthaben = guthaben;
    }

    /**
     * Prüft ob der Betrag aus gezahlt werden kann.
     * 
     * @param betrag Der Betrag der geprüft werden soll.
     * @return {@code true} wenn der Betrag ausgezahlt werden kann, andernfalls {@code false}
     **/
    public boolean istAuszahlenMoeglich(int betrag)
    {
        return (_momentanesGuthaben.getBetragInCent() >= betrag);
    }

    /**
     * Gibt das Momentane Guthaben als Geldbetrag aus.
     * 
     * @return Der Momentane Guthabenstand
     **/
    public Geldbetrag getMomentanesGuthaben()
    {
        return _momentanesGuthaben;
    }

    /**
     * Setzt den Momentanen Guthabenstand auf den übergebenen Geldbetrag.
     * 
     * @apiNote Wichtig: Dies wird nur temporär gespeichert, falls der
     * Betrag nicht durch {@link ProfilWerkzeug.speichereGuthaben(Profil, int)}
     * im Profil gespeichert wird.
     * 
     * @param betrag Der Betrag auf den es gesetzt werden soll
     **/
    public void setMomentanesGuthaben(Geldbetrag betrag)
    {
        _momentanesGuthaben = betrag;
    }

    /**
     * Gibt den Status, des Bezahlers zurück.
     * 
     * @return Nur {@code true} wenn das Profil als Bezahler registriert ist, andernfalls {@code false}
     **/
    public boolean istBeitragszahler()
    {
        return _beitragszahler;
    }

    /**
     * Registriert dieses Konto als Bezahler.
     * 
     * @apiNote Es ist darauf zu achten, dass es nur einen Bezahler gibt.
     * 
     **/
    public void setBeitragszahler(String beitragsnummer, String geburtstag)
    {
        _beitragszahler = true;
        _beitragsnummer = beitragsnummer;
        _geburtstag = geburtstag;
    }

    /**
     * Gibt die Beitragsnummer des Beitragszahlers, sonst leerer String.
     * 
     * @return Die Beitragsnummer als String.
     **/
    public String getBeitragsnummer()
    {
        return _beitragsnummer;
    }

    /**
     * Gibt den Geburtstag des Beitragszahlers, sonst leerer String.
     * 
     * @return Den Geburtstag als String.
     **/
    public String getGeburtstag()
    {
        return _geburtstag;
    }

    /**
     * Gibt die Anzahl an Monaten die das {@link _momentaneGuthaben} noch reicht.
     * 
     * @return Die Anzahl an Monaten, 
     **/
    public int getVorrsichtlicheDauer()
    {
        return _vorraussichtlicheDauer;
    }

    /**
     * Setzt {@link _vorraussichtlicheDauer} auf die übergebenen Anzahl an Monaten.
     * 
     * @param monate Die Anzahl an Monaten die das Restgethaben noch reicht. 
     **/
    public void setVorraussichtlicheDauer(int monate)
    {
        _vorraussichtlicheDauer = monate;
    }
}
