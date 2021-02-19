package Werkzeuge.ProfilManager;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

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
    /**
     * 
     */
    private static final long serialVersionUID = 6640475293587633567L;
    private static final String TITEL = "Bewohner - Editor";
    private static JFrame _frame;
    private JButton _infoButton;
    JTextField _textFieldVorname;
    JTextField _textFieldNachname;
    JTextField _textFieldZimmer;
    JSpinner _spinnerEinzug;
    JTextField _textFieldTelefon;
    JTextField _textFieldEmail;
    JCheckBox _AuszugCheckbox;
    JSpinner _spinnerAuszug;
    JButton _speichernButton;
    JButton _abbrechenButton;
    JButton _bewohnerLöschenButton;

    public ProfilWerkzeugUI()
    {
        intitialisieren(null, null);
    }

    public ProfilWerkzeugUI(Profil profil)
    {
        Date Einzug = Date.from(profil.getEinzugsdatum().toZonedDateTime().toInstant());
        Date Auszug = Date.from(profil.getAuszugsdatum().toZonedDateTime().toInstant());
        intitialisieren(Einzug, Auszug);
        _textFieldVorname.setText(profil.getVorname());
        _textFieldNachname.setText(profil.getNachname());
        _textFieldTelefon.setText(profil.getHandynummer());
        _textFieldEmail.setText(profil.getEmail());
        _AuszugCheckbox.setSelected(Auszug.before(new Date()));
        _spinnerAuszug.setEnabled(Auszug.before(new Date()));
    }

    public void intitialisieren(Date einzug, Date auszug)
    {
        _frame = new JFrame(TITEL);
        _frame.setLocation(600, 400);
        _frame.setSize(430, 250);
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
        Calendar calendar = Calendar.getInstance();

        JPanel NamePanel = new JPanel();
        HauptPanel.add(NamePanel);
        NamePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        JLabel NameLabel = new JLabel("  Vor- Nachname :");
        NamePanel.add(NameLabel);
        _textFieldVorname = new JTextField();
        _textFieldVorname.setColumns(12);
        NamePanel.add(_textFieldVorname);
        _textFieldNachname = new JTextField();
        _textFieldNachname.setColumns(13);
        NamePanel.add(_textFieldNachname);

        JPanel EinzugdatumPanel = new JPanel();
        HauptPanel.add(EinzugdatumPanel);
        EinzugdatumPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        JLabel EinzugsLabel = new JLabel("  Einzugsdatum :");
        EinzugdatumPanel.add(EinzugsLabel);

        if (einzug == null)
        {
            einzug = calendar.getTime();
        }
        _spinnerEinzug = new JSpinner(new SpinnerDateModel(einzug, null, null, Calendar.YEAR));
        EinzugsLabel.setLabelFor(_spinnerEinzug);
        EinzugdatumPanel.add(_spinnerEinzug);

        _spinnerEinzug.setEditor(new JSpinner.DateEditor(_spinnerEinzug, "MM/yyyy"));
        JFormattedTextField ftf1 = null;
        ftf1 = getTextField(_spinnerEinzug);
        ftf1.setColumns(7);
        ftf1.setHorizontalAlignment(JTextField.RIGHT);

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
        JLabel _auszugsLabel = new JLabel("  Auszugsdatum :");
        AuszugsdatumPanel.add(_auszugsLabel);
        _AuszugCheckbox = new JCheckBox("", false);
        AuszugsdatumPanel.add(_AuszugCheckbox);

        if (auszug == null)
        {
            auszug = new Date(new GregorianCalendar(2099, 11, 30).toInstant().toEpochMilli());
        }
        _spinnerAuszug = new JSpinner(new SpinnerDateModel(auszug, null, null, Calendar.YEAR));
        _auszugsLabel.setLabelFor(_spinnerAuszug);
        _spinnerAuszug.setEnabled(false);
        AuszugsdatumPanel.add(_spinnerAuszug);

        _spinnerAuszug.setEditor(new JSpinner.DateEditor(_spinnerAuszug, "MM/yyyy"));
        JFormattedTextField ftf2 = null;
        ftf2 = getTextField(_spinnerAuszug);
        ftf2.setColumns(7);
        ftf2.setHorizontalAlignment(JTextField.RIGHT);

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

    /**
     * Return the formatted text field used by the editor, or
     * null if the editor doesn't descend from JSpinner.DefaultEditor.
     */
    public JFormattedTextField getTextField(JSpinner spinner)
    {
        JComponent editor = spinner.getEditor();
        if (editor instanceof JSpinner.DefaultEditor)
        {
            return ((JSpinner.DefaultEditor) editor).getTextField();
        }
        else
        {
            System.err.println("Unexpected editor type: "
                    + spinner.getEditor().getClass()
                    + " isn't a descendant of DefaultEditor");
            return null;
        }
    }

    public JTextField get_textFieldVorname()
    {
        return _textFieldVorname;
    }

    public JTextField get_textFieldNachname()
    {
        return _textFieldNachname;
    }

    public JSpinner get_spinnerEinzug()
    {
        return _spinnerEinzug;
    }

    public JTextField get_textFieldTelefon()
    {
        return _textFieldTelefon;
    }

    public JTextField get_textFieldEmail()
    {
        return _textFieldEmail;
    }

    public JSpinner get_spinnerAuszug()
    {
        return _spinnerAuszug;
    }

    public JCheckBox get_AuszugCheckbox()
    {
        return _AuszugCheckbox;
    }

    public JTextField get_textFieldZimmer()
    {
        return _textFieldZimmer;
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