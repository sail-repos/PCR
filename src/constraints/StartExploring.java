package constraints;

import constraints.config.Configuration;
import constraints.pattern.PSchedule;
import constraints.trace.Trace;

import java.util.Queue;
import java.util.Vector;

public class StartExploring implements Runnable {

	private Trace traceObj;                        //the current trace
	private Vector<String> schedule_prefix;        //the prefix that genrates the trace
	private Queue<PSchedule> exploreQueue;      //the seed interleavings

	public static class BoxInt {

		volatile int  value;
		BoxInt(int initial) {
			this.value = initial;
		}
		public synchronized int getValue() {
			return this.value;
		}
		public synchronized void increase() {
			this.value++;
		}
		public synchronized void decrease() {
			this.value--;
		}
	}

	public final static BoxInt executorsCount = new BoxInt(0);

	public StartExploring(Trace trace, Vector<String> prefix, Queue<PSchedule> queue) {
		this.traceObj = trace;
		this.schedule_prefix = prefix;
		this.exploreQueue = queue;
	}

	public Trace getTrace() {
		return this.traceObj;
	}

	/**
	 * start exploring other interleavings
	 */
	public void run() {
		try {
			//set pattern queue
			ExploreSeedInterleavings explore = new ExploreSeedInterleavings(exploreQueue);
			//raw mcr
//			ExploreSeedInterleavings explore = new ExploreSeedInterleavings(exploreQueue);

			//load the trace
			traceObj.finishedLoading(true);
			//build SMT constraints over the trace and search alternative prefixes
			explore.execute(traceObj, schedule_prefix);
			ExploreSeedInterleavings.memUsed += ExploreSeedInterleavings.memSize(ExploreSeedInterleavings.mapPrefixEquivalent);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		finally {
			if (Configuration.DEBUG) {
				System.out.println("  Exploration Done with this trace! >>\n\n");
			}
		}
	}
}
