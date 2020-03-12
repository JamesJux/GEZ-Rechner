package Fachwerte;

import java.util.Calendar;

public class Datum
{
    private final int TAG;
    private final int MONAT;
    private final int JAHR;
    private final int TagZahl;

    /*
     * Eine Klasse die ein Datum für einen gewissen Tag erstellt.
     * 
     */
    public Datum(Daten daten)
    {
        Calendar calendar = Calendar.getInstance();

        switch (daten)
        {
        case heute:
            TAG = calendar.get(Calendar.DATE);
            MONAT = calendar.get(Calendar.MONTH);
            JAHR = calendar.get(Calendar.YEAR);
            break;
        case vorherigerMonat:
            TAG = calendar.get(Calendar.DATE);
            MONAT = calendar.get(Calendar.MONTH) - 1;
            JAHR = calendar.get(Calendar.YEAR);
            break;
        case naechsterMonat:
            TAG = calendar.get(Calendar.DATE);
            MONAT = calendar.get(Calendar.MONTH) + 1;
            JAHR = calendar.get(Calendar.YEAR);
            break;
        default:
            TAG = 0;
            MONAT = 0;
            JAHR = 0;
        }
        TagZahl = ((JAHR - 2000) * 360) + (MONAT * 30) + TAG;
    }

    public Datum(int tag, int monat, int jahr)
    {
        TAG = tag;
        MONAT = monat;
        JAHR = jahr;
        TagZahl = ((jahr - 2000) * 360) + (monat * 30) + tag;
    }

    public int getTag()
    {
        return TAG;
    }

    public int getMonat()
    {
        return MONAT;
    }

    public int getJahr()
    {
        return JAHR;
    }

    public int getTagZahl()
    {
        return TagZahl;
    }

    public String toFormattedString()
    {
        return String.format("%02d.%02d.%4d", TAG, MONAT, JAHR);
    }
}
