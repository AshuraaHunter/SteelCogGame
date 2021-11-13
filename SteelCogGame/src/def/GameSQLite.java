package def;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class GameSQLite {
	protected String name;
	protected int score;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	public GameSQLite() {
		super();
		this.name = "undefined";
		this.score = 0;
	}

	public GameSQLite(String name,int score) {
		super();
		this.name = name;
		this.score = score;
	}

	public void Interact(String name,int score) {
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
				
				/*
				System.out.println("Driver name:  "+dm.getDriverName());
				System.out.println("Driver ver. : "+dm.getDriverVersion());
				System.out.println("Product name: "+dm.getDatabaseProductName());
				System.out.println("Product ver.: "+dm.getDatabaseProductVersion()+"\n");
				*/
				
				stmt = conn.createStatement();
				
				String sql = "CREATE TABLE IF NOT EXISTS SCOREBOARD " +
						"(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
						"NAME TEXT NOT NULL, " +
						"SCORE INTEGER NOT NULL)";
				stmt.executeUpdate(sql);
				conn.commit();
				// System.out.println("Database table successfully created.\n");
				
				sql = "INSERT INTO SCOREBOARD (NAME, SCORE) VALUES " + 
						"('"+name+"', " + score+")";
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
