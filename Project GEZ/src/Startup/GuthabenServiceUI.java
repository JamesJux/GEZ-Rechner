package Startup;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Material.Profil;

public class GuthabenServiceUI extends JFrame
{
    public GuthabenServiceUI(Profil profil)
    {
        intitialisieren(profil);
    }

    private JPanel contentPanel;
    private static Font FontGuthaben = new Font("Tahoma", Font.PLAIN, 20);
    private JButton _AuszahlenButton;
    private JButton _EinzahlenButton;
    private JButton _OKButton;
    private JPanel buttonPanel;
    private JPanel hauptPanel;
    private JPanel textPanel;
    private JPanel GuthabenPanel;
    private JLabel NurTextGuthabenLabel;
    private JLabel _textGuthabenLabel;

    public void intitialisieren(Profil profil)
    {
        this.setTitle(profil.getName().toFormattedString());
        setBounds(100, 100, 300, 150);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPanel = new JPanel();
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setLayout(new FlowLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        getContentPane().add(contentPanel, BorderLayout.CENTER);
        {
            hauptPanel = new JPanel();
            hauptPanel.setLayout(new BorderLayout(0, 0));

            textPanel = new JPanel();
            hauptPanel.add(textPanel, BorderLayout.NORTH);
            NurTextGuthabenLabel = new JLabel("Momentanes Guthaben von :");
            textPanel.add(NurTextGuthabenLabel);
            getContentPane().add(hauptPanel, BorderLayout.CENTER);

            GuthabenPanel = new JPanel();
            hauptPanel.add(GuthabenPanel, BorderLayout.CENTER);

            _textGuthabenLabel = new JLabel(profil.getMomentanesGuthaben().toFormattedString());
            _textGuthabenLabel.setFont(FontGuthaben);
            GuthabenPanel.add(_textGuthabenLabel);

            buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
            getContentPane().add(buttonPanel, BorderLayout.SOUTH);
            _EinzahlenButton = new JButton("Einzahlen");
            buttonPanel.add(_EinzahlenButton);

            _AuszahlenButton = new JButton("Auszahlen");
            buttonPanel.add(_AuszahlenButton);

            _OKButton = new JButton("OK");
            buttonPanel.add(_OKButton);

        }
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

    public static void schliesseFenster()
    {
        System.exit(1);
    }

}
