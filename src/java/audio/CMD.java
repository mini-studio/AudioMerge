package audio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wuquancheng on 14-1-17.
 */
public class CMD {
    public static interface CMDAdapter {
        void callback(String line);
        void errorCallback(String line);
    }

    public static void exec(String cmd,CMDAdapter cmdAdapter) throws IOException {

        List<String> cmds = new ArrayList<String>();
        cmds.add("sh");
        cmds.add("-c");
        cmds.add(cmd);
        ProcessBuilder processBuilder = new ProcessBuilder(cmds);
        Process p = processBuilder.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        String line = null;
        while ((line=reader.readLine())!=null) {
            cmdAdapter.errorCallback(line);
        }
        reader.close();
        reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        line = null;
        while ((line=reader.readLine())!=null) {
            cmdAdapter.callback(line);
        }
        reader.close();
    }

}
