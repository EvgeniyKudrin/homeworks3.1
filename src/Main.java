import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        List<File> fileList = Arrays.asList(
                new File("F://Games//savegames//save0.dat"),
                new File("F://Games//savegames//save1.dat"),
                new File("F://Games//savegames//save2.dat")
        );
        GameProgress[] newList = {
                new GameProgress(400,120,15,209),
                new GameProgress(600,140,10,500),
                new GameProgress(1200,250,50,900)
        };
        saveGame(newList);
        zipFiles(newList);
        for (File x : fileList) {
            deleteFile(x);
        }
    }

    public static void saveGame(GameProgress[] newList) {
        for (int i = 0; i < newList.length; i++) {
            try (FileOutputStream fos = new FileOutputStream("F//Games//savegames//save" + i + ".dat");
                 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(newList[i]);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public static void zipFiles(GameProgress[] newList) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream("F://Games//savegames//zip.zip"))) {
            for (int i = 0; i < newList.length; i++) {
                FileInputStream fis = new FileInputStream("F://Games//savegames//save" + i + ".dat");
                ZipEntry entry = new ZipEntry("save" + i + ".dat");
                zout.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zout.write(buffer);
                zout.closeEntry();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    public static void deleteFile( File file) {
        if (file.delete()) {
            System.out.println("Файл" + file.getName() + "удалён");
        } else {
            System.out.println("Файл" + file.getName() + "не удалён");
        }
    }
}