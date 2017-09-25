package audio;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Wuquancheng on 2017/9/25.
 */
public class Scanner {

    private String sourceDirectory;
    private String outputDirectory;

    public Scanner(String sourceDir, String outputDir) {
        this.sourceDirectory = sourceDir;
        this.outputDirectory = outputDir;
    }

    public void scan() {
        List<AudioFile> audioFiles = scanSourceDirectory();
        for (AudioFile af : audioFiles) {
            try {
                mergeFile(af);
                if (af.getMerge() != null) {
                    System.out.println(af.getMerge());
                    //需要对文件进行移除的动作，并添加到数据库
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private List<AudioFile> scanSourceDirectory() {
        File sourceDir = new File(sourceDirectory);
        String[] files = sourceDir.list();
        Map<String, AudioFile> m = new HashMap<>();
        for (String f : files) {
            if (f.endsWith(".wav")) {
                File file = new File(sourceDirectory, f);
                if (file.isFile()) {
                    long fileSize =  fileSize(file.getAbsolutePath());
                    long intervalInMinute = (System.currentTimeMillis() - file.lastModified())/1000/60;
                    if (fileSize > 10 && intervalInMinute > 3) {
                        int p = f.lastIndexOf("-");
                        String fileKey = f.substring(0, p);
                        String inOrOut = f.substring(p+1);
                        AudioFile audioFile = m.get(fileKey);
                        if (audioFile == null) {
                            audioFile = new AudioFile();
                            audioFile.setBaseName(fileKey);
                            m.put(fileKey, audioFile);
                        }
                        if (inOrOut.equalsIgnoreCase("in.wav")) {
                            audioFile.setIn(file.getAbsolutePath());
                        }
                        else if (inOrOut.equalsIgnoreCase("out.wav")) {
                            audioFile.setOut(file.getAbsolutePath());
                        }
                    }
                }
            }
        }
        return new ArrayList(m.values());
    }


    /**
     * 获取文件的大小
     * @param file
     * @return
     */
    private long fileSize(String file) {
        String cmd = "ls -l  "+file+" | awk '{print $5}'";
        final Map m = new HashMap();
        m.put("v", -1L);
        try {
            CMD.exec(cmd, new CMD.CMDAdapter() {
                @Override
                public void callback(String line) {
                    m.put("v", Long.parseLong(line));
                }

                @Override
                public void errorCallback(String line) {
                    m.put("v", -1L);
                }
            });
        } catch (IOException e) {
            System.out.println(cmd);
            e.printStackTrace();
        }
        return (Long)m.get("v");
    }

    private void mergeFile(final AudioFile audioFile) throws Exception{
        String mergedName = audioFile.getBaseName() + "-all.wav";
        final String targetFileName = outputDirectory + File.separator + mergedName;
        String cmd = "sox -m " + audioFile.getIn() + " " + audioFile.getOut() + " " + targetFileName;
        CMD.exec(cmd, new CMD.CMDAdapter() {
            @Override
            public void callback(String line) {
                audioFile.setMerge(targetFileName);
            }
            @Override
            public void errorCallback(String line) {
                audioFile.setMerge(null);
                audioFile.setMergeError(line);
            }
        });
        File f = new File(targetFileName);
        if (f.exists()) {
            audioFile.setMerge(targetFileName);
        }
        else {
            audioFile.setMerge(null);
        }
    }
}
