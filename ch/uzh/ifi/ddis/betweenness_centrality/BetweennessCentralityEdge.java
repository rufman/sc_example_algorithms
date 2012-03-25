package ch.uzh.ifi.ddis.betweenness_centrality;

import ch.uzh.ifi.ddis.clustering_coefficient.ClusteringCoefficientVertex;

import com.signalcollect.javaapi.DefaultEdge;

public class BetweennessCentralityEdge extends
		DefaultEdge<ClusteringCoefficientVertex> {

	BetweennessCentralityEdge(int sourceID, int targetID) {
		super(sourceID, targetID);
	}

	@Override
	public Object signal(ClusteringCoefficientVertex sourceVertex) {
		return sourceVertex.getState();
	}
}
