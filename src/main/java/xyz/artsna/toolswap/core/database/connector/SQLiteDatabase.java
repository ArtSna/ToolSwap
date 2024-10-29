package xyz.artsna.toolswap.core.database.connector;

import org.jetbrains.annotations.NotNull;
import xyz.artsna.toolswap.core.database.Database;
import xyz.artsna.toolswap.core.exceptions.DatabaseException;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteDatabase extends Database {
	
	private final String CONNECTION_URL;
		
	public SQLiteDatabase(@NotNull String fileName) {
		if (fileName.contains("/")) {
			File directory = new File(fileName.substring(0, fileName.lastIndexOf('/')));
            directory.mkdirs();
		}
		var file = new File(fileName);

		try {
			if(!file.exists()){
				file.createNewFile();
			}
		} catch (IOException e) {
			throw new DatabaseException(e);
		}

		this.CONNECTION_URL = "jdbc:sqlite:" + file.getAbsolutePath();
	}

	protected Connection reconnect() throws SQLException {
		return DriverManager.getConnection(CONNECTION_URL);
	}
}