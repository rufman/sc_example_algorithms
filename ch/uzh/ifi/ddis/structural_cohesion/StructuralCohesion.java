/**
 *
 */
package ch.uzh.ifi.ddis.structural_cohesion;

import com.signalcollect.Graph;
import com.signalcollect.Vertex;
import com.signalcollect.Edge;
import com.signalcollect.ExecutionInformation;
import com.signalcollect.StateForwarderEdge;
import com.signalcollect.configuration.ExecutionMode;
import com.signalcollect.javaapi.*;

/**
 * @author: Stephane Rufer
 * The University of Z&uuml;rich<br>
 * 
 * Date: Mar 11, 2012
 * Package: ch.uzh.ifi.ddis.structural_cohesion
 */
public class StructuralCohesion {
	public static void main(String[] args) {
		Graph graph = GraphBuilder.build();	
		
		graph.addVertex(new StructuralCohesionVertex(1, -1));
		graph.addVertex(new StructuralCohesionVertex(2, -1));
		graph.addVertex(new StructuralCohesionVertex(3, -1));
		graph.addVertex(new StructuralCohesionVertex(4, -1));
		graph.addVertex(new StructuralCohesionVertex(5, -1));
		graph.addVertex(new StructuralCohesionVertex(6, -1));
		graph.addVertex(new StructuralCohesionVertex(7, -1));
		graph.addVertex(new StructuralCohesionVertex(8, -1));
		graph.addVertex(new StructuralCohesionVertex(9, -1));
		graph.addVertex(new StructuralCohesionVertex(10, -1));
		graph.addVertex(new StructuralCohesionVertex(11, -1));
		graph.addVertex(new StructuralCohesionVertex(12, -1));
		graph.addVertex(new StructuralCohesionVertex(13, -1));
		graph.addVertex(new StructuralCohesionVertex(14, -1));
		graph.addVertex(new StructuralCohesionVertex(15, -1));
		
	    
	    graph.addEdge(new StateForwarderEdge<Integer, Integer>(1,5));
	    graph.addEdge(new StateForwarderEdge<Integer, Integer>(2,5));
	    graph.addEdge(new StateForwarderEdge<Integer, Integer>(3,5));
	    graph.addEdge(new StateForwarderEdge<Integer, Integer>(4,5));
	    graph.addEdge(new StateForwarderEdge<Integer, Integer>(5,1));
	    graph.addEdge(new StateForwarderEdge<Integer, Integer>(5,2));
	    graph.addEdge(new StateForwarderEdge<Integer, Integer>(5,3));
	    graph.addEdge(new StateForwarderEdge<Integer, Integer>(5,4));
	    
	    graph.addEdge(new StateForwarderEdge<Integer, Integer>(5,6));
	    graph.addEdge(new StateForwarderEdge<Integer, Integer>(6,5));
	    
	    graph.addEdge(new StateForwarderEdge<Integer, Integer>(6,7));
	    graph.addEdge(new StateForwarderEdge<Integer, Integer>(6,8));
	    graph.addEdge(new StateForwarderEdge<Integer, Integer>(6,12));
	    graph.addEdge(new StateForwarderEdge<Integer, Integer>(6,15));
	    graph.addEdge(new StateForwarderEdge<Integer, Integer>(7,6));
	    graph.addEdge(new StateForwarderEdge<Integer, Integer>(8,6));
	    graph.addEdge(new StateForwarderEdge<Integer, Integer>(12,6));
	    graph.addEdge(new StateForwarderEdge<Integer, Integer>(15,6));
	    
	    graph.addEdge(new StateForwarderEdge<Integer, Integer>(7,8));
	    graph.addEdge(new StateForwarderEdge<Integer, Integer>(8,7));
	    
	    graph.addEdge(new StateForwarderEdge<Integer, Integer>(8,15));
	    graph.addEdge(new StateForwarderEdge<Integer, Integer>(8,12));
	    graph.addEdge(new StateForwarderEdge<Integer, Integer>(8,9));
	    graph.addEdge(new StateForwarderEdge<Integer, Integer>(15,8));
	    graph.addEdge(new StateForwarderEdge<Integer, Integer>(12,8));
	    graph.addEdge(new StateForwarderEdge<Integer, Integer>(9,8));
	    
	    graph.addEdge(new StateForwarderEdge<Integer, Integer>(9,10));
	    graph.addEdge(new StateForwarderEdge<Integer, Integer>(9,12));
	    graph.addEdge(new StateForwarderEdge<Integer, Integer>(10,9));
	    graph.addEdge(new StateForwarderEdge<Integer, Integer>(12,9));
	    
	    graph.addEdge(new StateForwarderEdge<Integer, Integer>(10,11));
	    graph.addEdge(new StateForwarderEdge<Integer, Integer>(11,10));
	    
	    graph.addEdge(new StateForwarderEdge<Integer, Integer>(11,12));
	    graph.addEdge(new StateForwarderEdge<Integer, Integer>(12,11));
	    
	    graph.addEdge(new StateForwarderEdge<Integer, Integer>(12,13));
	    graph.addEdge(new StateForwarderEdge<Integer, Integer>(12,15));
	    graph.addEdge(new StateForwarderEdge<Integer, Integer>(13,12));
	    graph.addEdge(new StateForwarderEdge<Integer, Integer>(15,12));
	    
	    graph.addEdge(new StateForwarderEdge<Integer, Integer>(13,14));
	    graph.addEdge(new StateForwarderEdge<Integer, Integer>(13,15));
	    graph.addEdge(new StateForwarderEdge<Integer, Integer>(14,13));
	    graph.addEdge(new StateForwarderEdge<Integer, Integer>(15,13));
	    
	    ExecutionInformation stats = graph.execute(ExecutionConfiguration
				.withExecutionMode(ExecutionMode.Synchronous()));
	    
	    //ExecutionInformation stats = graph.execute();
	    System.out.println(stats);
	    
	    //print the state of every vertex in the graph.
	    graph.foreachVertex(FunUtil.convert(new VertexCommand(){
	        public void f(Vertex v) {
	                System.out.println(v);
	        }
	    }));
	    
	    graph.shutdown();
	}
}
