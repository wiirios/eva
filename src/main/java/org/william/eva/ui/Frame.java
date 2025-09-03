package org.william.eva.ui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.JTextPane;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.Box;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import org.william.eva.annotation.Incomplete;
import org.william.eva.input.KeyAction;
import org.william.eva.input.Undo;
import org.william.eva.io.Config;
import org.william.eva.io.file.FileManager;
import org.william.eva.util.Resources;

import javax.swing.JScrollPane;

public class Frame {
	private static final int HEIGHT = 600;
	private static final int WIDTH = HEIGHT * 16 / 9;
	
	private JFrame jFrame;
	private JFileChooser jFile;
	private Config config;
	private KeyAction btnAction;
	private Undo undo;
	private FileManager fileManager;
	private Resources resources;
	
	public JTextPane terminalPane;
	
	private static String typeFile;
	private boolean canSyntax;
	
	private static final Set<String> KEYWORDS = new HashSet<>(Set.of(
            "abstract", "continue", "for", "new", "switch",
            "assert", "default", "goto", "package", "synchronized",
            "boolean", "do", "if", "private", "this",
            "break", "double", "implements", "protected", "throw",
            "byte", "else", "import", "public", "throws",
            "case", "enum", "instanceof", "return", "transient",
            "catch", "extends", "int", "short", "try",
            "char", "final", "interface", "static", "void",
            "class", "finally", "long", "strictfp", "volatile",
            "const", "float", "native", "super", "while"
    ));
	
	/* later I make the variables have syntax highlight too */
	
	private static final Set<String> PRIMITIVE_DATA_TYPES = new HashSet<>(Set.of(
			"byte", "short", "int", "long", "float",
			"double", "char", "String", "boolean"		
	));
	
	public Frame() {
		initialize();
	}
	
	@Incomplete
	private void initialize() {
		jFile = new JFileChooser("c:");
		config = new Config("./src/main/resources/config.properties");

		FlatDarkLaf.setup();
		FlatLightLaf.setup();

		try {
			if (config.getProperties("getthemefromsystem").equals("on")) {
				if (config.getSystemTheme() == 0) {
					UIManager.setLookAndFeel(new FlatDarkLaf());				
				} else {
					UIManager.setLookAndFeel(new FlatLightLaf());
				}
			} else {
				if (config.getProperties("theme").equals("dark")) {
					UIManager.setLookAndFeel(new FlatDarkLaf());				
				} else {
					UIManager.setLookAndFeel(new FlatLightLaf());
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					resources = new Resources(Locale.getDefault().getLanguage(), Locale.getDefault().getCountry());
					
					jFrame = new JFrame();
					jFrame.setSize(WIDTH, HEIGHT);
					jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					jFrame.setLocationRelativeTo(null);
										
					JMenuBar menuBar = new JMenuBar();
					jFrame.setJMenuBar(menuBar);
					
					JPanel panel = new JPanel();
					jFrame.getContentPane().add(panel, BorderLayout.SOUTH);
					
					terminalPane = new JTextPane();
					terminalPane.setFont(new Font("Courier New", Font.PLAIN, 12));
					terminalPane.setEditable(false);
					boolean isDarkMode = UIManager.getLookAndFeel().getName().equals(FlatDarkLaf.NAME);

					terminalPane.setBackground(isDarkMode ? new Color(36, 37, 43) : new Color(249, 249, 249));
					terminalPane.setForeground(isDarkMode ? new Color(255, 255, 255) : new Color(36, 37, 43));
					
					JTextPane textPane = new JTextPane();
					textPane.setVisible(false);
					textPane.setFont(new Font("Consolas", Font.PLAIN, 14)); 
					
					// create undo
					undo = new Undo(textPane);
					
					GroupLayout gl_panel = new GroupLayout(panel);
					gl_panel.setHorizontalGroup(
						gl_panel.createParallelGroup(Alignment.LEADING)
							.addComponent(terminalPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)
					);
					gl_panel.setVerticalGroup(
						gl_panel.createParallelGroup(Alignment.LEADING)
							.addComponent(terminalPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
					);
					panel.setLayout(gl_panel);

					jFrame.getContentPane().add(textPane, BorderLayout.CENTER);
					
					JMenu fileMenu = new JMenu(resources.getText("file"));
					menuBar.add(fileMenu);
					
					JMenuItem openMenuItem = new JMenuItem(resources.getText("open"));
					JMenuItem saveMenuItem = new JMenuItem(resources.getText("save"));
					JMenuItem runMenuItem = new JMenuItem(resources.getText("run"));
					JMenuItem compileMenuItem = new JMenuItem(resources.getText("compile"));
					saveMenuItem.setEnabled(false);
					runMenuItem.setEnabled(false);				
					compileMenuItem.setEnabled(false);
					
					btnAction = new KeyAction(jFile, jFrame, textPane, terminalPane);
					
					fileMenu.add(openMenuItem);
					openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
					openMenuItem.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							if (btnAction.openDialog()) {
								saveMenuItem.setEnabled(true);
								runMenuItem.setEnabled(true);
								compileMenuItem.setEnabled(true);		
								
								/* file extension */
								typeFile = btnAction.jFileExtension();
								
								for (String i: btnAction.getExtensions()) {
									if (i.equals(typeFile)) {
										canSyntax = true;
									}
								}
							}
						}						
					});
					
					// save
					fileMenu.add(saveMenuItem);
					saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
					saveMenuItem.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							btnAction.saveDialog();
						}
					});
					
					JMenu projectMenu = new JMenu(resources.getText("project"));
					menuBar.add(projectMenu);
					
					projectMenu.add(runMenuItem);
					runMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK));
					runMenuItem.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							fileManager = new FileManager();
							try {
								if (config.getProperties("autosave").equals("on")) {
									try (BufferedWriter write = Files.newBufferedWriter(fileManager.getFilePath(jFile))) {
										write.write(textPane.getText(), 0, textPane.getText().length());
									} catch (Exception e2) {
									}
								}
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							btnAction.runProject();
						}						
					});
										
					projectMenu.add(compileMenuItem);
					compileMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, KeyEvent.CTRL_DOWN_MASK));
					compileMenuItem.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							btnAction.compileProject();
						}
						
					});
					
					JMenu windowMenu = new JMenu(resources.getText("window"));
					menuBar.add(windowMenu);
					
					JMenuItem preferencesMenuItem = new JMenuItem(resources.getText("preferences"));
					preferencesMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK | KeyEvent.ALT_DOWN_MASK));
					preferencesMenuItem.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							new Dialog(jFrame);					
						}
						
					});
					windowMenu.add(preferencesMenuItem);		
					
					JMenu helpMenu = new JMenu(resources.getText("help"));
					menuBar.add(helpMenu);
					
					JMenuItem githubMenuItem = new JMenuItem(resources.getText("githubpage"));
					githubMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, KeyEvent.CTRL_DOWN_MASK | KeyEvent.ALT_DOWN_MASK));
					githubMenuItem.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							try {
								Desktop.getDesktop().browse(new URI("https://github.com/wiirios/eva"));
							} catch (IOException | URISyntaxException e1) {
								e1.printStackTrace();
							}		
						}
					});					
					helpMenu.add(githubMenuItem);
									
					textPane.addKeyListener(new KeyListener() {
						char lastChar;
										
						@Override
						public void keyTyped(KeyEvent e) {
							lastChar = e.getKeyChar();							
						}

						@Override
						public void keyPressed(KeyEvent e) {
							StyledDocument docStyled = textPane.getStyledDocument();
							Document doc = textPane.getDocument();
							int docLength = doc.getLength();
					        
					        if (canSyntax) {
					        	try {
						            String text = doc.getText(0, docLength);
						            int caretPosition = textPane.getCaretPosition();

						            if (lastChar == '{' && e.getKeyCode() == KeyEvent.VK_ENTER) {
						                doc.insertString(caretPosition, " \n}", null);
						            } 
						            else if (lastChar == '(') {
						                doc.insertString(caretPosition, ")", null);
						            }
						            
						        	/* yeh yeh i know this code is a bullshit */
									/* but works */
									/* i'll make it better later */
						            
						            for (String i : KEYWORDS) {
						                int index = 0;
						                boolean start = false;
						                boolean end = false;
						                					                		
						                while ((index = text.indexOf(i, index)) >= 0) {
						                    if (index == 0 || !Character.isLetterOrDigit(text.charAt(index - 1))) start = true;					                    
						                    if (index + i.length() == text.length() || !Character.isLetterOrDigit(text.charAt(index + i.length()))) end = true;
						                    
						                    if (start && end) {              
						                        Style style = docStyled.addStyle("keywordStyle", null);  
						                        StyleConstants.setForeground(style, Color.BLUE);
						                        StyleConstants.setBold(style, true);

						                        docStyled.setCharacterAttributes(index, i.length(), style, false);						                
						                    }

						                    index += i.length();
						                }
						            }

						        } catch (BadLocationException e1) {
						            e1.printStackTrace();
						        }
					        }					        

							/* ignore */
							// System.out.println(doc.getDefaultRootElement().getElementCount());
							// System.out.println(doc.getDefaultRootElement().getElementIndex(textPane.getCaretPosition()));
						}

						@Override
						public void keyReleased(KeyEvent e) {}
					});
																				
					JPanel panel_1 = new JPanel();
					jFrame.getContentPane().add(panel_1, BorderLayout.WEST);
					panel_1.setLayout(new BorderLayout(0, 0));
					
					JPanel panel_2 = new JPanel();
					panel_1.add(panel_2, BorderLayout.SOUTH);
					panel_2.setLayout(new BorderLayout(0, 0));
					
					JScrollPane jScrollPane = new JScrollPane(textPane);
					jFrame.getContentPane().add(jScrollPane);
					
					Component horizontalStrut = Box.createHorizontalStrut(55);
					jScrollPane.setRowHeaderView(horizontalStrut);
					
					jFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
