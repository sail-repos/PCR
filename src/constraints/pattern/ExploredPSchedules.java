package constraints.pattern;

import java.util.ArrayList;

public class ExploredPSchedules {

    private static ArrayList<String> exploredPScheduls;
    private static ArrayList<String> exploredSchedules;

    public static ArrayList<String> getExploredPScheduls(){

        if (exploredPScheduls == null){
            exploredPScheduls = new ArrayList<>();
        }
        return exploredPScheduls;
    }

    public static ArrayList<String> getExploredScheduls(){

        if (exploredSchedules == null){
            exploredSchedules = new ArrayList<>();
        }
        return exploredSchedules;
    }

    public static boolean checkIfThisPScheduleExplored(PSchedule curPSchedule){

        exploredPScheduls = getExploredPScheduls();
        if (exploredPScheduls.contains(curPSchedule.getPScheduleSign())){
            return true;
        }else {
            exploredPScheduls.add(curPSchedule.getPScheduleSign());
            return false;
        }
    }

    public static boolean checkIfThisScheduleExplored(PSchedule curPSchedule){

        exploredSchedules = getExploredScheduls();
        if (exploredSchedules.contains(curPSchedule.getCurSchedule().toString())){
            return true;
        } else {
            exploredSchedules.add(curPSchedule.getCurSchedule().toString());
            return false;

        }
    }

}
