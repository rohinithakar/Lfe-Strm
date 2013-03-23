package poke.db;

public class StorageFactory {
	public static IStorage getStorage() {
		return new DBStorage();
	}
}
