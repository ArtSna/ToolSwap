package xyz.artsna.toolswap.core.database;

import xyz.artsna.toolswap.core.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public abstract class Database {
	
	private Connection connection;
	
	private boolean logQuery = false;
	
	public void debugQuery(boolean logQuery) {
		this.logQuery = logQuery;
	}
	
	protected Connection getConnection() {
		try {
			if(connection == null || connection.isClosed())
				connection = reconnect();
			return connection;
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
    }
	protected void closeConnection() {
        try {
			if(connection != null && !connection.isClosed())
				connection.close();
        } catch (SQLException e) {
			throw new DatabaseException(e);
        }
	}
	
	protected abstract Connection reconnect() throws SQLException;

	public void executeQuery(String query, Object... arguments) {
		if(logQuery)
			System.out.println(query);

		try {
			PreparedStatement st = getConnection().prepareStatement(query);

		    for( int i = 0; i < arguments.length;i++){
		    	if(arguments[i] instanceof UUID)
			        st.setObject(i+1,arguments[i].toString());
		    	else
		    		st.setObject(i+1,arguments[i]);
		    }
			
			st.executeUpdate();
			st.close();		
			
		} catch (SQLException e) {
			throw new DatabaseException(e);
		} finally {
			closeConnection();
		}
	}
	public DataResponse executeSelect(String query, Object... arguments) {	
		if(logQuery)
			System.out.println(query);
		
		try {
			List<Row> results = new ArrayList<>();
			PreparedStatement st = getConnection().prepareStatement(query);
		    for( int i = 0; i < arguments.length;i++){
		    	if(arguments[i] instanceof UUID)
			        st.setObject(i+1,arguments[i].toString());
		    	else
		    		st.setObject(i+1,arguments[i]);
		    }
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				 Map<String, Object> values = new HashMap<>();
				for (int y = 1; y <= rs.getMetaData().getColumnCount(); y++) {
					values.put(rs.getMetaData().getColumnLabel(y), rs.getObject(y));
				}
				results.add(new Row(values));
			}
			st.close();
			return new DataResponse(results);
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}
}