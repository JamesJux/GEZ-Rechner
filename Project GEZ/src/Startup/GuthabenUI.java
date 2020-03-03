package Startup;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Material.Profil;

public class GuthabenUI extends JFrame
{

    private JPanel contentPanel;
    Profil testProfil = new Profil("1/515", "Dominick", "Labatz", 12345, "test Email", "4120", 10, 2018, 01, 2099);
    private static Font FontGuthaben = new Font("Tahoma", Font.PLAIN, 20);
    private JButton _AuszahlenButton;
    private JButton _EinzahlenButton;
    private JButton _OKButton;
    private JPanel buttonPanel;
    private JPanel hauptPanel;
    /**
     * @wbp.nonvisual location=380,99
     */
    private final JPanel panel = new JPanel();
    private JPanel panel_2;
    private JPanel textPanel;
    private JPanel GuthabenPanel;
    private JLabel NurTextGuthabenLabel;
    private JLabel _textGuthabenLabel;

    /**
     * Launch the application.
     */
    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                try
                {
                    GuthabenUI frame = new GuthabenUI();
                    frame.setVisible(true);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public GuthabenUI()
    {
        intitialisieren();
        registriereUIAktionen();
    }

    public void intitialisieren()
    {
        this.setTitle(testProfil.getName().toFormattedString());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPanel = new JPanel();
        setBounds(100, 100, 300, 150);
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

            _textGuthabenLabel = new JLabel(testProfil.getGuthaben().toFormattedString());
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

    private void registriereUIAktionen()
    {
        _EinzahlenButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("Einzahlen");
            }
        });

        _AuszahlenButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("Auszahlen");
            }
        });

        _OKButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("ok");
            }
        });
    }

    public static void schliesseFenster()
    {
        System.exit(1);
    }

}
