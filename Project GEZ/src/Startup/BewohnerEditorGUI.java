package Startup;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BewohnerEditorGUI extends JDialog
{
    private static final String TITEL = "Bewohner - Editor";

    private static final JFrame _frame = new JFrame(TITEL);
    private JTextField _textFieldVorname;
    private JTextField _textFieldNachname;
    private JTextField _textFieldTelefon;
    private JTextField _textFieldEmail;
    private Choice _choiceMonat;
    private Choice _choiceJahr;
    private Choice _choiceHaus;
    private Choice _choiceEtage;
    private Choice _choiceZimmer;
    private Choice _choiceJahrAus;
    private Choice _choiceMonatAus;
    JButton _speichernButton;
    JButton AbbrechenButton;

    public static void main(String[] args)
    {
        try
        {
            BewohnerEditorGUI dialog = new BewohnerEditorGUI();
            dialog.intitialisieren();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
            dialog.registriereUIAktionen();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void intitialisieren()
    {

        _frame.setBounds(100, 100, 450, 320);
        _frame.setVisible(true);
        _frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        _frame.getContentPane().setLayout(new BorderLayout(0, 0));

        JPanel _hauptPanel = new JPanel();
        _frame.getContentPane().add(_hauptPanel, BorderLayout.CENTER);

        _hauptPanel.setLayout(new GridLayout(0, 1));

        JPanel _NamePanel = new JPanel();
        _hauptPanel.add(_NamePanel);
        _NamePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

        JLabel _nameLabel = new JLabel("  Vor- Nachname :");
        _NamePanel.add(_nameLabel);

        _textFieldVorname = new JTextField();
        _NamePanel.add(_textFieldVorname);
        _textFieldVorname.setColumns(15);

        _textFieldNachname = new JTextField();
        _NamePanel.add(_textFieldNachname);
        _textFieldNachname.setColumns(15);

        JPanel _HausUndZimmer = new JPanel();
        _hauptPanel.add(_HausUndZimmer);
        _HausUndZimmer.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

        JLabel _hausLabel = new JLabel("  Haus :");
        _HausUndZimmer.add(_hausLabel);

        _choiceHaus = new Choice();
        _choiceHaus.add("1");
        _choiceHaus.add("2");
        _HausUndZimmer.add(_choiceHaus);

        JLabel _etageLabel = new JLabel("  Etage :");
        _HausUndZimmer.add(_etageLabel);

        _choiceEtage = new Choice();
        _choiceEtage.add("0");
        _choiceEtage.add("1");
        _choiceEtage.add("2");
        _choiceEtage.add("3");
        _choiceEtage.add("4");
        _choiceEtage.add("5");
        _HausUndZimmer.add(_choiceEtage);

        JLabel _zimmerLabel = new JLabel("  Zimmer :");
        _HausUndZimmer.add(_zimmerLabel);

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
        _HausUndZimmer.add(_choiceZimmer);

        JPanel _EinzugdatumPanel = new JPanel();
        _hauptPanel.add(_EinzugdatumPanel);
        _EinzugdatumPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

        JLabel _einzugsLabel = new JLabel("  Einzugsmonat :");
        _EinzugdatumPanel.add(_einzugsLabel);

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
        _EinzugdatumPanel.add(_choiceMonat);

        _choiceJahr = new Choice();
        _choiceJahr.add("2018");
        _choiceJahr.add("2019");
        _choiceJahr.add("2020");
        _choiceJahr.add("2021");
        _choiceJahr.add("2022");
        _EinzugdatumPanel.add(_choiceJahr);

        JPanel _TelefonPanel = new JPanel();
        _hauptPanel.add(_TelefonPanel);
        _TelefonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

        JLabel _telefonLabel = new JLabel("  Telefon :");
        _TelefonPanel.add(_telefonLabel);

        _textFieldTelefon = new JTextField();
        _TelefonPanel.add(_textFieldTelefon);
        _textFieldTelefon.setColumns(30);

        JPanel _EmailPanel = new JPanel();
        _hauptPanel.add(_EmailPanel);
        _EmailPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

        JLabel _emailLabel = new JLabel("  E-Mail :   ");
        _EmailPanel.add(_emailLabel);

        _textFieldEmail = new JTextField();
        _EmailPanel.add(_textFieldEmail);
        _textFieldEmail.setColumns(30);

        JPanel _AuszugsdatumPanel = new JPanel();
        _hauptPanel.add(_AuszugsdatumPanel);
        _AuszugsdatumPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

        JLabel _auszugsLabel = new JLabel("  Auszugsmonat :");
        _AuszugsdatumPanel.add(_auszugsLabel);

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
        _AuszugsdatumPanel.add(_choiceMonatAus);

        _choiceJahrAus = new Choice();
        _choiceJahrAus.add("2018");
        _choiceJahrAus.add("2019");
        _choiceJahrAus.add("2020");
        _choiceJahrAus.add("2021");
        _choiceJahrAus.add("2022");
        _AuszugsdatumPanel.add(_choiceJahrAus);

        JPanel ButtonPanel = new JPanel();
        _frame.getContentPane().add(ButtonPanel, BorderLayout.SOUTH);

        ButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        _speichernButton = new JButton("Speichern");
        ButtonPanel.add(_speichernButton);

        AbbrechenButton = new JButton("Abbrechen");
        ButtonPanel.add(AbbrechenButton);

        _frame.revalidate();
    }

    public final void registriereUIAktionen()
    {
        AbbrechenButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                schliesseFenster();
            }
        });

        _speichernButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                benutzerSpeichern();
            }
        });
    }

    protected void benutzerSpeichern()
    {
        String zimmer = _choiceHaus.getSelectedItem() + "/" + _choiceEtage.getSelectedItem() + _choiceZimmer.getSelectedItem();
        String vorname = _textFieldVorname.getText();
        String name = _textFieldNachname.getText();
        int Guthaben = 12345;
        int EinMonat = Integer.parseInt(_choiceMonat.getSelectedItem());
        int EinJahr = Integer.parseInt(_choiceJahr.getSelectedItem());
        String Email = _textFieldEmail.getText();
        String Handynummer = _textFieldTelefon.getText();
        int AusMonat = Integer.parseInt(_choiceMonatAus.getSelectedItem());
        int AusJahr = Integer.parseInt(_choiceJahrAus.getSelectedItem());
        System.out.println(zimmer + ";" + vorname + ";" + name + ";" + Guthaben + ";" + EinMonat + ";" + EinJahr + ";" + Email + ";"
                + Handynummer + ";" + AusMonat + ";" + AusJahr);
        //        ProfilManagerService.createProfil(zimmer, vorname, name, Guthaben, EinMonat, EinJahr, Email, Handynummer,  AusMonat, AusJahr);

    }

    public static void schliesseFenster()
    {
        _frame.dispose();
        System.exit(1);
    }
}
