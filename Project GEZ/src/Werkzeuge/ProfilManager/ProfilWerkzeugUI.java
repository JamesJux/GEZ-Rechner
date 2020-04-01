package Werkzeuge.ProfilManager;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Materialien.Profil;
import Werkzeuge.Startseite.Startseite;

public class ProfilWerkzeugUI extends JInternalFrame
{
    private static final String TITEL = "Bewohner - Editor";
    private static JFrame _frame;
    private JButton _infoButton;
    JTextField _textFieldVorname;
    JTextField _textFieldNachname;
    JTextField _textFieldTelefon;
    JTextField _textFieldEmail;
    Choice _choiceMonat;
    Choice _choiceJahr;
    Choice _choiceHaus;
    Choice _choiceEtage;
    Choice _choiceZimmer;
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
        _textFieldVorname.setText(profil.getName().getVorname());
        _textFieldNachname.setText(profil.getName().getNachname());
        _textFieldTelefon.setText(profil.getHandynummer());
        _textFieldEmail.setText(profil.getEmail());
        _choiceMonat.select("" + profil.getEinzugsdatum().toInstant().toString().substring(5, 7));
        _choiceJahr.select("" + profil.getEinzugsdatum().toInstant().toString().substring(0, 4));
        _choiceHaus.select("" + profil.getZimmer().getHaus());
        _choiceEtage.select("" + profil.getZimmer().getStockwerk());
        _choiceZimmer.select("" + profil.getZimmer().getZimmernummer());
        _choiceMonatAus.select("" + profil.getAuszugsdatum().toInstant().toString().substring(5, 7));
        _choiceJahrAus.select("" + profil.getAuszugsdatum().toInstant().toString().substring(0, 4));
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
        NamePanel.add(_textFieldVorname);
        _textFieldVorname.setColumns(15);
        _textFieldNachname = new JTextField();
        NamePanel.add(_textFieldNachname);
        _textFieldNachname.setColumns(15);

        JPanel HausUndZimmer = new JPanel();
        HauptPanel.add(HausUndZimmer);
        HausUndZimmer.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        JLabel HausLabel = new JLabel("  Haus :");
        HausUndZimmer.add(HausLabel);
        _choiceHaus = new Choice();
        _choiceHaus.add("1");
        _choiceHaus.add("2");
        HausUndZimmer.add(_choiceHaus);
        JLabel EtageLabel = new JLabel("  Etage :");
        HausUndZimmer.add(EtageLabel);
        _choiceEtage = new Choice();
        _choiceEtage.add("0");
        _choiceEtage.add("1");
        _choiceEtage.add("2");
        _choiceEtage.add("3");
        _choiceEtage.add("4");
        _choiceEtage.add("5");
        HausUndZimmer.add(_choiceEtage);
        JLabel ZimmerLabel = new JLabel("  Zimmer :");
        HausUndZimmer.add(ZimmerLabel);
        _choiceZimmer = new Choice();
        _choiceZimmer.add("00");
        _choiceZimmer.add("01");
        _choiceZimmer.add("02");
        _choiceZimmer.add("03");
        _choiceZimmer.add("04");
        _choiceZimmer.add("05");
        _choiceZimmer.add("06");
        _choiceZimmer.add("07");
        _choiceZimmer.add("08");
        _choiceZimmer.add("09");
        _choiceZimmer.add("10");
        _choiceZimmer.add("11");
        _choiceZimmer.add("12");
        _choiceZimmer.add("13");
        _choiceZimmer.add("14");
        _choiceZimmer.add("15");
        _choiceZimmer.add("16");
        _choiceZimmer.add("17");
        _choiceZimmer.add("18");
        _choiceZimmer.add("19");
        _choiceZimmer.add("20");
        HausUndZimmer.add(_choiceZimmer);

        JPanel EinzugdatumPanel = new JPanel();
        HauptPanel.add(EinzugdatumPanel);
        EinzugdatumPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        JLabel EinzugsLabel = new JLabel("  Einzugsmonat :");
        EinzugdatumPanel.add(EinzugsLabel);
        _choiceMonat = new Choice();
        _choiceMonat.add("1");
        _choiceMonat.add("2");
        _choiceMonat.add("3");
        _choiceMonat.add("4");
        _choiceMonat.add("5");
        _choiceMonat.add("6");
        _choiceMonat.add("7");
        _choiceMonat.add("8");
        _choiceMonat.add("9");
        _choiceMonat.add("10");
        _choiceMonat.add("11");
        _choiceMonat.add("12");
        EinzugdatumPanel.add(_choiceMonat);
        _choiceJahr = new Choice();
        _choiceJahr.add("2018");
        _choiceJahr.add("2019");
        _choiceJahr.add("2020");
        _choiceJahr.add("2021");
        _choiceJahr.add("2022");
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
        _choiceMonatAus.add("1");
        _choiceMonatAus.add("2");
        _choiceMonatAus.add("3");
        _choiceMonatAus.add("4");
        _choiceMonatAus.add("5");
        _choiceMonatAus.add("6");
        _choiceMonatAus.add("7");
        _choiceMonatAus.add("8");
        _choiceMonatAus.add("9");
        _choiceMonatAus.add("10");
        _choiceMonatAus.add("11");
        _choiceMonatAus.add("12");
        AuszugsdatumPanel.add(_choiceMonatAus);
        _choiceJahrAus = new Choice();
        _choiceJahrAus.add("2018");
        _choiceJahrAus.add("2019");
        _choiceJahrAus.add("2020");
        _choiceJahrAus.add("2021");
        _choiceJahrAus.add("2022");
        _choiceJahrAus.add("2099");
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

    public Choice get_choiceHaus()
    {
        return _choiceHaus;
    }

    public Choice get_choiceEtage()
    {
        return _choiceEtage;
    }

    public Choice get_choiceZimmer()
    {
        return _choiceZimmer;
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