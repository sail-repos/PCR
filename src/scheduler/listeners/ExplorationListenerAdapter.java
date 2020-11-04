package scheduler.listeners;

import scheduler.scheduling.events.EventDesc;
import scheduler.scheduling.strategy.ChoiceType;
import scheduler.scheduling.strategy.ThreadInfo;

import java.util.List;
import java.util.SortedSet;

public class ExplorationListenerAdapter implements ExplorationListener {

    @Override
    public void startingExploration(String name) {

    }

    @Override
    public void startingSchedule() {

    }

    @Override
    public void makingChoice(SortedSet<? extends Object> choices, ChoiceType choiceType) {

    }

    @Override
    public void choiceMade(Object choice) {

    }

    @Override
    public void completedSchedule(List<Integer> choicesMade) {

    }

    @Override
    public void completedExploration() {

    }

    @Override
    public void beforeForking(ThreadInfo childThread) {

    }

    @Override
    public void beforeEvent(EventDesc eventDesc) {

    }

    @Override
    public void afterEvent(EventDesc eventDesc) {

    }

    @Override
    public void failureDetected(String errorMsg, List<Integer> choicesMade) {

    }

}
