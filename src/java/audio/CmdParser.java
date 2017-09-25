package audio;

import java.util.Properties;

/**
 * Created by Wuquancheng on 14-2-7.
 */
public class CmdParser {

    String[] args;
    Properties properties = new Properties();

    public CmdParser (String[] args) {
        this.args = args;
        parse();
    }

    private void parse() {
        for (String arg:args) {
            String[] items = arg.split("=");
            if (items.length==2) {
                String key = items[0].trim();
                String value = items[1].trim();
                if (key.startsWith("-D")) {
                    System.setProperty(key.substring(2),value);
                }
                else if (key.startsWith("-")) {
                    properties.setProperty(key.substring(1),value);
                }
                else {
                    properties.setProperty(key,value);
                }
            }
        }
    }

    public String get(String key) {
        return properties.getProperty(key);
    }
}
