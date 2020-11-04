package constraints.pattern;

import java.util.List;

public class PSchedule {

    private Pattern curPattern;
    private List<String> curSchedule;
    private String PScheduleSign;
    public PSchedule(List<String> curSchedule) {

        this.curPattern = null;
        this.curSchedule = curSchedule;
    }

    public PSchedule(Pattern curPattern, List<String> curSchedule) {
        this.curPattern = curPattern;
        this.curSchedule = curSchedule;
        PScheduleSign = uniformPScheduleString(curPattern,curSchedule);
    }

    public String uniformPScheduleString(Pattern curPattern, List<String> curSchedule){

        String spschedule = curPattern.getUpattern() + "-" + curSchedule.toString();
        return spschedule;
    }

    public String getPScheduleSign() {
        return PScheduleSign;
    }

    public Pattern getCurPattern() {
        return curPattern;
    }

    public List<String> getCurSchedule() {
        return curSchedule;
    }

    @Override
    public String toString() {
        return "PSchedule{" +
                "curPattern=" + curPattern +
                ", curSchedule=" + curSchedule +
                ", PScheduleSign='" + PScheduleSign + '\'' +
                '}';
    }
}
