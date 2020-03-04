package Fachwerte;

import Enums.Errors;
import Service.ErrorOutputService;

/*
 * Generierung von Zimmern
 * 
 * bsp: A/BCC  			A = Haus B = Stockwerk C = Zimmernummer 
 */
public class Zimmer
{

    private final int _haus;
    private final int _stockwerk;
    private final String _zimmernummer;
    private final String ZIMMERID;

    public Zimmer(String zimmer)
    {
        _haus = Integer.valueOf(zimmer.substring(0, 1));
        _stockwerk = Integer.valueOf(zimmer.substring(2, 3));
        int zimmernummer = Integer.valueOf(zimmer.substring(3, 5));
        if (zimmernummer < 10)
        {
            _zimmernummer = "0" + zimmernummer;
        }
        else if (zimmernummer < 0)
        {
            _zimmernummer = "ERROR";
            ErrorOutputService.ErrorOutputConsole(Errors.inputError);
        }
        else
        {
            _zimmernummer = "" + zimmernummer;
        }

        ZIMMERID = _haus + "/" + _stockwerk + _zimmernummer;
    }

    public int getHaus()
    {
        return _haus;
    }

    public int getStockwerk()
    {
        return _stockwerk;
    }

    public String getZimmernummer()
    {
        return _zimmernummer;
    }

    /**
     * Die Methode gibt die Zimmer ID nach einem bestimmten Schema als String aus
     * 
     * @return String, die Zimmer ID nach dem Schema: A/BCC || A = Haus B = Stockwerk C = Zimmernummer  
     */
    public String toFormattedString()
    {
        return ZIMMERID;
    }
}
