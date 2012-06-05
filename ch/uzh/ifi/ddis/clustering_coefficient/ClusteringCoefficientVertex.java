package ch.uzh.ifi.ddis.clustering_coefficient;

import java.util.ArrayList;
import com.signalcollect.javaapi.DataGraphVertex;

/**
 * @author: Stephane Rufer The University of Z&uuml;rich<br>
 * 
 *          Date: Mar 11, 2012 Package: ch.uzh.ifi.ddis.clustering_coefficient
 */
public class ClusteringCoefficientVertex extends
		DataGraphVertex<Integer, ArrayList<Integer>, ArrayList<Integer>> {

	/**
	 * Constructor for the vertex. Initializes the vertex with the given state
	 * 
	 * @param id
	 *            The unique id of the vertex
	 * @param state
	 *            The initial vertex state
	 */
	public ClusteringCoefficientVertex(Integer id, ArrayList<Integer> state) {
		super(id, state);
	}

	public void initialize_state(ArrayList<Integer> state) {
		this.setState(state);
	}

	/**
	 * The collect function for the vertex. This sets the state of the vertex,
	 * so that it represents the immediate neighborhood of the vertex.
	 */
	public ArrayList<Integer> collect(ArrayList<Integer> oldState,
			Iterable<ArrayList<Integer>> mostRecentSignals) {
		ArrayList<Integer> newState = (ArrayList<Integer>) (((ArrayList<Integer>) oldState)
				.clone());

		for (ArrayList<Integer> neighbors : mostRecentSignals) {
			for (Integer n : neighbors) {
				newState.add(n);
			}
		}

		return newState;

	}
}
