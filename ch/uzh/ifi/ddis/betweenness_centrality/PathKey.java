/**
 *
 */
package ch.uzh.ifi.ddis.betweenness_centrality;

import java.io.Serializable;

/**
 * @author: Stephane Rufer
 * The University of Z&uuml;rich<br>
 * 
 * Date: Mar 29, 2012
 * Package: ch.uzh.ifi.ddis.betweenness_centrality
 */
public class PathKey implements Serializable{
	private Integer source_id;
	private Integer target_id;
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		
		PathKey that = (PathKey) o;
		if (source_id != null ? !source_id.equals(that.source_id) : that.source_id != null) return false;
		if (target_id != null ? !target_id.equals(that.target_id) : that.target_id != null) return false;
		
		return true;
	}
	
	@Override
	public int hashCode() {
		int result = 17;
		result = 37*result + source_id.hashCode();
		result = 37*result + target_id.hashCode();
		
		return result;
	}
	
	@Override
	public String toString(){
		return "["+source_id.toString()+";"+target_id.toString()+"]";
	}
	
	//Getters and Setters
	public Integer getSourceId(){
		return source_id;
	}
	
	public Integer getTargetId(){
		return target_id;
	}

	public void setSourceId(Integer id){
		source_id = id;
	}
	
	public void setTargetId(Integer id){
		target_id = id;
	}

}
