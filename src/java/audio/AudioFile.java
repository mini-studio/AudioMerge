package audio;

/**
 * Created by Wuquancheng on 2017/9/25.
 */
public class AudioFile {

    private String mergeError;
    private String baseName;
    private String in;
    private String out;
    private String merge;

    public String getIn() {
        return in;
    }

    public void setIn(String in) {
        this.in = in;
    }

    public String getOut() {
        return out;
    }

    public String getMerge() {
        return merge;
    }

    public void setMerge(String merge) {
        this.merge = merge;
    }

    public void setOut(String out) {
        this.out = out;
    }

    public AudioFile() {

    }

    public String getBaseName() {
        return baseName;
    }

    public void setBaseName(String baseName) {
        this.baseName = baseName;
    }

    public String getMergeError() {
        return mergeError;
    }

    public void setMergeError(String mergeError) {
        this.mergeError = mergeError;
    }
}
