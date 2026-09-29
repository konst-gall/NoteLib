package com.konst_gall.noteLib;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.util.logging.Logger;
import java.io.IOException;
import java.io.Writer;

/**
 * Responsible for all interactions between the files and the program
 */
public class FileInteractor {
    File folder;
    private final Logger logger = Logger.getLogger("FileInteractor");

    public FileInteractor(String ActivaFolder) {
        this.folder = new File(ActivaFolder);
        if(!folder.exists()) {
            try {
                Files.createDirectories(folder.toPath());
            }
            catch(IOException e) {
                logger.info("Error Creating " + ActivaFolder);
            }
        }
    }

    public String[] getAllFiles() {
        return folder.list();
    }

    public boolean deleteFile(String name) {
        File file = new File(folder, name);

        if(file.exists()) {
            if(file.delete()) {
                logger.info("File deleted: " + name);
            }
            else {
                logger.info("File not deleted: " + name);
            }

            return true;
        }

        return false;
    }

    public void saveFile(String FileName) {
        File file = new File(folder, FileName);

        if(!file.exists()) {
            try {
                file.createNewFile();
                logger.info("File created: " + FileName);
            }
            catch (IOException e) {
                logger.info("Error creating file");
            }
        }
    }

    public void addTextToFile(String FileName, String Text) {
        File file = new File(folder, FileName);

        try {
            FileWriter writer = new FileWriter(file, false);
            writer.write(Text);
            writer.close();
            logger.info("Wrote in file: " + FileName);
        }
        catch (IOException e) {
            logger.info("Error writing to file");
        }
    }

    public String getTextFromFile(String FileName) {
        if(FileName == null) {
            return null;
        }

        File file = new File(folder, FileName);
        try {
            return Files.readString(file.toPath());
        }
        catch (IOException e) {
            logger.info("Error reading file" + e);
        }

        return null;
    }
}
