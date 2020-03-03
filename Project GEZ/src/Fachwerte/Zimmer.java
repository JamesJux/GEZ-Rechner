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

    private final int HAUS, STOCKWERK;
    private final String ZIMMERNUMMER, ZIMMERID;

    public Zimmer(String zimmer)
    {
        HAUS = Integer.valueOf(zimmer.substring(0, 1));
        STOCKWERK = Integer.valueOf(zimmer.substring(2, 3));
        int zimmernummer = Integer.valueOf(zimmer.substring(3, 5));
        if (zimmernummer < 10)
        {
            ZIMMERNUMMER = "0" + zimmernummer;
        }
        else if (zimmernummer < 0)
        {
            ZIMMERNUMMER = "ERROR";
            ErrorOutputService.ErrorOutputConsole(Errors.inputError);
        }
        else
        {
            ZIMMERNUMMER = "" + zimmernummer;
        }

        ZIMMERID = HAUS + "/" + STOCKWERK + ZIMMERNUMMER;
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
