package ch.uzh.ifi.ddis.betweenness_centrality;

import java.util.HashMap;
import java.util.Set;

import com.signalcollect.javaapi.DefaultEdge;

public class BetweennessCentralityEdge extends
		DefaultEdge<BetweennessCentralityVertex> {
	
	BetweennessCentralityEdge(int sourceID, int targetID) {
		super(sourceID, targetID);
	}

	@Override
	public Object signal(BetweennessCentralityVertex sourceVertex) {
		HashMap<Set<Integer>, PathValue> state = (HashMap<Set<Integer>, PathValue>) ((HashMap) sourceVertex.getState()).clone();
		for (Set<Integer> key : state.keySet()){
			double distance_up_to_now = state.get(key).getDistance();
			if (this.sourceId) {
				
			}
			sourceVertex.getState().get(key).setDistance(distance_up_to_now + this.weight());
		}
		return sourceVertex.getState();
	}
}
