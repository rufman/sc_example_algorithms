package ch.uzh.ifi.ddis.clustering_coefficient;

import java.util.ArrayList;
import com.signalcollect.javaapi.DataGraphVertex;

/**
 * @author: Stephane Rufer
 * The University of Z&uuml;rich<br>
 * 
 * Date: Mar 11, 2012
 * Package: ch.uzh.ifi.ddis.clustering_coefficient
 */
public class ClusteringCoefficientVertex
		extends
		DataGraphVertex<Integer, ArrayList<Integer>, ArrayList<Integer>> {
	
	public ClusteringCoefficientVertex(Integer id, ArrayList<Integer> state){
		super(id, state);
	}
	
	public void initialize_state(ArrayList<Integer> state){
		this.setState(state);
	}

	public ArrayList<Integer> collect(ArrayList<Integer> oldState, 
										Iterable<ArrayList<Integer>> mostRecentSignals){
		ArrayList<Integer> newState = (ArrayList<Integer>) (((ArrayList<Integer>) oldState).clone());
		
		/*ArrayList<Integer> list = new ArrayList<Integer>();
		scala.collection.Iterator<scala.collection.Iterable<Edge>> it = this.getOutgoingEdges().iterator();
		while (it.hasNext()){
			 scala.collection.Iterator<Edge> eit = it.next().iterator();
			 while (eit.hasNext()){
				 Edge e = eit.next();
				 list.add((Integer)e.id().targetId());
			 }
		}*/
		
		
		for (ArrayList<Integer> neighbors : mostRecentSignals){
			for (Integer n : neighbors){
				newState.add(n);
			}
				/*Iterator<ClusteringCoefficientVertex> it = list.iterator();
				while (it.hasNext()){
					ClusteringCoefficientVertex v = it.next();
					if (! newState.contains(v)){
						newState.add(v);
					}
				}*/
		}
			
		return newState;
			
	}
}
