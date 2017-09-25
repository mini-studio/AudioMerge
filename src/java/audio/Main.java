package audio;

import java.io.*;
import java.util.Properties;

/**
 * Created by Wuquancheng on 2017/9/25.
 */
public class Main {

    static Properties properties = new Properties();

    private static void loadConfig(String conf)  throws IOException {
        String configFilePath = conf + File.separator + "conf.properties";
        InputStream in = new FileInputStream(configFilePath);
        InputStreamReader reader = new InputStreamReader(in, "utf-8");
        properties.load(reader);
    }


    public static void main(String[] args) throws IOException {
        String conf = System.getProperty("conf");
        if (conf == null) {
            conf = System.getProperty("user.dir") + File.separator + "conf";
        }
        loadConfig(conf);
        String sourceDir = (String)properties.get("record.file.src.dir");
        String outputDir = (String)properties.get("record.file.merger.dir");
        File file = new File(outputDir);
        if (!file.exists() || !file.isDirectory()) {
            file.mkdirs();
        }
        Scanner scanner = new Scanner(sourceDir, outputDir);
        scanner.scan();
    }


}
