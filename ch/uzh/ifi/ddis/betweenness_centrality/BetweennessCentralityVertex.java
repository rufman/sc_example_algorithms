/**
 *
 */
package ch.uzh.ifi.ddis.betweenness_centrality;

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
		DataGraphVertex<Integer, HashMap<Set<Integer>,Set<Integer>>, HashMap<Set<Integer>,Set<Integer>>> {
	
	public BetweennessCentralityVertex(Integer id, HashMap<Set<Integer>,Set<Integer>> state){
		super(id, state);
	}

	public HashMap<Set<Integer>,Set<Integer>> collect(HashMap<Set<Integer>,Set<Integer>> oldState, 
													  Iterable<HashMap<Set<Integer>,Set<Integer>>> mostRecentSignals){
		HashMap<Set<Integer>,Set<Integer>> newState = (HashMap) ((HashMap) oldState).clone();
		
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
		
		for (HashMap<Set<Integer>,Set<Integer>> element : mostRecentSignals) {
			//for (HashMap<Set<Integer>,Set<Integer>> element : signal) {
				for (Set<Integer> key : element.keySet()) {
					if (oldState.get(key) == null) {
						//newState.put(key, element.get(key));
												
						for (Integer n : neighbors){
							if (element.get(key).contains(n)) {
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
									Set<Integer> new_value = new HashSet<Integer>();
									new_value.add(this.id()); 
									new_value.add(n);
									newState.put(new_key, new_value);
								} else if (new_vertex == -1){
									newState.put(key, element.get(key));
								} else {
									Set<Integer> new_key = new HashSet<Integer>();
									new_key.add(this.id());
									new_key.add(new_vertex);
									Set<Integer> new_value = new HashSet<Integer>();
									new_value.add(this.id()); 
									for (Integer i : element.get(key)){
										new_value.add(i); 
									}
									newState.put(new_key, new_value);
								}
							}
						}
					} else if(oldState.get(key).size() > element.get(key).size()) {
						newState.put(key, element.get(key));
					}
				}
			//}
	    }
			
		return newState;
			
	}

}
