package mapcov.testsattr;

public class testcase {

    private upattern pattern;

    private String prefix;
    private String suffix1;
    private String suffix2;

    private Boolean bugInducing;

    public testcase() {
    }

    public testcase(upattern pattern, String prefix, String suffix1, String suffix2) {
        this.pattern = pattern;
        this.prefix = prefix;
        this.suffix1 = suffix1;
        this.suffix2 = suffix2;
        this.bugInducing = false;
    }

    public upattern getPattern() {
        return pattern;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getSuffix1() {
        return suffix1;
    }

    public String getSuffix2() {
        return suffix2;
    }

    public void setPattern(upattern pattern) {
        this.pattern = pattern;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setSuffix1(String suffix1) {
        this.suffix1 = suffix1;
    }

    public void setSuffix2(String suffix2) {
        this.suffix2 = suffix2;
    }

    public Boolean getBugInducing() {
        return bugInducing;
    }

    public void setBugInducing(Boolean bugInducing) {
        this.bugInducing = bugInducing;
    }

    @Override
    public String toString() {
        return pattern + prefix + suffix1 + suffix2;
    }
}
