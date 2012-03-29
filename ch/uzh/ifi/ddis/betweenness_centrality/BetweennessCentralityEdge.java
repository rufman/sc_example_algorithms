package ch.uzh.ifi.ddis.betweenness_centrality;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.signalcollect.javaapi.DefaultEdge;

public class BetweennessCentralityEdge extends
		DefaultEdge<BetweennessCentralityVertex> {

	BetweennessCentralityEdge(int sourceID, int targetID) {
		super(sourceID, targetID);
	}

	@Override
	public Object signal(BetweennessCentralityVertex sourceVertex) {
		HashMap<Set<Integer>, PathValue> state = new HashMap<Set<Integer>, PathValue>();
		for (Set<Integer> key : sourceVertex.getState().keySet()) {
			Set<Integer> key_c = new HashSet<Integer>();
			for (Integer i : key_c){
				key_c.add(i);
			}
			PathValue pathvalue_c = new PathValue();
			Set<Integer> path = new HashSet<Integer>();
			for (Integer i : sourceVertex.getState().get(key).getPath()) {
				path.add(i);
			}
			pathvalue_c.setKey(key_c);
			pathvalue_c.setPath(path);
			pathvalue_c.setDistance(sourceVertex.getState().get(key).getDistance());
			
			state.put(key_c, pathvalue_c);
		}
		for (Set<Integer> key : state.keySet()){
			double old_distance = state.get(key).getDistance();
			state.get(key).setDistance(old_distance+this.weight());
		}
		return state;
	}
}
