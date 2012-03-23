/**
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
 * @author: Stephane Rufer
 * The University of Z&uuml;rich<br>
 * 
 * Date: Mar 11, 2012
 * Package: ch.uzh.ifi.ddis.clustering_coefficient
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
         
         /*graph.addEdge(new StateForwarderEdge<Integer, Integer>(1,2));
         graph.addEdge(new StateForwarderEdge<Integer, Integer>(1,3));
         graph.addEdge(new StateForwarderEdge<Integer, Integer>(1,4));
         
         graph.addEdge(new StateForwarderEdge<Integer, Integer>(2,3));
         graph.addEdge(new StateForwarderEdge<Integer, Integer>(2,1));
         graph.addEdge(new StateForwarderEdge<Integer, Integer>(2,4));
         
         graph.addEdge(new StateForwarderEdge<Integer, Integer>(3,1));
         graph.addEdge(new StateForwarderEdge<Integer, Integer>(3,2));
         graph.addEdge(new StateForwarderEdge<Integer, Integer>(3,4));
         
         graph.addEdge(new StateForwarderEdge<Integer, Integer>(4,1));
         graph.addEdge(new StateForwarderEdge<Integer, Integer>(4,2));
         graph.addEdge(new StateForwarderEdge<Integer, Integer>(4,3));*/
         
         graph.addEdge(new StateForwarderEdge<Integer, Integer>(1,2));
         
         graph.addEdge(new StateForwarderEdge<Integer, Integer>(2,3));
         graph.addEdge(new StateForwarderEdge<Integer, Integer>(2,1));
         graph.addEdge(new StateForwarderEdge<Integer, Integer>(2,6));
         graph.addEdge(new StateForwarderEdge<Integer, Integer>(2,5));
         
         graph.addEdge(new StateForwarderEdge<Integer, Integer>(3,2));
         graph.addEdge(new StateForwarderEdge<Integer, Integer>(3,4));
         
         graph.addEdge(new StateForwarderEdge<Integer, Integer>(4,7));
         graph.addEdge(new StateForwarderEdge<Integer, Integer>(4,5));
         graph.addEdge(new StateForwarderEdge<Integer, Integer>(4,3));
         
         graph.addEdge(new StateForwarderEdge<Integer, Integer>(5,2));
         graph.addEdge(new StateForwarderEdge<Integer, Integer>(5,4));
         graph.addEdge(new StateForwarderEdge<Integer, Integer>(5,6));
         graph.addEdge(new StateForwarderEdge<Integer, Integer>(5,7));
         
         graph.addEdge(new StateForwarderEdge<Integer, Integer>(6,2));
         graph.addEdge(new StateForwarderEdge<Integer, Integer>(6,5));
         graph.addEdge(new StateForwarderEdge<Integer, Integer>(6,7));
         
         graph.addEdge(new StateForwarderEdge<Integer, Integer>(7,4));
         graph.addEdge(new StateForwarderEdge<Integer, Integer>(7,5));
         graph.addEdge(new StateForwarderEdge<Integer, Integer>(7,6));
         
         ExecutionInformation stats = graph.execute(ExecutionConfiguration
 				.withExecutionMode(ExecutionMode.Synchronous()).withStepsLimit(
 						2));
         
         /*graph.foreachVertex(FunUtil.convert(new VertexCommand(){
        		 public void f(Vertex v) {
        			 ArrayList<Integer> list = new ArrayList<Integer>();
        			 scala.collection.Iterator<scala.collection.Iterable<Edge>> it = v.getOutgoingEdges().iterator();
        			 while (it.hasNext()){
        				 scala.collection.Iterator<Edge> eit = it.next().iterator();
        				 while (eit.hasNext()){
        					 Edge e = eit.next();
        					 list.add((Integer)e.id().targetId());
        				 }
        			 }
        			 //list.add((Integer)v.id());
        			 ClusteringCoefficientVertex v2 = (ClusteringCoefficientVertex) v;
        			 v2.initialize_state(list); 
        		 }
         }));*/
         
         //ExecutionInformation stats = graph.execute();
         System.out.println(stats);
         
         //print the state of every vertex in the graph.
         graph.foreachVertex(FunUtil.convert(new VertexCommand(){
             public void f(Vertex v) {
                     System.out.println(v);
             }
         }));
         
         graph.foreachVertex(FunUtil.convert(new VertexCommand(){
    		 public void f(Vertex v) {
    			 ArrayList<Integer> v_state = (ArrayList<Integer>) v.state();
    			 ArrayList<Integer> n_state = new ArrayList<Integer>();
    			 ArrayList<Integer> neighbors = new ArrayList<Integer>();
    			 
    			 scala.collection.Iterator<scala.collection.Iterable<Edge>> it = v.getOutgoingEdges().iterator();
    			 while (it.hasNext()){
    				 scala.collection.Iterator<Edge> eit = it.next().iterator();
    				 while (eit.hasNext()){
    					 Edge e = eit.next();
    					 neighbors.add((Integer)e.id().targetId());
    				 }
    			 }
    			 n_state = (ArrayList<Integer>) v_state.clone();
    			 int count = 0;
    			 for (Integer v_i : v_state){
    				 for (Integer n : neighbors){
	    				 if(v_i.equals(n)){
	    					 count++;
	    				 }
    				 }
    			 }
    			 float possible_edges = (neighbors.size()-1)*neighbors.size()/2;
    			 float completed_edges = (count-neighbors.size()*2)/2;
    			 float clustering_coefficient = completed_edges/possible_edges;
    			 System.out.println("vertex: "+v.id()
    					 			+",\tCompleted Edges: "+completed_edges
    					 			+",\tPossible Edges: "+possible_edges
    					 			+",\tClustering Coefficient: "+clustering_coefficient);
    		 }
         }));
         graph.shutdown();
	 }
}
