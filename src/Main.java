import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        String[] dirGames = {"src", "res", "savegames", "temp"};
        String[] dirSrc = {"main", "test"};
        String[] filesMain = {"Main.java", "Utils.java"};
        String[] dirRes = {"drawables", "vectors", "icons"};
        String[] filesTemp = {"temp.txt"};
        String path = "C:\\Users\\dudko\\Games\\";
        for (int i = 0; i < dirGames.length; i++) {
            File dir = new File(path + dirGames[i]);
            String message = "";
            if (dir.mkdir()) {
                message = "Каталог \"" + dirGames[i] + "\" создан";
            } else {
                message = "Error create";
            }
            System.out.println(message);
            sb.append(message + "\n");
            wr(sb);
        }
        String path1 = "C:\\Users\\dudko\\Games\\src\\";
        for (int i = 0; i < dirSrc.length; i++) {
            File dir1 = new File(path1 + dirSrc[i]);
            String message = "";
            if (dir1.mkdir()) {
                message = "Каталог \"" + dirSrc[i] + "\" создан";
            } else {
                message = "Error create";
            }
            System.out.println(message);
            sb.append(message + "\n");
            wr(sb);
        }
        String path2 = "C:\\Users\\dudko\\Games\\res\\";
        for (int i = 0; i < dirRes.length; i++) {
            File dir2 = new File(path2 + dirRes[i]);
            String message = "";
            if (dir2.mkdir()) {
                message = "Каталог \"" + dirRes[i] + "\" создан";
            } else {
                message = "Error create";
            }
            System.out.println(message);
            sb.append(message + "\n");
            wr(sb);
        }
        for (int i = 0; i < filesMain.length; i++) {
            File main = new File("C:\\Users\\dudko\\Games\\src\\main\\" + filesMain[i]);
            String message = "";
            try {
                if (main.createNewFile())
                    message = "Файл " + filesMain[i] + " создан";
            } catch (IOException ex) {
                message = "Error create";
            }
            System.out.println(message);
            sb.append(message + "\n");
            wr(sb);
        }
        for (int i = 0; i < filesTemp.length; i++) {
            File temp = new File("C:\\Users\\dudko\\Games\\temp\\" + filesTemp[i]);
            String message = "";
            try {
                if (temp.createNewFile())
                    message = "Файл " + filesTemp[i] + " создан";
            } catch (IOException ex) {
                message = "Error create";
            }
            System.out.println(message);
            sb.append(message + "\n");
            wr(sb);
        }
        GameProgress [] gp= {
            new GameProgress(20, 203, 3, 20),
            new GameProgress(5, 20, 7, 50),
            new GameProgress(8, 30, 9, 80),
        };
        List<String> allSaves = new ArrayList<>();
        String filePath = "C:\\Users\\dudko\\Games\\savegames\\";
        String zipFilePath = "C:\\Users\\dudko\\Games\\savegames\\zip.zip";
        for (int i = 0; i < gp.length; i++) {
            String saveFileName = filePath  + "save" + i + ".txt";
            saveGame(saveFileName, gp);
            allSaves.add(saveFileName);
        }
        zipFiles(zipFilePath,  allSaves);
        for (int i =0; i < allSaves.size(); i++) {
            String saveFileName = filePath + "save" + i + ".txt";
            File deleteFile = new File(saveFileName);
            if (deleteFile.delete()){
                System.out.println("Файл " + saveFileName + " удален");
            } else {
                System.out.println("Файл не удален");
            }
       }
    }

    public static void wr(StringBuilder sb) {
        try (FileWriter writer = new FileWriter("temp.txt", true)) {
            String text = sb.toString();
            writer.write(text);
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void saveGame(String path, GameProgress[] gp) {
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gp);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String zipFilePath, List<String> allSaves) {
        try (FileOutputStream fos = new FileOutputStream(zipFilePath);
             ZipOutputStream zipOut = new ZipOutputStream(fos)) {
            for (String save : allSaves) {
                File fileToZip = new File(save);
                try (FileInputStream fis = new FileInputStream(fileToZip)) {
                    ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
                    zipOut.putNextEntry(zipEntry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zipOut.write(buffer);
                    zipOut.closeEntry();
                    System.out.println("Файл \"" + save + "\" добавлен в архив " + "\"" + zipFilePath + "\"");
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}


