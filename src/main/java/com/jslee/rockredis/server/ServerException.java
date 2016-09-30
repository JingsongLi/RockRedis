package com.jslee.rockredis.server;

import org.rocksdb.RocksDBException;

/**
 * To any Exception in server.
 * Created by jslee on 2016/9/30.
 */
public class ServerException extends RuntimeException {
    public ServerException(RocksDBException e) {
        super(e);
    }
}
