package com.konst_gall.noteLib;

import javax.print.DocFlavor;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.function.Consumer;

/**
 * Responsible for managing the list of all active files
 */
public class FileList {
    JScrollPane list;
    String[] fileNameList;
    private JPanel listPanel = new JPanel();
    JButton activeButton = null; //stores the button that is currently active

    private Consumer<String> fileSelectedListener;
    public void setFileSelectedListener(Consumer<String> listener) {
        this.fileSelectedListener = listener;
    }

    public FileList() {
        fileNameList = ButtonAction.fileInteractor.getAllFiles();
        list = new JScrollPane(listPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        list.setBackground(Color.decode(GUI.BACKGROUNDCOLOUR));
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(Color.decode(GUI.BACKGROUNDCOLOUR));

        if(fileNameList == null || fileNameList.length == 0) {
            return;
        }

        for(int i = 0; i < fileNameList.length; i++) { //adds the buttons into the list
            JButton activeButton = new JButton(fileNameList[i]);
            activeButton.setBackground(Color.decode(GUI.BUTTONCOLOUR));
            addMouseHighLight(activeButton);
            activeButton.setPreferredSize(new Dimension(100, 25));
            activeButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));

            activeButton.addActionListener(e -> {
                selectButton(activeButton);
                ButtonAction.filePressed(activeButton.getText());
                if (fileSelectedListener != null) {          // <-- notify the GUI
                    fileSelectedListener.accept(activeButton.getText());
                }
            });

            listPanel.add(activeButton);
        }
    }

    private void selectButton(JButton buttonSelected) {
        buttonSelected.setBackground(Color.decode(GUI.ACTIVEBUTTONCOLOUR));
        deselectButton(activeButton);
        activeButton = buttonSelected;
    }

    public void deselectButton(JButton button) {
        if(button != null) {
            button.setBackground(Color.decode(GUI.BUTTONCOLOUR));
        }
    }

    private void addMouseHighLight(JButton toAdd) {
        toAdd.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                toAdd.setBackground(Color.decode(GUI.ACTIVEBUTTONCOLOUR));
                toAdd.setForeground(Color.decode(GUI.TEXTCOLOUR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                toAdd.setBackground(Color.decode(GUI.BUTTONCOLOUR));
                toAdd.setForeground(Color.decode(GUI.TEXTCOLOUR));
            }
        });
    }

    public void removeFile(String fileName) {
        for(int i = 0; i < listPanel.getComponentCount(); i++) {
            Component component = listPanel.getComponent(i);

            if (component instanceof JButton button) {
                if(button.getText().equals(fileName)) {
                    listPanel.remove(component);
                    break;
                }
            }
        }

        listPanel.revalidate();
        listPanel.repaint();
    }

    public void addFile(String fileName) {
        JButton activeButton = new JButton(fileName);
        activeButton.setBackground(Color.decode(GUI.BUTTONCOLOUR));
        addMouseHighLight(activeButton);
        activeButton.setPreferredSize(new Dimension(100, 25));
        activeButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));

        activeButton.addActionListener(e -> {
            selectButton(activeButton);
            ButtonAction.filePressed(activeButton.getText());
            if (fileSelectedListener != null) {          // <-- notify the GUI
                fileSelectedListener.accept(activeButton.getText());
            }
        });

        listPanel.add(activeButton);
        listPanel.revalidate();
        listPanel.repaint();
    }

    public String getActiveFile() {
        if(activeButton == null) {
            return null;
        }

        return activeButton.getText();
    }
}
