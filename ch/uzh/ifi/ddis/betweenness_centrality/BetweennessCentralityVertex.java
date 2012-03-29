/**
 *
 */
package ch.uzh.ifi.ddis.betweenness_centrality;
import java.util.Arrays;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.signalcollect.Edge;
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
		DataGraphVertex<Integer, HashMap<PathKey,PathValue>, HashMap<PathKey,PathValue>> {
	
	public BetweennessCentralityVertex(Integer id, HashMap<PathKey,PathValue> state){
		super(id, state);
	}

	public HashMap<PathKey,PathValue> collect(HashMap<PathKey,PathValue> oldState, 
													  Iterable<HashMap<PathKey,PathValue>> mostRecentSignals){
		HashMap<PathKey,PathValue> newState = (HashMap<PathKey,PathValue>) ((HashMap) oldState).clone();
		
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
		
		for (HashMap<PathKey,PathValue> signal : mostRecentSignals) {
				for (PathKey key : signal.keySet()) {
					if (oldState.get(key) == null) {
						PathValue value = signal.get(key);
						if (value.getPath().contains(this.id())) {
							newState.put(key, signal.get(key));
						}
						for (Integer n : neighbors){
							if (value.getPath().contains(n)) {							
								if (n.equals(key.getSourceId()) && n.equals(key.getTargetId())) {
									PathKey new_key = new PathKey();
									new_key.setSourceId(this.id());
									new_key.setTargetId(n);
									PathValue new_value = new PathValue();
									Set<Integer> path = new HashSet<Integer>();
									path.add(this.id());
									path.add(n);
									new_value.setKey(new_key);
									new_value.setPath(path);
									new_value.setDistance(value.getDistance()+1);
									newState.put(new_key, new_value);
								} else {
									int new_vertex = -1;
									if (!n.equals(key.getSourceId()) && n.equals(key.getTargetId())) {
										new_vertex = key.getSourceId();
									} else if (!n.equals(key.getTargetId()) && n.equals(key.getSourceId())) {
										new_vertex = key.getTargetId();
									} else {
										newState.put(key, signal.get(key));
									}
									
									if (new_vertex != -1){
										PathKey new_key = new PathKey();
										new_key.setSourceId(this.id());
										new_key.setTargetId(new_vertex);
										PathValue new_value = new PathValue();
										Set<Integer> path = new HashSet<Integer>();
										path.add(this.id());
										for (Integer i : value.getPath()){
											path.add(i); 
										}
										new_value.setKey(new_key);
										new_value.setPath(path);
										new_value.setDistance(value.getDistance()+1);
										newState.put(new_key, new_value);
									}
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
