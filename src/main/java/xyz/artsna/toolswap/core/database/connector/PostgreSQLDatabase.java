package xyz.artsna.toolswap.core.database.connector;

import xyz.artsna.toolswap.core.database.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgreSQLDatabase extends Database {
	
	private final String CONNECTION_URL;
	private final String CONNECTION_USER;
	private final String CONNECTION_PASSWORD;
		
	public PostgreSQLDatabase(String name, String host, int port, String username, String password, String options) {
		this.CONNECTION_URL = "jdbc:postgresql://" + host + ":" + port + "/" + name + (options != null ? options : "");
		this.CONNECTION_USER = username;
		this.CONNECTION_PASSWORD = password;
	}
	
	protected Connection reconnect() throws SQLException {
		return DriverManager.getConnection(CONNECTION_URL, CONNECTION_USER, CONNECTION_PASSWORD);
	}

}
