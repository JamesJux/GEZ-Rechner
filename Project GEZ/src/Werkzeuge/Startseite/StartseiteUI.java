package Werkzeuge.Startseite;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class StartseiteUI extends JInternalFrame
{
    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                try
                {
                    StartseiteUI frame = new StartseiteUI();
                    frame.setVisible(true);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    private static final String TITEL = "Startseite - GEZ-Rechner";

    private static JFrame _frame;
    private Choice _bewohnerChoice;
    private JButton _bewohnerBearbeitenButton;
    private JButton _guthabenButton;
    private JButton _uebersichtButton;

    public StartseiteUI()
    {
        _frame = Startseite._frame;
        intitialisieren();
    }

    public void intitialisieren()
    {
        _frame.setTitle(TITEL);
        _frame.setBounds(600, 400, 450, 150);
        _frame.setVisible(true);
        _frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        _frame.getContentPane().setLayout(new BorderLayout(0, 0));

        JPanel AuswahlBewohnerPanel = new JPanel();
        _frame.getContentPane().add(AuswahlBewohnerPanel, BorderLayout.CENTER);
        AuswahlBewohnerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        JLabel lblNewLabel = new JLabel(" Bewohner: ");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        AuswahlBewohnerPanel.add(lblNewLabel);

        _bewohnerChoice = new Choice();
        _bewohnerChoice.setFont(new Font("Tahoma", Font.PLAIN, 20));
        AuswahlBewohnerPanel.add(_bewohnerChoice);

        JPanel ButtonPanel = new JPanel();
        _frame.getContentPane().add(ButtonPanel, BorderLayout.SOUTH);

        _uebersichtButton = new JButton("Übersicht");
        ButtonPanel.add(_uebersichtButton);

        JLabel AbstandsLabel = new JLabel("abst");
        AbstandsLabel.setForeground(SystemColor.control);
        ButtonPanel.add(AbstandsLabel);

        _guthabenButton = new JButton("Guthaben anzeigen");
        ButtonPanel.add(_guthabenButton);

        _bewohnerBearbeitenButton = new JButton("Bewohner bearbeiten");
        ButtonPanel.add(_bewohnerBearbeitenButton);
    }

    public Choice get_bewohnerChoice()
    {
        return _bewohnerChoice;
    }

    public JButton get_bewohnerBearbeitenButton()
    {
        return _bewohnerBearbeitenButton;
    }

    public JButton get_guthabenButton()
    {
        return _guthabenButton;
    }

    public JButton get_uebersichtButton()
    {
        return _uebersichtButton;
    }

    public void set_bewohnerBearbeitenKlickbarButton(Boolean klickbar)
    {
        _bewohnerBearbeitenButton.setEnabled(klickbar);
    }

    public void set_guthabenKlickbarButton(Boolean klickbar)
    {
        _guthabenButton.setEnabled(klickbar);
    }

    public void set_neuerBewohnerSelected()
    {
        this._guthabenButton = null;
    }
}
