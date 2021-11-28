package engine.file;

import engine.log.Log;
import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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

    public static List<String> readAllLines(String fileName) {
        List<String> lines = new ArrayList<>();
        String file = File.class.getClassLoader().getResource(fileName).getPath().substring(1);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }
}
