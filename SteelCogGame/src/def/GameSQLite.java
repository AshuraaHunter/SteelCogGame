package def;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DatabaseMetaData;

public class GameSQLite {
	protected int score;

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	public GameSQLite() {
		super();
		this.score = 0;
	}

	public GameSQLite(int score) {
		super();
		this.score = score;
	}

	public void Interact(int score) {
		Connection conn = null;
		Statement stmt = null;
		
		try {
			Class.forName("org.sqlite.JDBC");
			// System.out.println("Database driver loaded.\n");
			
			String dbURL = "jdbc:sqlite:scoreboard.db";
			conn = DriverManager.getConnection(dbURL);
			
			if (conn != null) {
				// System.out.println("Connected to database.\n");
				conn.setAutoCommit(false);
				
				DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
				/*
				System.out.println("Driver name:  "+dm.getDriverName());
				System.out.println("Driver ver. : "+dm.getDriverVersion());
				System.out.println("Product name: "+dm.getDatabaseProductName());
				System.out.println("Product ver.: "+dm.getDatabaseProductVersion()+"\n");
				*/
				
				stmt = conn.createStatement();
				
				String sql = "CREATE TABLE IF NOT EXISTS SCOREBOARD " +
						"(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
						"SCORE INTEGER NOT NULL)";
				stmt.executeUpdate(sql);
				conn.commit();
				// System.out.println("Database table successfully created.\n");
				
				sql = "INSERT INTO SCOREBOARD (SCORE) VALUES " + 
						"("+score+")";
				stmt.executeUpdate(sql);
				conn.commit();
				
				conn.close();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
