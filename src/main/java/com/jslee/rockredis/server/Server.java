package com.jslee.rockredis.server;

import com.jslee.rockredis.grpc.*;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Server {
    private static final Logger LOG = LoggerFactory.getLogger(Server.class.getName());

    private int port;
    private io.grpc.Server server;
    private RocksDB db;

    /**
     * com.jslee.rockredis.test.Main launches the server from the command line.
     */
    public static void main(String[] args) throws Exception {
        final Server server = new Server();
        server.start();
        server.blockUntilShutdown();
    }

    private void start() throws IOException, RocksDBException {
        Config.load();
        startDb();
        startGrpc();
    }

    private void startDb() throws Config.ConfigMissingException, RocksDBException {
        String file = Config.get(Config.DB_PATH) + "/" + Config.get(Config.DB_NAME);
        Options options = new Options();
        options.setCreateIfMissing(true);
        // options.setCompactionStyle(CompactionStyle.UNIVERSAL);
        db = RocksDB.open(options, file);
    }

    private void startGrpc() throws IOException {
        port = Config.getInt(Config.SERVER_PORT, 50051);
        server = ServerBuilder.forPort(port)
                .addService(new RockServerImpl())
                .build()
                .start();
        LOG.info("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Use stderr here since the LOG may have been reset by its JVM shutdown hook.
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                Server.this.stop();
                System.err.println("*** server shut down");
            }
        });
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    private class RockServerImpl extends RockServerGrpc.RockServerImplBase {
        @Override
        public void set(SetRequest req, StreamObserver<SetReply> responseObserver) {
            SetReply reply = SetReply.newBuilder().build();
            try {
                db.put(req.getKey().getBytes(),req.getValue().getBytes());
            } catch (RocksDBException e) {
                throw new ServerException(e);
            }
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }

        @Override
        public void get(GetRequest req, StreamObserver<GetReply> responseObserver) {
            String value = null;
            try {
                byte[] bytes = db.get(req.getKey().getBytes());
                if (bytes != null) {
                    value = new String(bytes);
                }
            } catch (RocksDBException e) {
                throw new ServerException(e);
            }

            GetReply.Builder builder = GetReply.newBuilder();
            if (value != null) {
                builder.setValue(value);
            }
            responseObserver.onNext(builder.build());
            responseObserver.onCompleted();
        }
    }
}
