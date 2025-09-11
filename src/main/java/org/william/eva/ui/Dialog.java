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
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Locale;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.commons.configuration.ConfigurationException;
import org.william.eva.annotation.Incomplete;
import org.william.eva.io.Config;
import org.william.eva.util.Resources;

import javax.swing.JButton;

public class Dialog extends JDialog {
    private static final long serialVersionUID = 1L;
    private static final int HEIGHT = 300;
    private static final int WIDTH = HEIGHT * 16 / 9;

    private boolean setSelected = false;

    private String[] languages = {"EN", "PT"};
    private String[] themes = {"Dark", "Light"};
    private String[] charset = {"ISO-8859-1", "US-ASCII", "UTF-8", "UTF-16BE", "UTF-16LE", "UTF-16", "UTF-32BE", "UTF-32LE", "UTF-32"};
    private String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
    
    private Resources resources;
    private Config config;
    
    private CardLayout cardLayout;
    private JPanel container;

    public Dialog(JFrame parent) {
        super(parent, true);                
        initialize();
    }

    @Incomplete
    private void initialize() {
    	resources = new Resources(Locale.getDefault().getLanguage(), Locale.getDefault().getCountry());    
    	config = new Config("./src/main/resources/config.properties");
    	
        this.setSize(WIDTH, HEIGHT);
        this.setModalityType(DEFAULT_MODALITY_TYPE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle(resources.getText("preferences"));    
        getContentPane().setLayout(new BorderLayout(0, 0));
          
        JList<String> list = new JList<>(new String[]{resources.getText("general"), resources.getText("language")});
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        getContentPane().add(new JScrollPane(list), BorderLayout.WEST);

        cardLayout = new CardLayout();
        container = new JPanel(cardLayout);
        getContentPane().add(container, BorderLayout.CENTER);

        JPanel panelGeneral = new JPanel();
        panelGeneral.setLayout(new BoxLayout(panelGeneral, BoxLayout.Y_AXIS));

        JPanel themePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        themePanel.add(new JLabel(resources.getText("theme")));
        
        Dimension size = new Dimension(WIDTH, 30);
        
        JComboBox<String> jComboBoxThemes = new JComboBox<>(themes);
        themePanel.add(jComboBoxThemes);
        themePanel.setPreferredSize(size);
        themePanel.setMaximumSize(themePanel.getPreferredSize()); 
        themePanel.setMinimumSize(themePanel.getPreferredSize());
        panelGeneral.add(themePanel);
        
        JPanel charsetPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        charsetPanel.add(new JLabel("Charset"));
        charsetPanel.setPreferredSize(size);
        charsetPanel.setMaximumSize(charsetPanel.getPreferredSize()); 
        charsetPanel.setMinimumSize(charsetPanel.getPreferredSize());
        
        JComboBox<String> charsetBox = new JComboBox<>(charset);
        charsetPanel.add(charsetBox);
        panelGeneral.add(charsetPanel);
        
        JCheckBox jCheckBox = new JCheckBox();
        jCheckBox.setSelected(true);     
           
        JPanel saveIntervalPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        saveIntervalPanel.add(new JLabel(resources.getText("autosave")));
        saveIntervalPanel.setPreferredSize(size);
        saveIntervalPanel.setMaximumSize(saveIntervalPanel.getPreferredSize());
        saveIntervalPanel.setMinimumSize(saveIntervalPanel.getPreferredSize());
        saveIntervalPanel.add(jCheckBox);
        
        panelGeneral.add(saveIntervalPanel);
        
        try {
			if (config.getProperties("getthemefromsystem").equals("true")) setSelected = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        JCheckBox jCheckBoxSystemTheme = new JCheckBox();        

        if (setSelected) jCheckBoxSystemTheme.setSelected(true);
        else jCheckBoxSystemTheme.setSelected(false);
        
        JPanel getThemeSystemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        getThemeSystemPanel.add(new JLabel(resources.getText("getthemefromsystem")));
        getThemeSystemPanel.setPreferredSize(size);
        getThemeSystemPanel.setMaximumSize(getThemeSystemPanel.getPreferredSize());
        getThemeSystemPanel.setMinimumSize(getThemeSystemPanel.getPreferredSize());
        getThemeSystemPanel.add(jCheckBoxSystemTheme);
        
        panelGeneral.add(getThemeSystemPanel);

        JPanel intervalPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        intervalPanel.add(new JLabel(resources.getText("editorsave")));
        intervalPanel.setPreferredSize(size);
        intervalPanel.setMaximumSize(intervalPanel.getPreferredSize()); 
        intervalPanel.setMinimumSize(intervalPanel.getPreferredSize());
        
        JTextField saveIntervalField = new JTextField("10", 10);
        intervalPanel.add(saveIntervalField);        
        panelGeneral.add(intervalPanel);
        
        JPanel fontsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fontsPanel.add(new JLabel(resources.getText("font")));
        
        JComboBox<String> jComboBoxFonts = new JComboBox<String>(fonts);
        fontsPanel.add(jComboBoxFonts);
        fontsPanel.setPreferredSize(size);
        fontsPanel.setMaximumSize(fontsPanel.getPreferredSize()); 
        fontsPanel.setMinimumSize(fontsPanel.getPreferredSize());
        panelGeneral.add(fontsPanel);
        
        JPanel savePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton jButtonSave = new JButton(resources.getText("save"));
        savePanel.add(jButtonSave);
        savePanel.setPreferredSize(size);
        savePanel.setMaximumSize(savePanel.getPreferredSize()); 
        savePanel.setMinimumSize(savePanel.getPreferredSize());
        
        panelGeneral.add(savePanel);

        JPanel panelLanguage = new JPanel();
        panelLanguage.add(new JLabel(resources.getText("language")));
        panelLanguage.add(new JComboBox<>(languages));

        container.add(panelGeneral, resources.getText("general"));
        container.add(panelLanguage, resources.getText("language"));

        list.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                String selected = list.getSelectedValue();
                cardLayout.show(container, selected);
            }
        });
        
        jButtonSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					config.rewriteProperties("theme", jComboBoxThemes.getSelectedItem().toString());
					config.rewriteProperties("charset", charsetBox.getSelectedItem().toString());
					config.rewriteProperties("autosave", String.valueOf(jCheckBox.isSelected()));
					config.rewriteProperties("getthemefromsystem", String.valueOf(jCheckBoxSystemTheme.isSelected()));
					config.rewriteProperties("autosaveinterval", saveIntervalField.getText());
					config.rewriteProperties("editorfont", jComboBoxFonts.getSelectedItem().toString());
				} catch (ConfigurationException | IOException e1) {
					e1.printStackTrace();					
				}
			}
        });

        this.setVisible(true);
    }
}
