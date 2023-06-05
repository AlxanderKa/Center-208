package work;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.io.FilenameUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

@Getter
@Setter
@ToString
public class Sourse {
    private String pathStr;
    private final String fileName;
    private final Path path;
    private final File file;
    private final double size;
    private final String type;

    public Sourse(String pathStr) {
        this.pathStr = pathStr;
        this.path = Path.of(pathStr);
        this.file = new File(pathStr);
        this.fileName = file.getName();
        this.size = file.length();
        this.type = FilenameUtils.getExtension(fileName);
    }
   /* public void openFile(File file) {
        try {
            Desktop.getDesktop().open(file);
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }*/

}
