package Fachwerte;

/**
 * Ein Geldbetrag in Euro, gespeichert als ganze Euro- und ganze Cent-Beträge.
 * 
 * @author Dominick Labatz
 * @version 19.10.2019
 */
public final class Geldbetrag
{

    private final int _euroAnteil;
    private final int _centAnteil;
    private final int _eurocent;
    private boolean _negativ;

    /**
     * Wählt einen Geldbetrag aus.
     * 
     * @param eurocent Der Betrag in ganzen Euro-Cent
     */
    public Geldbetrag(int eurocent, boolean minus)
    {
        _euroAnteil = eurocent / 100;
        _centAnteil = eurocent % 100;
        _eurocent = eurocent;
        _negativ = minus;
    }

    /**
     * Gibt den Betrag in Eurocent zurück.
     */
    public int getBetragInCent()
    {
        return _eurocent;
    }

    /**
     * Gibt zurück ob der Betrag negativ ist.
     * 
     * @return Boolean ob der Betrag negativ ist.
     */
    public boolean istBetragNegativ()
    {
        return _negativ;
    }

    /**
     * Gibt zurück ob der Betrag null ist.
     * 
     * @return Boolean ob der Betrag 0,00€ ist.
     */
    public boolean istBetragNull()
    {
        return _eurocent == 0;
    }

    /**
     * Liefert einen formatierten String des Geldbetrags in der Form "10,23"
     * zurück.
     * 
     * Format: (-) X,YY€
     * 
     * @return eine String-Repräsentation.
     */
    public String toFormattedString()
    {
        return getFormatiertenNegativAnteil() + _euroAnteil + "," + getFormatiertenCentAnteil() + " €";
    }

    /**
     * Liefert das Minus bei negativen Beträgen zurück.
     * 
     * @return eine String-Repräsentation des Minus.
     */
    private String getFormatiertenNegativAnteil()
    {
        String result = "";
        if (_negativ)
        {
            result = "-";
        }
        return result;
    }

    /**
     * Liefert einen zweistelligen Centbetrag zurück.
     * 
     * @return eine String-Repräsentation des Cent-Anteils.
     */
    private String getFormatiertenCentAnteil()
    {
        String result = "";
        if (_centAnteil < 10)
        {
            result += "0";
        }
        result += _centAnteil;
        return result;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime + _centAnteil;
        result = prime * result + _euroAnteil;
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        boolean result = false;
        if (obj instanceof Geldbetrag)
        {
            Geldbetrag other = (Geldbetrag) obj;
            result = (_centAnteil == other._centAnteil)
                    && (_euroAnteil == other._euroAnteil) && (_negativ = other._negativ);
        }
        return result;
    }
}
