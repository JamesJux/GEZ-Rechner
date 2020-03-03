package Fachwerte;

public class Name
{
    private final String VORNAME;
    private final String NACHNAME;

    public Name(String vorname, String name)
    {
        VORNAME = vorname;
        NACHNAME = name;
    }

    public String getVorname()
    {
        return VORNAME;
    }

    public String getNachname()
    {
        return NACHNAME;
    }

    public String toFormattedString()
    {
        return VORNAME + " " + NACHNAME;
    }
}
