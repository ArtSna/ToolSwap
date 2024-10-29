package xyz.artsna.toolswap.core.database;

import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

public class Row {
	
	private final Map<String, Object> values;
	
	public Row(Map<String, Object> values) {
		this.values = values;
	}
	
	public Object get(String column) {
		return values.get(column);
	}
	
	public String getString(String column) {
		return get(column).toString();
	}
	
	public Integer getInteger(String column) {
		try {
			return Integer.parseInt(getString(column));
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	public UUID getUUID(String column) {
		try {
			return UUID.fromString(getString(column));
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	public Long getLong(String column) {
		try {
			return Long.parseLong(getString(column));
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	public Double getDouble(String column) {
		try {
			return Double.parseDouble(getString(column));
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	public Boolean getBoolean(String column) {
		return getInteger(column) == 1;
	}
	
	public Map<String, Object> toMap() {
		return values;
	}

	
	@Override
	public String toString() {
		StringBuilder row = new StringBuilder("Row = [");
		
		for(Entry<String, Object> value : values.entrySet()) {
			row.append(value.getKey()).append(": ").append(value.getValue()).append(", ");
		}
				
		return row.substring(0, row.length() - 2) + "]";
	}
	
}

