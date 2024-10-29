package xyz.artsna.toolswap.core.database.connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import xyz.artsna.toolswap.core.database.Database;
import xyz.artsna.toolswap.core.exceptions.DatabaseException;

public class MySqlDatabase extends Database {
	
	private final String CONNECTION_URL;
	private final String CONNECTION_USER;
	private final String CONNECTION_PASSWORD;
			
	public MySqlDatabase(String name, String host, int port, String username, String password, String options) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new DatabaseException(e);
		}
		
		this.CONNECTION_URL = "jdbc:mysql://" + host + ":" + port + "/" + name + (options != null ? options : "");
		this.CONNECTION_USER = username;
		this.CONNECTION_PASSWORD = password;
	}
	
	protected Connection reconnect() throws SQLException {
		return DriverManager.getConnection(CONNECTION_URL, CONNECTION_USER, CONNECTION_PASSWORD);
	}

}
