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
	
	private int threshold;
	
	public StructuralCohesionVertex(Integer id, Integer state){
		super(id, state);
		this.threshold = 0;
	}

	public Integer collect(Integer oldState, Iterable<Integer> mostRecentSignals){
		int count = 0;
		for (Integer neighbor_degree : mostRecentSignals){
			if (neighbor_degree > threshold || neighbor_degree == -1 ) {
				count++;
			}
		}
		
		if (count > threshold) {
			oldState = count;
			threshold++;
			return oldState;
		} else {
			oldState = threshold;
			return oldState;
		}
	}
}
