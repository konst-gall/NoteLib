package com.konst_gall.noteLib;

/**
 * Responsible for passing commands to the file interactor
 */
public class ButtonAction {
    public static final FileInteractor fileInteractor = new FileInteractor("./Storage");

    public static void removePressed(String fileName) {
        fileInteractor.deleteFile(fileName);
    }

    public static void savePressed(String fileName, String text) {
        fileInteractor.addTextToFile(fileName, text);
    }

    public static void filePressed(String fileName) {
        //empty, no file interaction takes place
    }

    public static void createPressed(String fileName) {
        fileInteractor.saveFile(fileName);
    }

    public static String getFileText(String fileName) {
        return fileInteractor.getTextFromFile(fileName);
    }
}
