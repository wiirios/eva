package org.william.eva.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.formdev.flatlaf.FlatDarkLaf;

public class TabManager {
    private JTabbedPane tabbedPane;
    private Map<String, TabInfo> openTabs;
    private JPanel containerPanel;
    
    private static class TabInfo {
        JTextPane textPane;
        JScrollPane scrollPane;
        String filePath;
        boolean isModified;
        
        TabInfo(JTextPane textPane, JScrollPane scrollPane, String filePath) {
            this.textPane = textPane;
            this.scrollPane = scrollPane;
            this.filePath = filePath;
            this.isModified = false;
        }
    }
    
    public TabManager(JPanel containerPanel) {
        this.containerPanel = containerPanel;
        this.openTabs = new HashMap<>();
        this.tabbedPane = new JTabbedPane();
        this.tabbedPane.setVisible(false);
    }
    
    public JTextPane openTab(String fileName, String filePath, String fileContent) {
        if (openTabs.containsKey(filePath)) {
            TabInfo tabInfo = openTabs.get(filePath);
            int index = tabbedPane.indexOfComponent(tabInfo.scrollPane);
            if (index != -1) tabbedPane.setSelectedIndex(index);
            
            return tabInfo.textPane;
        }
        
        JTextPane textPane = createTextPane();
        textPane.setText(fileContent);
        JScrollPane scrollPane = new JScrollPane(textPane);
        TabInfo tabInfo = new TabInfo(textPane, scrollPane, filePath);
        openTabs.put(filePath, tabInfo);
        
        textPane.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                markAsModified(filePath);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                markAsModified(filePath);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                markAsModified(filePath);
            }
        });
        
        tabbedPane.addTab(fileName, scrollPane);
        
        if (!tabbedPane.isVisible()) {
            containerPanel.removeAll();
            containerPanel.setLayout(new BorderLayout());
            containerPanel.add(tabbedPane, BorderLayout.CENTER);
            tabbedPane.setVisible(true);
            containerPanel.revalidate();
            containerPanel.repaint();
        }
        
        tabbedPane.setSelectedComponent(scrollPane);
        
        return textPane;
    }
    
    public void closeCurrentTab() {
        int selectedIndex = tabbedPane.getSelectedIndex();
        if (selectedIndex != -1) closeTab(selectedIndex);
    }
    
    public void closeTab(int index) {
        if (index >= 0 && index < tabbedPane.getTabCount()) {
            JScrollPane scrollPane = (JScrollPane) tabbedPane.getComponentAt(index);
            String pathToRemove = null;
            
            for (Map.Entry<String, TabInfo> entry : openTabs.entrySet()) {
                if (entry.getValue().scrollPane == scrollPane) {
                    pathToRemove = entry.getKey();
                    break;
                }
            }
            
            if (pathToRemove != null) openTabs.remove(pathToRemove);
            
            tabbedPane.removeTabAt(index);
            
            if (tabbedPane.getTabCount() == 0) tabbedPane.setVisible(false);
        }
    }
    
    public JTextPane getCurrentTextPane() {
        int selectedIndex = tabbedPane.getSelectedIndex();
        if (selectedIndex == -1) return null;
        
        JScrollPane scrollPane = (JScrollPane) tabbedPane.getComponentAt(selectedIndex);
        return (JTextPane) scrollPane.getViewport().getView();
    }
    
    public String getCurrentFilePath() {
        JTextPane currentPane = getCurrentTextPane();
        if (currentPane == null) return null;
        
        for (TabInfo tabInfo : openTabs.values()) {
            if (tabInfo.textPane == currentPane) return tabInfo.filePath;
        }
        
        return null;
    }
    
    private void markAsModified(String filePath) {
        TabInfo tabInfo = openTabs.get(filePath);
        if (tabInfo != null && !tabInfo.isModified) {
            tabInfo.isModified = true;
            int index = tabbedPane.indexOfComponent(tabInfo.scrollPane);
            if (index != -1) {
                String currentTitle = tabbedPane.getTitleAt(index);
                if (!currentTitle.startsWith("*")) tabbedPane.setTitleAt(index, "*" + currentTitle);
            }
        }
    }
    
    public void markAsSaved(String filePath) {
        TabInfo tabInfo = openTabs.get(filePath);
        if (tabInfo != null && tabInfo.isModified) {
            tabInfo.isModified = false;
            int index = tabbedPane.indexOfComponent(tabInfo.scrollPane);
            if (index != -1) {
                String currentTitle = tabbedPane.getTitleAt(index);
                if (currentTitle.startsWith("*")) {
                    tabbedPane.setTitleAt(index, currentTitle.substring(1));
                }
            }
        }
    }
    
    private JTextPane createTextPane() {
        JTextPane textPane = new JTextPane();
        textPane.setFont(new Font("Consolas", Font.PLAIN, 14));
        
        boolean isDarkMode = UIManager.getLookAndFeel().getName().equals(FlatDarkLaf.NAME);
        textPane.setBackground(isDarkMode ? new Color(30, 31, 34) : Color.WHITE);
        textPane.setForeground(isDarkMode ? new Color(169, 183, 198) : Color.BLACK);
        textPane.setCaretColor(isDarkMode ? Color.WHITE : Color.BLACK);
        
        return textPane;
    }
    
    public int getTabCount() {
        return tabbedPane.getTabCount();
    }
    
    public boolean isFileOpen(String filePath) {
        return openTabs.containsKey(filePath);
    }
    
    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }
}
