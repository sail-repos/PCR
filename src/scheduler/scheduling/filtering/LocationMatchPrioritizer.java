package scheduler.scheduling.filtering;

import scheduler.scheduling.events.LocationDesc;
import scheduler.scheduling.strategy.ThreadInfo;

import java.util.Comparator;
import java.util.Set;

public class LocationMatchPrioritizer implements Comparator<ThreadInfo> {

    private final LocationMatchType locMatchType;
    private final Set<LocationDesc> allowedLocs;

    public LocationMatchPrioritizer(LocationMatchType locMatchType, Set<LocationDesc> allowedLocs) {
        this.locMatchType = locMatchType;
        this.allowedLocs = allowedLocs;
    }

    @Override
    public int compare(ThreadInfo threadInfo, ThreadInfo anotherThreadInfo) {
        boolean threadInfoMatch = false;
        boolean anotherThreadInfoMatch = false;
        for (LocationDesc allowedLoc : allowedLocs) {
            if (threadInfoMatch && anotherThreadInfoMatch) {
                return threadInfo.compareTo(anotherThreadInfo);
            }
            if (!threadInfoMatch) {
                threadInfoMatch = locMatchType.match(threadInfo, allowedLoc);
            }
            if (!anotherThreadInfoMatch) {
                anotherThreadInfoMatch = locMatchType.match(anotherThreadInfo, allowedLoc);
            }
        }
        if (!threadInfoMatch && !anotherThreadInfoMatch) {
            return threadInfo.compareTo(anotherThreadInfo);
        } else if (!threadInfoMatch) {
            return 1;
        } else {
            return -1;
        }
    }

}
