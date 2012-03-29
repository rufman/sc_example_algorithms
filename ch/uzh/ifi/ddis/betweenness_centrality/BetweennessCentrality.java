/**
 *
 */
package ch.uzh.ifi.ddis.betweenness_centrality;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.signalcollect.ExecutionInformation;
import com.signalcollect.interfaces.AggregationOperation;
import com.signalcollect.Graph;
import com.signalcollect.ReduceStatesOperation;
import com.signalcollect.StateForwarderEdge;
import com.signalcollect.Vertex;
import com.signalcollect.configuration.ExecutionMode;
import com.signalcollect.javaapi.ExecutionConfiguration;
import com.signalcollect.javaapi.FunUtil;
import com.signalcollect.javaapi.GraphBuilder;
import com.signalcollect.javaapi.VertexCommand;

/**
 * @author: Stephane Rufer
 * The University of Z&uuml;rich<br>
 * 
 * Date: Mar 11, 2012
 * Package: ch.uzh.ifi.ddis.betweenness_centrality
 */
public class BetweennessCentrality {
	
	public void execute_graph(){
		Graph graph = GraphBuilder.build();
        
        HashMap<Set<Integer>,Set<Integer>> l1 = new HashMap<Set<Integer>,Set<Integer>>();
        Set<Integer> l1_key = new HashSet<Integer>();
        l1_key.add(1);
        l1_key.add(1);
        Set<Integer> l1_value = new HashSet<Integer>();
        l1_value.add(1);
        l1.put(l1_key, l1_key);
        
        HashMap<Set<Integer>,Set<Integer>> l2 = new HashMap<Set<Integer>,Set<Integer>>();
        Set<Integer> l2_key = new HashSet<Integer>();
        l2_key.add(2);
        l2_key.add(2);
        Set<Integer> l2_value = new HashSet<Integer>();
        l2_value.add(2);
        l2.put(l2_key, l2_key);
        
        HashMap<Set<Integer>,Set<Integer>> l3 = new HashMap<Set<Integer>,Set<Integer>>();
        Set<Integer> l3_key = new HashSet<Integer>();
        l3_key.add(3);
        l3_key.add(3);
        Set<Integer> l3_value = new HashSet<Integer>();
        l3_value.add(3);
        l3.put(l3_key, l3_key);
        
        HashMap<Set<Integer>,Set<Integer>> l4 = new HashMap<Set<Integer>,Set<Integer>>();
        Set<Integer> l4_key = new HashSet<Integer>();
        l4_key.add(4);
        l4_key.add(4);
        Set<Integer> l4_value = new HashSet<Integer>();
        l4_value.add(4);
        l4.put(l4_key, l4_key);
        
        HashMap<Set<Integer>,Set<Integer>> l5 = new HashMap<Set<Integer>,Set<Integer>>();
        Set<Integer> l5_key = new HashSet<Integer>();
        l5_key.add(5);
        l5_key.add(5);
        Set<Integer> l5_value = new HashSet<Integer>();
        l5_value.add(5);
        l5.put(l5_key, l5_key);
        
        HashMap<Set<Integer>,Set<Integer>> l6 = new HashMap<Set<Integer>,Set<Integer>>();
        Set<Integer> l6_key = new HashSet<Integer>();
        l6_key.add(6);
        l6_key.add(6);
        Set<Integer> l6_value = new HashSet<Integer>();
        l6_value.add(6);
        l6.put(l6_key, l6_key);
        
        HashMap<Set<Integer>,Set<Integer>> l7 = new HashMap<Set<Integer>,Set<Integer>>();
        Set<Integer> l7_key = new HashSet<Integer>();
        l7_key.add(7);
        l7_key.add(7);
        Set<Integer> l7_value = new HashSet<Integer>();
        l7_value.add(7);
        l7.put(l7_key, l7_key);
        
        HashMap<Set<Integer>,Set<Integer>> l8 = new HashMap<Set<Integer>,Set<Integer>>();
        Set<Integer> l8_key = new HashSet<Integer>();
        l8_key.add(8);
        l8_key.add(8);
        Set<Integer> l8_value = new HashSet<Integer>();
        l8_value.add(8);
        l8.put(l8_key, l8_key);
        graph.addVertex(new BetweennessCentralityVertex(1, l1));
        graph.addVertex(new BetweennessCentralityVertex(2, l2));
        graph.addVertex(new BetweennessCentralityVertex(3, l3));
        graph.addVertex(new BetweennessCentralityVertex(4, l4));
        graph.addVertex(new BetweennessCentralityVertex(5, l5));
        graph.addVertex(new BetweennessCentralityVertex(6, l6));
        
        graph.addEdge(new StateForwarderEdge<Integer, Integer>(1,2));
        
        graph.addEdge(new StateForwarderEdge<Integer, Integer>(2,3));
        graph.addEdge(new StateForwarderEdge<Integer, Integer>(2,1));
        graph.addEdge(new StateForwarderEdge<Integer, Integer>(2,4));
        
        graph.addEdge(new StateForwarderEdge<Integer, Integer>(3,5));
        graph.addEdge(new StateForwarderEdge<Integer, Integer>(3,2));
        graph.addEdge(new StateForwarderEdge<Integer, Integer>(3,4));
        
        graph.addEdge(new StateForwarderEdge<Integer, Integer>(4,2));
        graph.addEdge(new StateForwarderEdge<Integer, Integer>(4,3));
        
        graph.addEdge(new StateForwarderEdge<Integer, Integer>(5,6));
        graph.addEdge(new StateForwarderEdge<Integer, Integer>(5,3));
        
        graph.addEdge(new StateForwarderEdge<Integer, Integer>(6,5));
        
        ExecutionInformation stats = graph.execute();
        
        System.out.println(stats);
        
        //get global shortest paths
        final HashMap<Set<Integer>, Set<Integer>> globalShortestPaths = graph.aggregate(new GetGlobalShortestPaths());
        System.out.println("Global Shortest Paths: "+globalShortestPaths);
        System.out.println("Global Shortest Paths Size: "+globalShortestPaths.size());
        
        //print the state of every vertex in the graph.
        graph.foreachVertex(FunUtil.convert(new VertexCommand(){
            public void f(Vertex v) {
                    System.out.println(v);
            }
        }));
        
        //print the state of every vertex in the graph.
        graph.foreachVertex(FunUtil.convert(new VertexCommand(){
            public void f(Vertex v) {
            	HashMap<Set<Integer>, Set<Integer>> v_state = (HashMap) v.state();
            	int v_on_shortest_path = 0;
            	int v_global_minus = 0;
            	
            	for (Set<Integer> key : globalShortestPaths.keySet()){
            		if (key.contains(v.id())) {
            			v_global_minus++;
            		}else if (globalShortestPaths.get(key).contains(v.id())) {
            			v_on_shortest_path++;
            		}
            	}
            	float bc = (float) v_on_shortest_path/ (float) (globalShortestPaths.size()-v_global_minus);
            	System.out.println("Vertex: "+v.id()+" COUNT: "+v_state.size()+" SP: "+v_on_shortest_path+" GL: "+(globalShortestPaths.size()-v_global_minus)+" BC: "+bc);
            }
        }));
        graph.shutdown();
	}
	
	public static void main(String[] args) {
        BetweennessCentrality bc = new BetweennessCentrality();
        bc.execute_graph();
	}
	
	
	//Inner class to aggregate global shortest paths in the graph
	private class GetGlobalShortestPaths implements AggregationOperation<HashMap<Set<Integer>, Set<Integer>>> {

		@Override
		public HashMap<Set<Integer>, Set<Integer>> aggregate(
				HashMap<Set<Integer>, Set<Integer>> arg0,
				HashMap<Set<Integer>, Set<Integer>> arg1) {
			HashMap<Set<Integer>, Set<Integer>> state0 = (HashMap<Set<Integer>, Set<Integer>>) ((HashMap) arg0).clone();
			HashMap<Set<Integer>, Set<Integer>> state1 = (HashMap<Set<Integer>, Set<Integer>>) ((HashMap) arg1).clone();
			state0.putAll(state1);
			return state0;
		}

		@Override
		public HashMap<Set<Integer>, Set<Integer>> extract(Vertex arg0) {
			return (HashMap<Set<Integer>, Set<Integer>>) ((HashMap) arg0.state()).clone();
		}

		@Override
		public HashMap<Set<Integer>, Set<Integer>> neutralElement() {
			return new HashMap<Set<Integer>, Set<Integer>>();
		}

	}
}
