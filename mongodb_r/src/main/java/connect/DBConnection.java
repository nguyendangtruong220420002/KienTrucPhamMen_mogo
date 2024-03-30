package connect;

import com.mongodb.ConnectionString;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoDatabase;

public class DBConnection {
	private static final String CONNECTION_STRING = "mongodb://localhost:27017/";
	private static final String DB_NAME = "restaurants"; // "ordersDB";

	private MongoClient client;
	private MongoDatabase db;

	private static final DBConnection instance = new DBConnection();
	
	public DBConnection() {
		try {
		ConnectionString connectionString = new ConnectionString(CONNECTION_STRING);
		this.client = MongoClients.create(connectionString);
		this.db = this.client.getDatabase(DB_NAME);
		System.out.println("Connected mongodb: " + CONNECTION_STRING);
		System.out.println("Connected to database: " + DB_NAME);
    } catch (Exception e) {
        System.err.println("Error connecting to database: " + e.getMessage());
        e.printStackTrace();
    }
	}

	public DBConnection getInstance() {
	
		return instance;
	}

	public MongoClient getClient() {
		return this.client;
	}

	public MongoDatabase getDatabase() {
		return db;
	}
	
	public boolean isConnected() {
	    return this.client != null && this.db != null;
	}
	
}
