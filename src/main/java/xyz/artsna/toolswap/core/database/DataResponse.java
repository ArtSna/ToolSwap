package xyz.artsna.toolswap.core.database;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class DataResponse {

	private final List<Row> result;
	
	public DataResponse(List<Row> result) {
		this.result = result;
	}
	
	public Row first() {
		return result.getFirst();
	}
	
	public Row last() {
		return result.getLast();
	}
	
	public List<Row> all() {
		return Collections.unmodifiableList(result);
	}
	
	public boolean isEmpty() {
		return result.isEmpty();
	}
	
	public Row get(int index) {
		return result.get(index);
	}
	
	public Stream<Row> stream() {
		return result.stream();
	}
	
	@Override
	public String toString() {
		StringBuilder response = new StringBuilder("DataResponse = [");
		
		for(Row value : result) {
			response.append(value.toString()).append(", ");
		}
				
		return response.substring(0, response.length() - 2) + "]";
	}
}
