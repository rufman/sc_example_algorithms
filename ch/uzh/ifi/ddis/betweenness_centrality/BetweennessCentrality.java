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
        
		for(int i=1;i<=6;i++){
			HashMap<Set<Integer>,PathValue> hm = new HashMap<Set<Integer>,PathValue>();
			Set<Integer> key = new HashSet<Integer>();
			key.add(i);
			PathValue value = new PathValue();
	        value.setKey(key);
	        Set<Integer> path = new HashSet<Integer>();
	        path.add(i);
	        value.setPath(path);
	        value.setDistance(0);
	        hm.put(key, value);
	        graph.addVertex(new BetweennessCentralityVertex(i, hm));
		}
        
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
        final HashMap<Set<Integer>,PathValue> globalShortestPaths = graph.aggregate(new GetGlobalShortestPaths());
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
            		}else if (globalShortestPaths.get(key).getPath().contains(v.id())) {
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
	private class GetGlobalShortestPaths implements AggregationOperation<HashMap<Set<Integer>,PathValue>> {

		@Override
		public HashMap<Set<Integer>,PathValue> aggregate(
				HashMap<Set<Integer>,PathValue> arg0,
				HashMap<Set<Integer>,PathValue> arg1) {
			HashMap<Set<Integer>,PathValue> state0 = (HashMap<Set<Integer>,PathValue>) ((HashMap) arg0).clone();
			HashMap<Set<Integer>,PathValue> state1 = (HashMap<Set<Integer>,PathValue>) ((HashMap) arg1).clone();
			state0.putAll(state1);
			return state0;
		}

		@Override
		public HashMap<Set<Integer>,PathValue> extract(Vertex arg0) {
			return (HashMap<Set<Integer>,PathValue>) ((HashMap) arg0.state()).clone();
		}

		@Override
		public HashMap<Set<Integer>,PathValue> neutralElement() {
			return new HashMap<Set<Integer>,PathValue>();
		}

	}
}
