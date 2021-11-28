package engine.file;

import engine.log.Log;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

public class File {
    public static String ReadFile(String fileName) {
        InputStream is = File.class.getClassLoader().getResourceAsStream(fileName);

        try {
            return IOUtils.toString(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }
}
