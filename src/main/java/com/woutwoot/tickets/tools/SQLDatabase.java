package com.woutwoot.tickets.tools;

import org.bukkit.plugin.Plugin;

import java.sql.*;

/**
 * Abstract Database class, serves as a base for any connection method (MySQL,
 * SQLite, etc.)
 * 
 * @author -_Husky_-
 * @author tips48
 */
public abstract class SQLDatabase {

	protected Connection connection;
	
	/**
	 * Plugin instance, use for plugin.getDataFolder()
	 */
	protected Plugin plugin;

	/**
	 * Creates a new Database
	 * 
	 * @param plugin
	 *            Plugin instance
	 */
	protected SQLDatabase(Plugin plugin) {
		this.plugin = plugin;
		this.connection = null;
	}

	/**
	 * Opens a connection with the database
	 * 
	 * @return Opened connection
	 * @throws java.sql.SQLException
	 *             if the connection can not be opened
	 * @throws ClassNotFoundException
	 *             if the driver cannot be found
	 */
	public abstract Connection openConnection() throws SQLException,
			ClassNotFoundException;

	/**
	 * Checks if a connection is open with the database
	 * 
	 * @return true if the connection is open
	 * @throws java.sql.SQLException
	 *             if the connection cannot be checked
	 */
	public boolean checkConnection() throws SQLException {
		return connection != null && !connection.isClosed();
	}

	/**
	 * Gets the connection with the database
	 * 
	 * @return Connection with the database, null if none
	 */
	public Connection getConnection() {
        try {
            if (!checkConnection()) {
                openConnection();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
	}

	/**
	 * Closes the connection with the database
	 * 
	 * @return true if successful
	 * @throws java.sql.SQLException
	 *             if the connection cannot be closed
	 */
	public boolean closeConnection() throws SQLException {
		if (connection == null) {
			return false;
		}
		connection.close();
		return true;
	}


	/**
	 * Executes a SQL Query<br>
	 * 
	 * If the connection is closed, it will be opened
	 * 
	 * @param query
	 *            Query to be run
	 * @return the results of the query
	 * @throws java.sql.SQLException
	 *             If the query cannot be executed
	 * @throws ClassNotFoundException
	 *             If the driver cannot be found; see {@link #openConnection()}
	 */
	public ResultSet querySQL(String query) throws SQLException,
			ClassNotFoundException {
		if (!checkConnection()) {
			openConnection();
		}

		Statement statement = connection.createStatement();

		ResultSet result = statement.executeQuery(query);

		return result;
	}

	/**
	 * Executes an Update SQL Query<br>
	 * See {@link java.sql.Statement#executeUpdate(String)}<br>
	 * If the connection is closed, it will be opened
	 * 
	 * @param query
	 *            Query to be run
	 * @return Result Code, see {@link java.sql.Statement#executeUpdate(String)}
	 * @throws java.sql.SQLException
	 *             If the query cannot be executed
	 * @throws ClassNotFoundException
	 *             If the driver cannot be found; see {@link #openConnection()}
	 */
	public int updateSQL(String query) throws SQLException,
			ClassNotFoundException {
		if (!checkConnection()) {
			openConnection();
		}

		Statement statement = connection.createStatement();

		int result = statement.executeUpdate(query);

		return result;
	}

    public boolean updatePrepSQL(PreparedStatement ps) throws SQLException,
            ClassNotFoundException {
        if (!checkConnection()) {
            openConnection();
        }

        boolean result = ps.execute();

        return result;
    }
}