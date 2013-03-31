package poke.db;

import poke.server.conf.ServerConf;

public class StorageFactory {
	public static IUserStorage getUserStorage(ServerConf.GeneralConf server) {
		return new UserStorageImpl(server);
	}
	
	public static IImageStorage getImageStorage(ServerConf.GeneralConf server) {
		return new ImageStorageImpl(server);
	}
}
