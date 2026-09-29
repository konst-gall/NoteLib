/* Remove file button, FileButtonPressed*/

package com.konst_gall.noteLib;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.SplitPaneUI;

import com.formdev.flatlaf.intellijthemes.FlatOneDarkIJTheme;
import com.sun.tools.javac.Main;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GUI {
    public FileInteractor fileInteractor = new FileInteractor("./Storage");
    static FileList fileList = new FileList();

    final static String BACKGROUNDCOLOUR = "#3C3F41";
    final static String TEXTCOLOUR = "#A9B7C6";
    public final static String BUTTONCOLOUR = "#4A8AC7";
    public final static String ACTIVEBUTTONCOLOUR = "#7AA5FF";
    final static int borderSize = 20; //size for the borders around the app

    public static void main(String[] args) {
        FlatOneDarkIJTheme.setup();
        SwingUtilities.invokeLater(() -> InitGUI());
    }

    public static void InitGUI() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("NoteLib");
        frame.setIconImage(
                new ImageIcon(GUI.class.getResource("/icons/noteLibIcon.png")).getImage()
        );

        JPanel MainPanel = new JPanel();
        MainPanel.setBackground(Color.decode(BACKGROUNDCOLOUR));
        MainPanel.setLayout(new BoxLayout(MainPanel, BoxLayout.Y_AXIS));
        Border whiteBorder = BorderFactory.createLineBorder(Color.white);
        Border emptyBorder = BorderFactory.createEmptyBorder(borderSize, borderSize, borderSize, borderSize);
        MainPanel.setBorder(BorderFactory.createCompoundBorder(emptyBorder, whiteBorder));

        JSplitPane MainSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        //settings for MainSplit
        MainSplit.setDividerSize(5);

        //adding components to main
        MainPanel.add(MainSplit);
        JPanel Editor = new JPanel();
        JPanel Selector = new JPanel();
        Selector.setBackground(Color.decode(GUI.BACKGROUNDCOLOUR));
        MainSplit.add(Selector);
        MainSplit.add(Editor);

        //Setting up selector part
        //Setting up buttons for the selector
        Selector.setLayout(new BorderLayout(0,0));
        JPanel SelectorTop = new JPanel();
        SelectorTop.setBackground(Color.decode(BACKGROUNDCOLOUR));
        SelectorTop.setLayout(new FlowLayout());

        //Creates the add files button
        JButton createFileButton = new JButton("+");
        createFileButton.setBackground(Color.decode(BUTTONCOLOUR));
        createFileButton.setForeground(Color.decode(TEXTCOLOUR));
        createFileButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                createFileButton.setBackground(Color.decode(ACTIVEBUTTONCOLOUR));
                createFileButton.setForeground(Color.decode(TEXTCOLOUR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                createFileButton.setBackground(Color.decode(BUTTONCOLOUR));
                createFileButton.setForeground(Color.decode(TEXTCOLOUR));
            }
        });
        createFileButton.addActionListener(e -> {
            toCreateState(Editor);
            frame.revalidate();
            frame.repaint();
        });

        //Creates the Save file button
        JButton removeButton = new JButton("-");
        removeButton.setBackground(Color.decode(BUTTONCOLOUR));
        removeButton.setForeground(Color.decode(TEXTCOLOUR));
        removeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                removeButton.setBackground(Color.decode(ACTIVEBUTTONCOLOUR));
                removeButton.setForeground(Color.decode(TEXTCOLOUR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                removeButton.setBackground(Color.decode(BUTTONCOLOUR));
                removeButton.setForeground(Color.decode(TEXTCOLOUR));
            }
        });
        removeButton.addActionListener(e -> {
            ButtonAction.removePressed(fileList.getActiveFile());
            fileList.removeFile(fileList.getActiveFile());
            frame.revalidate();
            frame.repaint();
        });

        SelectorTop.add(createFileButton);
        SelectorTop.add(removeButton);
        Selector.add(SelectorTop, BorderLayout.NORTH);
        Selector.add(fileList.list, BorderLayout.CENTER);

        Editor.setBackground(Color.decode(BACKGROUNDCOLOUR));
        Editor.setLayout(new BoxLayout(Editor, BoxLayout.Y_AXIS));
        toEditState(Editor);

        fileList.setFileSelectedListener(fileName -> {
            toEditState(Editor);
            frame.revalidate();
            frame.repaint();
        });

        //window set up
        frame.add(MainPanel);
        frame.setResizable(true);
        frame.setSize(500, 500);
        Dimension minSize = frame.getPreferredSize();
        minSize.height = 300;
        frame.setMinimumSize(minSize);
        frame.setVisible(true);
    }

    //takes the editor to the state to be able to create new files
    private static void toCreateState(JPanel panel) {
        panel.removeAll();
        JLabel nameLabel = new JLabel("File name:");
        nameLabel.setForeground(Color.decode(TEXTCOLOUR));
        nameLabel.setBackground(Color.decode(BACKGROUNDCOLOUR));

        JTextField inputField = new JTextField();
        inputField.setBackground(Color.decode(BACKGROUNDCOLOUR));
        inputField.setForeground(Color.decode(TEXTCOLOUR));

        JPanel savePanel = new JPanel();
        savePanel.setBackground(Color.decode(BACKGROUNDCOLOUR));

        JPanel padding = new JPanel();
        padding.setBackground(Color.decode(BACKGROUNDCOLOUR));
        padding.setPreferredSize(new Dimension(20, 20));

        JButton createButton = new JButton("Create");
        createButton.setBackground(Color.decode(BUTTONCOLOUR));
        createButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                createButton.setBackground(Color.decode(ACTIVEBUTTONCOLOUR));
                createButton.setForeground(Color.decode(TEXTCOLOUR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                createButton.setBackground(Color.decode(BUTTONCOLOUR));
                createButton.setForeground(Color.decode(TEXTCOLOUR));
            }
        });
        createButton.addActionListener(e -> {
            ButtonAction.createPressed(inputField.getText());
            fileList.addFile(inputField.getText());
        });

        savePanel.add(padding);
        savePanel.add(createButton);
        panel.add(nameLabel);
        panel.add(inputField);
        panel.add(savePanel);
    }

    //takes the editor into the edit state
    public static void toEditState(JPanel panel) {
        panel.removeAll();
        JTextArea fileText = new JTextArea();
        fileText.setBackground(Color.decode(BACKGROUNDCOLOUR));
        fileText.setForeground(Color.decode(TEXTCOLOUR));
        fileText.setMinimumSize(new Dimension(500, 200));
        String text = ButtonAction.getFileText(fileList.getActiveFile());
        if(text != null) {
            fileText.setText(text);
        }

        JPanel buttomAlign = new JPanel();
        buttomAlign.setBackground(Color.decode(BACKGROUNDCOLOUR));
        buttomAlign.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton saveButton = new JButton("Save");
        saveButton.setBackground(Color.decode(BUTTONCOLOUR));
        saveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                saveButton.setBackground(Color.decode(ACTIVEBUTTONCOLOUR));
                saveButton.setForeground(Color.decode(TEXTCOLOUR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                saveButton.setBackground(Color.decode(BUTTONCOLOUR));
                saveButton.setForeground(Color.decode(TEXTCOLOUR));
            }
        });
        saveButton.addActionListener(e -> {
            ButtonAction.savePressed(fileList.getActiveFile(), fileText.getText());
        });

        panel.add(fileText);
        buttomAlign.add(saveButton);
        panel.add(buttomAlign);
    }
}
