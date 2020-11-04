package scheduler.listeners;

import scheduler.reex.Scheduler;
import scheduler.scheduling.events.EventDesc;
import scheduler.scheduling.events.LocationDesc;
import scheduler.scheduling.strategy.ThreadInfo;

import java.util.ArrayList;
import java.util.List;

public class ExplorationContextSwitchListener extends ExplorationListenerAdapter {
    
    private ThreadInfo previousThreadInfo;
    private LocationDesc previousThreadLocation;
    private List<String> contextSwitchInfo;
    private String CONTEXSWITCHLOGFILE = "";

    @Override
    public void startingSchedule() {
        previousThreadInfo = null;
        contextSwitchInfo = new ArrayList<String>();
    }
    
    @Override
    public void choiceMade(Object choice) {
        if (choice instanceof ThreadInfo) {
            ThreadInfo currentChoice = (ThreadInfo) choice;
            if (currentChoice != previousThreadInfo) {
                if (previousThreadInfo != null && previousThreadLocation != null) {
                    String switchFrom = "Switched after " + previousThreadInfo.getThread().getName() + " at "
                            + previousThreadLocation.getClassName() + " "  
                            + previousThreadLocation.getToLineNumber();
                    contextSwitchInfo.add(switchFrom);
                }
                String switchTo = "To " + 
                        currentChoice.getThread().getName()
                        //+ " at " + 
                        //currentChoice.getLocationDesc().getClassName() + " " + 
                        //currentChoice.getLocationDesc().getToLineNumber()
                        ;
                contextSwitchInfo.add(switchTo);
                previousThreadInfo = currentChoice;
               
            }
        }
    }
    
    @Override
    public void completedSchedule(List<Integer> choicesMade) {
        previousThreadInfo = null;
        previousThreadLocation = null;
    }
    
    @Override
    public void failureDetected(String errorMsg, List<Integer> choicesMade) {

        CONTEXSWITCHLOGFILE = "";

        CONTEXSWITCHLOGFILE += "============================== CONTEXT SWITCH INFO ==============================" + "\n";
        for (String s : contextSwitchInfo) {
            CONTEXSWITCHLOGFILE += s + "\n";
        }
        CONTEXSWITCHLOGFILE += "============================================================";
        System.err.println(CONTEXSWITCHLOGFILE);
    }
    
    @Override
    public void afterEvent(EventDesc eventDesc) {
        ThreadInfo ti = Scheduler.getLiveThreadInfos().get(Thread.currentThread());
        previousThreadLocation = ti.getLocationDesc();
    }

}
