package xyz.artsna.toolswap.bukkit.data.config.components;

import xyz.artsna.toolswap.bukkit.data.config.Config;
import xyz.artsna.toolswap.core.database.Database;
import xyz.artsna.toolswap.core.database.DatabaseType;
import xyz.artsna.toolswap.core.database.connector.MySqlDatabase;
import xyz.artsna.toolswap.core.database.connector.PostgreSQLDatabase;
import xyz.artsna.toolswap.core.database.connector.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class DatabaseConfig {

    private final Config config;

    public DatabaseConfig(Config config) {
        this.config = config;
    }

    public void configure() {
        for(DatabaseData data : getDatabaseDataList()) {

            Database database = switch (data.getType()) {
                case SQLITE -> new SQLiteDatabase(data.getFileName());
                case MYSQL ->  new MySqlDatabase(data.getName(), data.getHost(), data.getPort(), data.getUsername(), data.getPassword(), data.getOptions());
                case POSTGRESQL -> new PostgreSQLDatabase(data.getName(), data.getHost(), data.getPort(), data.getUsername(), data.getPassword(), data.getOptions());
            };

            database.debugQuery(config.isDebug());

            config.getPlugin().getDatabaseManager().register(data.getIdentifier(), database);
        }
    }

    public List<DatabaseData> getDatabaseDataList() {
        if(!config.contains("databases"))  return Collections.emptyList();

        Set<String> identifiers = config.getSectionList("databases");
        List<DatabaseData> data = new ArrayList<>();

        for(String identifier : identifiers)
            data.add(getDatabaseData(identifier));

        return data;
    }

    public DatabaseData getDatabaseData(String identifier) {
        if(!config.contains("databases." + identifier))  return null;

        DatabaseType type = DatabaseType.valueOf(config.getString("databases." + identifier  + ".type"));

        if(type.equals(DatabaseType.SQLITE)){
            var fileName = config.getString("databases." + identifier + ".file-name");
            assert fileName != null;
            fileName = fileName
                    .replaceAll("%plugin_dir%", config.getPlugin().getDataFolder().getPath().replace('\\', '/'))
                    .replaceAll("%server_plugins_dir%", config.getPlugin().getDataFolder().getParentFile().getPath().replace('\\', '/'));
            return new DatabaseData(identifier, fileName);
        } else {
            return new DatabaseData(identifier, type,
                    config.getString("databases." + identifier + ".name"),
                    config.getString("databases." + identifier + ".host"),
                    config.getInt("databases." + identifier + ".port"),
                    config.getString("databases." + identifier + ".username"),
                    config.getString("databases." + identifier + ".password"),
                    config.getString("databases." + identifier + ".options")
            );
        }
    }

    public static class DatabaseData {

        private final String identifier;
        private final DatabaseType type;

        private final String name;
        private final String host;
        private final Integer port;
        private final String username;
        private final String password;
        private final String options;

        private final String fileName;

        public DatabaseData(String identifier, DatabaseType type, String name, String host, int port, String username, String password, String options) {
            this.identifier = identifier;
            this.type = type;
            this.name = name;
            this.host = host;
            this.port = port;
            this.username = username;
            this.password = password;
            this.options = options;
            this.fileName = null;
        }

        public DatabaseData(String identifier, String fileName) {
            this.identifier = identifier;
            this.fileName = fileName;
            this.type = DatabaseType.SQLITE;
            this.name = null;
            this.host = null;
            this.port = 0;
            this.username = null;
            this.password = null;
            this.options = null;
        }

        public String getIdentifier() {
            return identifier;
        }

        public String getFileName() {
            return fileName;
        }

        public String getName() {
            return name;
        }

        public DatabaseType getType() {
            return type;
        }

        public String getHost() {
            return host;
        }

        public int getPort() {
            return port;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public String getOptions() {
            return options;
        }
    }
}
