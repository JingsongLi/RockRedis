package com.jslee.rockredis.server;

import com.jslee.rockredis.grpc.*;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Server that manages startup/shutdown of a {@code Greeter} server.
 */
public class Server {
    private static final Logger LOG = LoggerFactory.getLogger(Server.class.getName());

    /* The port on which the server should run */
    private int port = 50051;
    private io.grpc.Server server;

    /**
     * com.jslee.rockredis.test.Main launches the server from the command line.
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        final Server server = new Server();
        server.start();
        server.blockUntilShutdown();
    }

    private void start() throws IOException {
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

    private Map<String,String> db = new ConcurrentHashMap<>();

    private class RockServerImpl extends RockServerGrpc.RockServerImplBase {
        @Override
        public void set(SetRequest req, StreamObserver<SetReply> responseObserver) {
            SetReply reply = SetReply.newBuilder().build();
            db.put(req.getKey(),req.getValue());
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }

        @Override
        public void get(GetRequest req, StreamObserver<GetReply> responseObserver) {
            String value = db.get(req.getKey());
            GetReply.Builder builder = GetReply.newBuilder();
            if (value != null) {
                builder.setValue(value);
            }
            responseObserver.onNext(builder.build());
            responseObserver.onCompleted();
        }
    }
}
