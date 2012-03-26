/**
 *
 */
package ch.uzh.ifi.ddis.structural_cohesion;

import java.util.Arrays;
import java.util.HashMap;

import com.signalcollect.javaapi.DataGraphVertex;

/**
 * @author: Stephane Rufer
 * The University of Z&uuml;rich<br>
 * 
 * Date: Mar 11, 2012
 * Package: ch.uzh.ifi.ddis.structural_cohesion
 */
public class StructuralCohesionVertex
		extends
		DataGraphVertex<Integer, Integer, Integer> {
	
	public StructuralCohesionVertex(Integer id, Integer state){
		super(id, state);
	}

	public Integer collect(Integer oldState, Iterable<Integer> mostRecentSignals){
		int newState = 0;
		int max_degree = 0;
		HashMap<Integer,Integer> k_coreness = new HashMap<Integer,Integer>();
		int array_pos = 0;
		int[] num_connections;
		if (oldState.equals(-1)) {
			num_connections = null;
		} else {
			num_connections = new int[oldState];
		}
		
		for (Integer neighbor_degree : mostRecentSignals){
			if (neighbor_degree == -1){
				newState = this.incomingEdgeCount();
			} else {
				Integer k_core_array_pos = k_coreness.get(neighbor_degree);
				if (k_core_array_pos == null){
					k_coreness.put(neighbor_degree, array_pos);
					num_connections[array_pos] = 1;
					array_pos++;
					if (neighbor_degree > max_degree){
						max_degree = neighbor_degree;
					}
				} else {
					num_connections[k_core_array_pos]++;
				}
			}
		}
		
		
			
			int connections = 0;
			int same_offset = 0;
			for (int k=max_degree;k>0;k--){
				Integer pos = k_coreness.get(k);
				if (pos != null){
					connections = connections + num_connections[pos];
					/*if (pos.equals(k)) {
						same_offset = num_connections[pos];
					} else {
						same_offset = 0;
					}
				} else {*/
					same_offset = 0;
				}
				if ((connections - k) >= 0) {
					newState = k;
					break;
				}
			}
		
		
		return newState;
	}
}
