package Werkzeuge.Guthaben;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Materialien.Profil;
import Werkzeuge.Startseite.Startseite;

public class GuthabenWerkzeugUI extends JInternalFrame
{
    private static JFrame _frame;

    public GuthabenWerkzeugUI(Profil profil)
    {
        _frame = new JFrame();
        intitialisieren(profil);
    }

    private static Font FontGuthaben = new Font("Tahoma", Font.PLAIN, 20);
    private JButton _AuszahlenButton;
    private JButton _EinzahlenButton;
    private JButton _OKButton;
    private JLabel _textGuthabenLabel;

    public void intitialisieren(Profil profil)
    {
        _frame.setTitle(profil.getName().toFormattedString());
        _frame.setBounds(625, 400, 400, 150);
        _frame.setVisible(true);
        _frame.setResizable(false);
        _frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
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

        JPanel hauptPanel = new JPanel();
        hauptPanel.setLayout(new BorderLayout(0, 0));

        JPanel textPanel = new JPanel();
        hauptPanel.add(textPanel, BorderLayout.NORTH);
        JLabel NurTextGuthabenLabel = new JLabel("Momentanes Guthaben von :");
        textPanel.add(NurTextGuthabenLabel);
        _frame.getContentPane().add(hauptPanel, BorderLayout.CENTER);

        JPanel GuthabenPanel = new JPanel();
        hauptPanel.add(GuthabenPanel, BorderLayout.CENTER);

        _textGuthabenLabel = new JLabel(profil.getMomentanesGuthaben().toFormattedString());
        _textGuthabenLabel.setFont(FontGuthaben);
        GuthabenPanel.add(_textGuthabenLabel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        _frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        _EinzahlenButton = new JButton("Einzahlen");
        buttonPanel.add(_EinzahlenButton);

        _AuszahlenButton = new JButton("Auszahlen");
        buttonPanel.add(_AuszahlenButton);

        _OKButton = new JButton("OK");
        buttonPanel.add(_OKButton);

        _frame.revalidate();
    }

    public JLabel get_textGuthabenLabel()
    {
        return _textGuthabenLabel;
    }

    public void set_textGuthabenLabel(String guthaben)
    {
        _textGuthabenLabel.setText(guthaben);
    }

    public JButton get_AuszahlenButton()
    {
        return _AuszahlenButton;
    }

    public JButton get_EinzahlenButton()
    {
        return _EinzahlenButton;
    }

    public JButton get_OKButton()
    {
        return _OKButton;
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