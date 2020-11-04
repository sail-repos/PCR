package scheduler.scheduling.filtering;

import scheduler.scheduling.strategy.ChoiceType;

import java.util.SortedSet;

/**
 * Default {@link SchedulingFilter} that does not perform any filtering.
 */
public class DefaultFilter implements SchedulingFilter {

    @Override
    public SortedSet<? extends Object> filterChoices(SortedSet<? extends Object> choices, ChoiceType choiceType) {
        return choices;
    }

}
