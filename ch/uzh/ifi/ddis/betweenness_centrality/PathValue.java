/**
 *
 */
package ch.uzh.ifi.ddis.betweenness_centrality;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author: Stephane Rufer
 * The University of Z&uuml;rich<br>
 * 
 * Date: Mar 29, 2012
 * Package: ch.uzh.ifi.ddis.betweenness_centrality
 */
public class PathValue implements Serializable{
	private Set<Integer> key;
	private Set<Integer> path;
	private float distance;
	
	public PathValue() {
		key = new HashSet<Integer>();
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		
		PathValue that = (PathValue) o;
		if (key != null ? !key.equals(that.key) : that.key != null) return false;
		if (path != null ? !path.equals(that.path) : that.path != null) return false;
		if (distance != that.distance) return false;
		
		return true;
	}
	
	@Override
	public int hashCode() {
		int result = 17;
		result = 37*result + key.hashCode();
		result = 37*result + path.hashCode();
		result = 37*result + (int) distance;
		
		return result;
	}
	
	@Override
	public String toString(){
		return "key: "+key.toString()+"; path: "+path.toString()+"; distance: "+distance;
	}
	
	//Getters and Setters
	public Set<Integer> getKey(){
		return key;
	}
	
	public Set<Integer> getPath(){
		return path;
	}
	
	public float getDistance(){
		return distance;
	}

	public void setKey(Set<Integer> key){
		this.key = key;
	}
	
	public void setPath(Set<Integer> path){
		this.path = path;
	}
	
	public void setDistance(float distance){
		this.distance = distance;
	}
	
}
