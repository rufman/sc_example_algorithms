/**
 *  Copyright 2012 Stephane Rufer
 *  
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *         http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  
 */
package ch.uzh.ifi.ddis.clustering_coefficient;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.signalcollect.Edge;
import com.signalcollect.ExecutionInformation;
import com.signalcollect.Graph;
import com.signalcollect.StateForwarderEdge;
import com.signalcollect.Vertex;
import com.signalcollect.configuration.ExecutionMode;
import com.signalcollect.javaapi.*;

/**
 * @author: Stephane Rufer The University of Z&uuml;rich<br>
 * 
 *          Date: Mar 11, 2012 Package: ch.uzh.ifi.ddis.clustering_coefficient
 *          
 *          Example Graph:
 *          
 *                  __3__          
 *                 /     \   
 *            1___2___5__4
 *                |  /| /
 *                |_/ |/
 *                6___7
 *                
 *          Clustering Coefficient Results per Vertex:
 *          	- 1: -
 *          	- 2: 0
 *          	- 3: 0.16667
 *          	- 4: 0.33334
 *          	- 5: 0.5
 *          	- 6: 0.66667
 *          	- 7: 0.66667
 */
public class ClusteringCoefficient {
	public static void main(String[] args) {
		Graph graph = GraphBuilder.build();

		ArrayList<Integer> l1 = new ArrayList<Integer>();
		l1.add(1);
		ArrayList<Integer> l2 = new ArrayList<Integer>();
		l2.add(2);
		ArrayList<Integer> l3 = new ArrayList<Integer>();
		l3.add(3);
		ArrayList<Integer> l4 = new ArrayList<Integer>();
		l4.add(4);
		ArrayList<Integer> l5 = new ArrayList<Integer>();
		l5.add(5);
		ArrayList<Integer> l6 = new ArrayList<Integer>();
		l6.add(6);
		ArrayList<Integer> l7 = new ArrayList<Integer>();
		l7.add(7);
		graph.addVertex(new ClusteringCoefficientVertex(1, l1));
		graph.addVertex(new ClusteringCoefficientVertex(2, l2));
		graph.addVertex(new ClusteringCoefficientVertex(3, l3));
		graph.addVertex(new ClusteringCoefficientVertex(4, l4));
		graph.addVertex(new ClusteringCoefficientVertex(5, l5));
		graph.addVertex(new ClusteringCoefficientVertex(6, l6));
		graph.addVertex(new ClusteringCoefficientVertex(7, l7));

		/*
		 * graph.addEdge(new StateForwarderEdge<Integer, Integer>(1,2));
		 * graph.addEdge(new StateForwarderEdge<Integer, Integer>(1,3));
		 * graph.addEdge(new StateForwarderEdge<Integer, Integer>(1,4));
		 * 
		 * graph.addEdge(new StateForwarderEdge<Integer, Integer>(2,3));
		 * graph.addEdge(new StateForwarderEdge<Integer, Integer>(2,1));
		 * graph.addEdge(new StateForwarderEdge<Integer, Integer>(2,4));
		 * 
		 * graph.addEdge(new StateForwarderEdge<Integer, Integer>(3,1));
		 * graph.addEdge(new StateForwarderEdge<Integer, Integer>(3,2));
		 * graph.addEdge(new StateForwarderEdge<Integer, Integer>(3,4));
		 * 
		 * graph.addEdge(new StateForwarderEdge<Integer, Integer>(4,1));
		 * graph.addEdge(new StateForwarderEdge<Integer, Integer>(4,2));
		 * graph.addEdge(new StateForwarderEdge<Integer, Integer>(4,3));
		 */

		graph.addEdge(new StateForwarderEdge<Integer, Integer>(1, 2));

		graph.addEdge(new StateForwarderEdge<Integer, Integer>(2, 3));
		graph.addEdge(new StateForwarderEdge<Integer, Integer>(2, 1));
		graph.addEdge(new StateForwarderEdge<Integer, Integer>(2, 6));
		graph.addEdge(new StateForwarderEdge<Integer, Integer>(2, 5));

		graph.addEdge(new StateForwarderEdge<Integer, Integer>(3, 2));
		graph.addEdge(new StateForwarderEdge<Integer, Integer>(3, 4));

		graph.addEdge(new StateForwarderEdge<Integer, Integer>(4, 7));
		graph.addEdge(new StateForwarderEdge<Integer, Integer>(4, 5));
		graph.addEdge(new StateForwarderEdge<Integer, Integer>(4, 3));

		graph.addEdge(new StateForwarderEdge<Integer, Integer>(5, 2));
		graph.addEdge(new StateForwarderEdge<Integer, Integer>(5, 4));
		graph.addEdge(new StateForwarderEdge<Integer, Integer>(5, 6));
		graph.addEdge(new StateForwarderEdge<Integer, Integer>(5, 7));

		graph.addEdge(new StateForwarderEdge<Integer, Integer>(6, 2));
		graph.addEdge(new StateForwarderEdge<Integer, Integer>(6, 5));
		graph.addEdge(new StateForwarderEdge<Integer, Integer>(6, 7));

		graph.addEdge(new StateForwarderEdge<Integer, Integer>(7, 4));
		graph.addEdge(new StateForwarderEdge<Integer, Integer>(7, 5));
		graph.addEdge(new StateForwarderEdge<Integer, Integer>(7, 6));

		ExecutionInformation stats = graph.execute(ExecutionConfiguration
				.withExecutionMode(ExecutionMode.Synchronous()).withStepsLimit(
						2));

		// ExecutionInformation stats = graph.execute();
		System.out.println(stats);

		// print the state of every vertex in the graph.
		graph.foreachVertex(FunUtil.convert(new VertexCommand() {
			public void f(Vertex v) {
				System.out.println(v);
			}
		}));

		// calculate the clustering coefficient by going through the whole graph
		graph.foreachVertex(FunUtil.convert(new VertexCommand() {
			public void f(Vertex v) {
				ArrayList<Integer> v_state = (ArrayList<Integer>) v.state();
				ArrayList<Integer> n_state = new ArrayList<Integer>();
				ArrayList<Integer> neighbors = new ArrayList<Integer>();

				// find all neighbors
				scala.collection.Iterator<scala.collection.Iterable<Edge>> it = v
						.getOutgoingEdges().iterator();
				while (it.hasNext()) {
					scala.collection.Iterator<Edge> eit = it.next().iterator();
					while (eit.hasNext()) {
						Edge e = eit.next();
						neighbors.add((Integer) e.id().targetId());
					}
				}
				// find completed edges among neighbors
				n_state = (ArrayList<Integer>) v_state.clone();
				int count = 0;
				for (Integer v_i : v_state) {
					for (Integer n : neighbors) {
						if (v_i.equals(n)) {
							count++;
						}
					}
				}
				float possible_edges = (neighbors.size() - 1)
						* neighbors.size() / 2; // all possible edges in the
												// neighborhood of the vertex
				float completed_edges = (count - neighbors.size() * 2) / 2; // all
																			// edges
																			// that
																			// are
																			// completed
				float clustering_coefficient = completed_edges / possible_edges;
				System.out.println("vertex: " + v.id() + ",\tCompleted Edges: "
						+ completed_edges + ",\tPossible Edges: "
						+ possible_edges + ",\tClustering Coefficient: "
						+ clustering_coefficient);
			}
		}));
		graph.shutdown();
	}
}
