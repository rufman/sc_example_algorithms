/**
 *
 */
package ch.uzh.ifi.ddis.k_core;

import java.util.Arrays;
import java.util.HashMap;

import com.signalcollect.javaapi.DataGraphVertex;

/**
 * @author: Stephane Rufer The University of Z&uuml;rich<br>
 * 
 *          Date: Mar 18, 2012 Package: ch.uzh.ifi.ddis.k_core
 */
public class KCoreVertex extends DataGraphVertex<Integer, Integer, Integer> {

	private int threshold; // indication of the stage of the computation

	/**
	 * Constructor for the kcore vertex class. This initializes the state of the
	 * vertex at the beginning of the computation.
	 * 
	 * This initializes the threshold at 0. The threshold is used to determine
	 * in what stage the computation is in. All k cores below the current
	 * threshold are ignored in that signal/collect step.
	 * 
	 * @param id
	 *            The unique vertex id
	 * @param state
	 *            The initial state of the vertex
	 */
	public KCoreVertex(Integer id, Integer state) {
		super(id, state);
		this.threshold = 0;
	}

	/**
	 * The collect method that calculates the kcore of the vertex. The kcore is
	 * represented by the vertex state.
	 */
	public Integer collect(Integer oldState, Iterable<Integer> mostRecentSignals) {
		int count = 0;
		// count the number of neighbors above the threshold. These are the
		// neighbors relevant to the computation at this step
		for (Integer neighbor_degree : mostRecentSignals) {
			if (neighbor_degree > threshold || neighbor_degree == -1) {
				count++;
			}
		}

		// new threshold is one k core higher.
		// The new state of this vertex is the 
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
