package constraints.sdg;

import constraints.graph.ReachabilityEngine;

import java.util.Map;
import java.util.Set;

public class ConstructReachabilitySDG {
	public static ReachabilityEngine ConstructReachability(Map<Integer, Set<Integer>> sdg){
		ReachabilityEngine reachSDG = new ReachabilityEngine();
		
		for (Map.Entry<Integer, Set<Integer>> entry:sdg.entrySet()){
			Integer key = entry.getKey();
			Set<Integer> valueSet = entry.getValue();

			for (Integer value : valueSet) {
				reachSDG.addEdge(key, value);
			}
		}
		
		return reachSDG;
	}
}
