package org.william.eva.ui;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JList;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;

import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.william.eva.annotation.Incomplete;

public class Dialog extends JDialog {
    private static final long serialVersionUID = 1L;
    private static final int HEIGHT = 300;
    private static final int WIDTH = HEIGHT * 16 / 9;

    private String[] languages = new String[]{"EN", "PT"};
    private String[] themes = new String[]{"Dark", "Light"};
    private String[] charset = new String[]{"ISO-8859-1", "US-ASCII", "UTF-8", "UTF-16BE", "UTF-16LE", "UTF-16", "UTF-32BE", "UTF-32LE", "UTF-32"};

    private CardLayout cardLayout;
    private JPanel container;

    public Dialog(JFrame parent) {
        super(parent, true);
        initialize();
    }

    @Incomplete
    private void initialize() {
        this.setSize(WIDTH, HEIGHT);
        this.setModalityType(DEFAULT_MODALITY_TYPE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("Preferences");
        getContentPane().setLayout(new BorderLayout(0, 0));
        
        JList<String> list = new JList<>(new String[]{"General", "Language"});
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        getContentPane().add(new JScrollPane(list), BorderLayout.WEST);

        cardLayout = new CardLayout();
        container = new JPanel(cardLayout);
        getContentPane().add(container, BorderLayout.CENTER);

        JPanel panelGeneral = new JPanel();
        panelGeneral.setLayout(new BoxLayout(panelGeneral, BoxLayout.Y_AXIS));

        JPanel themePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        themePanel.add(new JLabel("Theme"));
        
        Dimension size = new Dimension(WIDTH, 30);
        
        themePanel.add(new JComboBox<>(themes));
        themePanel.setPreferredSize(size);
        themePanel.setMaximumSize(themePanel.getPreferredSize()); 
        themePanel.setMinimumSize(themePanel.getPreferredSize());
        panelGeneral.add(themePanel);
        
        JPanel charsetPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        charsetPanel.add(new JLabel("Charset"));
        charsetPanel.setPreferredSize(size);
        charsetPanel.setMaximumSize(charsetPanel.getPreferredSize()); 
        charsetPanel.setMinimumSize(charsetPanel.getPreferredSize());
        charsetPanel.add(new JComboBox<>(charset));
        
        panelGeneral.add(charsetPanel);

        JPanel intervalPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        intervalPanel.add(new JLabel("Editor save interval (in minutes)"));
        intervalPanel.setPreferredSize(size);
        intervalPanel.setMaximumSize(intervalPanel.getPreferredSize()); 
        intervalPanel.setMinimumSize(intervalPanel.getPreferredSize());
        intervalPanel.add(new JTextField("10", 10));
        
        panelGeneral.add(intervalPanel);

        JPanel panelLanguage = new JPanel();
        panelLanguage.add(new JLabel("Language"));
        panelLanguage.add(new JComboBox<>(languages));

        container.add(panelGeneral, "General");
        container.add(panelLanguage, "Language");

        list.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                String selected = list.getSelectedValue();
                cardLayout.show(container, selected);
            }
        });

        this.setVisible(true);
    }
}
