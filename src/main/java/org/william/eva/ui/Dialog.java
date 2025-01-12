package org.william.eva.ui;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.DefaultComboBoxModel;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import javax.swing.JPanel;

public class Dialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private static final int HEIGHT = 300;
	private static final int WIDTH = HEIGHT * 16 / 9;
	private String dialogTitle = "Preferences";
	
	private String[] languages = new String[] {"EN", "PT"};
	private String[] themes = new String[] {"Dark", "Light"};

	public Dialog(JFrame parent) {
		super(parent, true);
		initialize();
	}
	
	private void initialize() {
		this.setSize(WIDTH, HEIGHT);
		this.setModalityType(DEFAULT_MODALITY_TYPE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setTitle(dialogTitle);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblNewLabel = new JLabel("Language");
		panel.add(lblNewLabel);
		
		JComboBox comboBox = new JComboBox();
		panel.add(comboBox);
		comboBox.setModel(new DefaultComboBoxModel(languages));
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.CENTER);
		
		JLabel lblNewLabel_1 = new JLabel("Theme");
		panel_1.add(lblNewLabel_1);
		
		JComboBox comboBox_1 = new JComboBox();
		panel_1.add(comboBox_1);
		comboBox_1.setModel(new DefaultComboBoxModel(themes));
		
		this.setVisible(true);
	}

}
