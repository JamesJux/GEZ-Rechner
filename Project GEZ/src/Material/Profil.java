package Material;

import Fachwerte.Datum;
import Fachwerte.Geldbetrag;
import Fachwerte.Name;
import Fachwerte.Zimmer;

public class Profil
{
    private final Name NAME;
    private final Zimmer ZIMMER;
    private final Datum EINZUGSDATUM;
    private final Datum AUSZUGSDATUM;

    private Geldbetrag _guthaben;
    private String _email;
    private String _handynummer;

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
            int EinMonat, int EinJahr, int AusMonat, int AusJahr)
    {
        ZIMMER = new Zimmer(zimmer);
        NAME = new Name(vorname, name);
        EINZUGSDATUM = new Datum(1, EinMonat, EinJahr);
        if (AusMonat == 0 && AusJahr == 0)
        {
            AUSZUGSDATUM = new Datum(30, 12, 2099);
        }
        else
        {
            AUSZUGSDATUM = new Datum(30, AusMonat, AusJahr);
        }

        _guthaben = new Geldbetrag(guthaben);
        _email = email;
        _handynummer = handynummer;
    }

    @Override
    public int hashCode()
    {
        String hashString = NAME.toFormattedString() + ZIMMER.toFormattedString() + EINZUGSDATUM.toFormattedString();

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
        return NAME;
    }

    public Zimmer getZimmer()
    {
        return ZIMMER;
    }

    public Datum getEinzugsdatum()
    {
        return EINZUGSDATUM;
    }

    public Datum getAuszugsdatum()
    {
        return AUSZUGSDATUM;
    }

    public String getEmail()
    {
        return _email;
    }

    public String getHandynummer()
    {
        return _handynummer;
    }

    public Geldbetrag getGuthaben()
    {
        return _guthaben;
    }

    public void setEmail(String email)
    {
        this._email = email;
    }

    public void setHandynummer(String handynummer)
    {
        this._handynummer = handynummer;
    }
}
