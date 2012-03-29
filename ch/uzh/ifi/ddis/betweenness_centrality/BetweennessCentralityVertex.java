/**
 *
 */
package ch.uzh.ifi.ddis.betweenness_centrality;

import java.util.Map.Entry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.signalcollect.Edge;
import com.signalcollect.EdgeId;
import com.signalcollect.javaapi.DataGraphVertex;

/**
 * @author: Stephane Rufer
 * The University of Z&uuml;rich<br>
 * 
 * Date: Mar 11, 2012
 * Package: ch.uzh.ifi.ddis.betweenness_centrality
 */
public class BetweennessCentralityVertex
		extends
		DataGraphVertex<Integer, HashMap<Set<Integer>,PathValue>, HashMap<Set<Integer>,PathValue>> {
	
	public BetweennessCentralityVertex(Integer id, HashMap<Set<Integer>,PathValue> state){
		super(id, state);
	}

	public HashMap<Set<Integer>,PathValue> collect(HashMap<Set<Integer>,PathValue> oldState, 
													  Iterable<HashMap<Set<Integer>,PathValue>> mostRecentSignals){
		HashMap<Set<Integer>,PathValue> newState = (HashMap<Set<Integer>,PathValue>) ((HashMap) oldState).clone();
		
		// Find neighbors
		Set<Integer> neighbors = new HashSet<Integer>();
		scala.collection.Iterator<scala.collection.Iterable<Edge>> it = this.getOutgoingEdges().iterator();
		while (it.hasNext()){
			 scala.collection.Iterator<Edge> eit = it.next().iterator();
			 while (eit.hasNext()){
				 Edge e = eit.next();
				 neighbors.add((Integer)e.id().targetId());
			 }
		}
		
		for (HashMap<Set<Integer>,PathValue> signal : mostRecentSignals) {
				for (Set<Integer> key : signal.keySet()) {
					if (oldState.get(key) == null) {
						PathValue value = signal.get(key);
						if (value.getPath().contains(this.id())) {
							newState.put(key, signal.get(key));
						}
						for (Integer n : neighbors){
							if (value.getPath().contains(n)) {
								int new_vertex = -1;
								Iterator<Integer> kit = key.iterator();
								int start_loop = 0;
								while (kit.hasNext()) {
									int next_kel = kit.next();
									if (!neighbors.contains(next_kel)) {
										new_vertex = next_kel;
									}
									start_loop++;
								}
								
								if (start_loop != 2) { 
									Set<Integer> new_key = new HashSet<Integer>();
									new_key.add(this.id());
									new_key.add(n);
									PathValue new_value = new PathValue();
									Set<Integer> path = new HashSet<Integer>();
									path.add(this.id());
									path.add(n);
									new_value.setKey(new_key);
									new_value.setPath(path);
									new_value.setDistance(value.getDistance());
									newState.put(new_key, new_value);
								} else if (new_vertex == -1){
									newState.put(key, signal.get(key));
								} else {
									Set<Integer> new_key = new HashSet<Integer>();
									new_key.add(this.id());
									new_key.add(new_vertex);
									PathValue new_value = new PathValue();
									Set<Integer> path = new HashSet<Integer>();
									path.add(this.id());
									for (Integer i : value.getPath()){
										path.add(i); 
									}
									new_value.setKey(new_key);
									new_value.setPath(path);
									new_value.setDistance(value.getDistance());
									newState.put(new_key, new_value);
								}
							}
						}
					} else if(oldState.get(key).getDistance() >= signal.get(key).getDistance()) {
						if (oldState.get(key).getPath().size() > signal.get(key).getPath().size()) {
							newState.put(key, signal.get(key));
						}
					}
				}
	    }
			
		return newState;
			
	}

}
