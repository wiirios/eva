package org.william.eva.ui;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.BoxLayout;

import javax.swing.JTextField;

import org.william.eva.annotation.Incomplete;

public class Dialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private static final int HEIGHT = 300;
	private static final int WIDTH = HEIGHT * 16 / 9;
	
	private String[] languages = new String[] {"EN", "PT"};
	private String[] themes = new String[] {"Dark", "Light"};
	private String[] charset = new String[] {"ISO-8859-1", "US-ASCII", "UTF-8", "UTF-16BE", "UTF-16LE", "UTF-16", "UTF-32BE", "UTF-32LE", "UTF-32"};
	
	public Dialog(JFrame parent) {
		super(parent, true);
		initialize();
	}		
	
	@Incomplete()
	private void initialize() {
		this.setSize(WIDTH, HEIGHT);
		this.setModalityType(DEFAULT_MODALITY_TYPE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setTitle("Preferences");
		getContentPane().setLayout(new BorderLayout(0, 0));

		JList list = new JList();
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {"General", "Language"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		getContentPane().add(list, BorderLayout.WEST);
		
		/** general panel */
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
		/* theme opt */
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(5);
		panel_1.add(horizontalStrut_2);
		
		JLabel lblNewLabel = new JLabel("Theme");
		panel_1.add(lblNewLabel);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		panel_1.add(horizontalStrut);
		
		JComboBox comboBox = new JComboBox(themes);
		panel_1.add(comboBox);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		panel_1.add(horizontalStrut_1);
		
		/* -- */
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_3 = new JPanel();
		panel_2.add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.X_AXIS));
		
		/* charset opt */
		
		Component horizontalStrut_3 = Box.createHorizontalStrut(5);
		panel_3.add(horizontalStrut_3);
		
		JLabel lblNewLabel_1 = new JLabel("Charset");
		panel_3.add(lblNewLabel_1);
		
		Component horizontalStrut_4 = Box.createHorizontalStrut(16);
		panel_3.add(horizontalStrut_4);
		
		JComboBox comboBox_1 = new JComboBox(charset);
		panel_3.add(comboBox_1);
		
		Component horizontalStrut_5 = Box.createHorizontalStrut(20);
		panel_3.add(horizontalStrut_5);
		
		Component verticalStrut_1 = Box.createVerticalStrut(10);
		panel_2.add(verticalStrut_1, BorderLayout.NORTH);
		
		JPanel panel_4 = new JPanel();
		panel_2.add(panel_4, BorderLayout.SOUTH);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_5 = new JPanel();
		panel_4.add(panel_5, BorderLayout.CENTER);
		panel_5.setLayout(new BoxLayout(panel_5, BoxLayout.X_AXIS));
		
		Component horizontalStrut_8 = Box.createHorizontalStrut(5);
		panel_5.add(horizontalStrut_8);
		
		JLabel lblNewLabel_2 = new JLabel("Editor save interval (in minutes)");
		panel_5.add(lblNewLabel_2);
		
		Component horizontalStrut_6 = Box.createHorizontalStrut(20);
		panel_5.add(horizontalStrut_6);
		
		JTextField textField = new JTextField("10");
		panel_5.add(textField);
		textField.setColumns(10);
		
		Component horizontalStrut_7 = Box.createHorizontalStrut(20);
		panel_5.add(horizontalStrut_7);
		
		Component verticalStrut_2 = Box.createVerticalStrut(10);
		panel_4.add(verticalStrut_2, BorderLayout.NORTH);
		
		Component verticalStrut = Box.createVerticalStrut(160);
		panel_4.add(verticalStrut, BorderLayout.SOUTH);
		
		/* -- */
		
		this.setVisible(true);
	}
}
