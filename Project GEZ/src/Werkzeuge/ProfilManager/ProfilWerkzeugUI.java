package Werkzeuge.ProfilManager;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Materialien.Profil;
import Werkzeuge.Startseite.Startseite;

/**
 * Das GUI des ProfilWerkzeuges des GEZ-Rechners.
 *  
 * @author Dominick Labatz
 * @version 02.04.2020
 */
public class ProfilWerkzeugUI extends JInternalFrame
{
    private static final String TITEL = "Bewohner - Editor";
    private static JFrame _frame;
    private JButton _infoButton;
    JTextField _textFieldVorname;
    JTextField _textFieldNachname;
    JTextField _textFieldZimmer;
    Choice _choiceMonat;
    Choice _choiceJahr;
    JTextField _textFieldTelefon;
    JTextField _textFieldEmail;
    Choice _choiceJahrAus;
    Choice _choiceMonatAus;
    JButton _speichernButton;
    JButton _abbrechenButton;
    JButton _bewohnerLöschenButton;

    public ProfilWerkzeugUI()
    {
        intitialisieren();
    }

    public ProfilWerkzeugUI(Profil profil)
    {
        intitialisieren();
        _textFieldVorname.setText(profil.getVorname());
        _textFieldNachname.setText(profil.getNachname());
        _textFieldTelefon.setText(profil.getHandynummer());
        _textFieldEmail.setText(profil.getEmail());
        _choiceMonat.select("" + profil.getEinzugsdatum().toInstant().toString().substring(5, 7));
        _textFieldZimmer.setText(profil.getZimmer());
        _choiceMonatAus.select("" + profil.getAuszugsdatum().toInstant().toString().substring(5, 7));
        _choiceJahrAus.select("" + profil.getAuszugsdatum().toInstant().toString().substring(0, 4));
        try
        {
            String temp = profil.getEinzugsdatum().toInstant().toString().substring(0, 4);
            _choiceJahr.remove(temp);
            _choiceJahr.add(temp);
            _choiceJahr.select(temp);
            temp = profil.getAuszugsdatum().toInstant().toString().substring(0, 4);
            _choiceJahrAus.remove(temp);
            _choiceJahrAus.add(temp);
            _choiceJahrAus.select(temp);
        }
        catch (IllegalArgumentException e)
        {

        }
    }

    public void intitialisieren()
    {
        _frame = new JFrame(TITEL);
        _frame.setBounds(600, 400, 450, 320);
        _frame.setVisible(true);
        _frame.setResizable(false);
        _frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        _frame.getContentPane().setLayout(new BorderLayout(0, 0));

        _frame.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                if (AbfrageSchliessenButton())
                {
                    schliessen();
                }
            }
        });

        JPanel HauptPanel = new JPanel();
        _frame.getContentPane().add(HauptPanel, BorderLayout.CENTER);

        HauptPanel.setLayout(new GridLayout(0, 1));

        JPanel NamePanel = new JPanel();
        HauptPanel.add(NamePanel);
        NamePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        JLabel NameLabel = new JLabel("  Vor- Nachname :");
        NamePanel.add(NameLabel);
        _textFieldVorname = new JTextField();
        _textFieldVorname.setColumns(15);
        NamePanel.add(_textFieldVorname);
        _textFieldNachname = new JTextField();
        _textFieldNachname.setColumns(15);
        NamePanel.add(_textFieldNachname);

        JPanel ZimmerPanel = new JPanel();
        HauptPanel.add(ZimmerPanel);
        ZimmerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        JLabel ZimmerLabel = new JLabel("  Zimmer :");
        ZimmerPanel.add(ZimmerLabel);
        _textFieldZimmer = new JTextField();
        _textFieldZimmer.setColumns(10);
        ZimmerPanel.add(_textFieldZimmer);

        JPanel EinzugdatumPanel = new JPanel();
        HauptPanel.add(EinzugdatumPanel);
        EinzugdatumPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        JLabel EinzugsLabel = new JLabel("  Einzugsmonat :");
        EinzugdatumPanel.add(EinzugsLabel);
        _choiceMonat = new Choice();

        EinzugdatumPanel.add(_choiceMonat);
        _choiceJahr = new Choice();
        EinzugdatumPanel.add(_choiceJahr);

        JPanel TelefonPanel = new JPanel();
        HauptPanel.add(TelefonPanel);
        TelefonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        JLabel TelefonLabel = new JLabel("  Telefon :");
        TelefonPanel.add(TelefonLabel);
        _textFieldTelefon = new JTextField();
        TelefonPanel.add(_textFieldTelefon);
        _textFieldTelefon.setColumns(30);

        JPanel EmailPanel = new JPanel();
        HauptPanel.add(EmailPanel);
        EmailPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        JLabel EmailLabel = new JLabel("  E-Mail :   ");
        EmailPanel.add(EmailLabel);
        _textFieldEmail = new JTextField();
        EmailPanel.add(_textFieldEmail);
        _textFieldEmail.setColumns(30);

        JPanel AuszugsdatumPanel = new JPanel();
        HauptPanel.add(AuszugsdatumPanel);
        AuszugsdatumPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        JLabel _auszugsLabel = new JLabel("  Auszugsmonat :");
        AuszugsdatumPanel.add(_auszugsLabel);
        _choiceMonatAus = new Choice();
        AuszugsdatumPanel.add(_choiceMonatAus);
        _choiceJahrAus = new Choice();

        AuszugsdatumPanel.add(_choiceJahrAus);
        _infoButton = new JButton("Info");
        AuszugsdatumPanel.add(_infoButton);

        JPanel ButtonPanel = new JPanel();
        _frame.getContentPane().add(ButtonPanel, BorderLayout.SOUTH);
        _bewohnerLöschenButton = new JButton("Bewohner Löschen");
        ButtonPanel.add(_bewohnerLöschenButton);
        _speichernButton = new JButton("Speichern");
        ButtonPanel.add(_speichernButton);
        _abbrechenButton = new JButton("Abbrechen");
        ButtonPanel.add(_abbrechenButton);

        for (int i = 1; i <= 12; i++)
        {
            _choiceMonat.add("" + i);
            _choiceMonatAus.add("" + i);
        }

        int aktJahr = Integer.valueOf(new GregorianCalendar().toInstant().toString().substring(0, 4));
        for (int i = -2; i <= 2; i++)
        {
            _choiceJahr.add("" + (aktJahr + i));
            _choiceJahrAus.add("" + (aktJahr + i));
        }
        _choiceJahrAus.add("2099");

        _frame.revalidate();
    }

    public JTextField get_textFieldVorname()
    {
        return _textFieldVorname;
    }

    public JTextField get_textFieldNachname()
    {
        return _textFieldNachname;
    }

    public JTextField get_textFieldTelefon()
    {
        return _textFieldTelefon;
    }

    public JTextField get_textFieldEmail()
    {
        return _textFieldEmail;
    }

    public Choice get_choiceMonat()
    {
        return _choiceMonat;
    }

    public Choice get_choiceJahr()
    {
        return _choiceJahr;
    }

    public JTextField get_textFieldZimmer()
    {
        return _textFieldZimmer;
    }

    public Choice get_choiceJahrAus()
    {
        return _choiceJahrAus;
    }

    public Choice get_choiceMonatAus()
    {
        return _choiceMonatAus;
    }

    public JButton get_speichernButton()
    {
        return _speichernButton;
    }

    public JButton get_bewohnerLöschenButton()
    {
        return _bewohnerLöschenButton;
    }

    public JButton getAbbrechenButton()
    {
        return _abbrechenButton;
    }

    public JButton get_InfoButton()
    {
        return _infoButton;
    }

    private static boolean AbfrageSchliessenButton()
    {
        return (JOptionPane.showConfirmDialog(_frame, "Nicht gespeicherte Änderungen gehen verloren.", "Beenden?", JOptionPane.OK_CANCEL_OPTION) == 0);
    }

    static void schliessen()
    {
        _frame.removeAll();
        _frame.dispose();
        Startseite.setzeAktiv();
    }
}