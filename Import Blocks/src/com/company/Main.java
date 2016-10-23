package com.company;


import org.apache.commons.io.FilenameUtils;
import java.io.IOException;
import java.io.File;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.security.CodeSource;
import java.util.Arrays;
import java.util.List;

public class Main{

    public static void main(String[] args) throws IOException {
        File jarDir = null;
        File currentDir = null;
        File parentDir = null;

        try {
            jarDir = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        System.out.println("Jar File is: " + jarDir);
        //Get current location of the jar (THIS SHOULD ALWAYS BE "/assets/blockTiles")
        try {
            currentDir = jarDir.getCanonicalFile().getParentFile(); // Resolve parent location out of the real path
            System.out.println("current dir is: " + currentDir);
        } catch (IOException e) {

        }
        //Get current parent of /blocktiles (this should always be "/assets")
        try {
          parentDir = currentDir.getCanonicalFile().getParentFile(); // Resolve parent location out of the real path
            System.out.println("Parent dir is: " + parentDir);
        } catch (IOException e) {

        }
        //Get current location of /blocks (should always be inside "assets")
        File blocksDir = new File(parentDir, "/blocks");
        System.out.println("Blocks dir is: " + blocksDir);

        File[] files = currentDir.listFiles(); //array of files in blockTiles Folder
        File[] filesBlock = blocksDir.listFiles(); //array of files in blocks Folder
        //iterate through array of files in texture folder “blockTiles” To clarify, this program runs from within the blockTiles folder.
        for(File x : files){
            String fileName = x.getName();
            //boolean for checking for existing .block file
            Boolean exists = false;
            String extension = "png";


            //if it’s a texture
            if (FilenameUtils.isExtension(fileName, extension))
            {
                for(File y : filesBlock){
                    String texture = FilenameUtils.removeExtension(fileName);
                    System.out.println("texture String is " + texture);
                    String block = FilenameUtils.removeExtension(y.getName());
                    System.out.println("block String is " + block);
                    if (texture == block){
                        exists = true;
                        System.out.println("matching file exists, moving on!");
                    }
                }

                if(!exists){
                    System.out.println("Matching file does NOT exist, generating!");
                    //Create new .block file using the same filename
                    System.out.println("fileName is " + FilenameUtils.removeExtension(fileName));
                    String blockFileName = (FilenameUtils.removeExtension(fileName) + ".block");
                    System.out.println("blockFileName String is " + blockFileName);

                    List<String> lines = Arrays.asList("//Customize this file to define your block!", "//https://github.com/Terasology/TutorialAssetSystem/wiki/Block-Attributes");
                    Path file = Paths.get(blocksDir + "\\" + blockFileName);
                    System.out.println("name of the file to be written is: " + file);
                    Files.write(file, lines, Charset.forName("UTF-8"));

                }
            }
        }

    }

}






