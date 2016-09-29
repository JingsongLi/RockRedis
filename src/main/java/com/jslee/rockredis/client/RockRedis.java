package com.jslee.rockredis.client;

import com.jslee.rockredis.grpc.*;
import com.jslee.rockredis.server.Server;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * A simple client that requests a greeting from the {@link Server}.
 */
public class RockRedis {
    private static final Logger LOG = LoggerFactory.getLogger(RockRedis.class.getName());

    private final ManagedChannel channel;
    private final RockServerGrpc.RockServerBlockingStub blockingStub;

    /**
     * Construct client connecting to HelloWorld server at {@code host:port}.
     */
    public RockRedis(String host, int port) {
        channel = ManagedChannelBuilder.forAddress(host, port)
                // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
                // needing certificates.
                .usePlaintext(true)
                .build();
        blockingStub = RockServerGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public void set(final String key, String value) {
        SetRequest request = SetRequest.newBuilder().setKey(key).setValue(value).build();
        SetReply response;
        try {
            response = blockingStub.set(request);
        } catch (StatusRuntimeException e) {
            LOG.warn("RPC failed: {}", e.getStatus());
            throw e;
        }
    }

    public String get(final String key) {
        GetRequest request = GetRequest.newBuilder().setKey(key).build();
        GetReply response;
        try {
            response = blockingStub.get(request);
        } catch (StatusRuntimeException e) {
            LOG.warn("RPC failed: {}", e.getStatus());
            throw e;
        }
        return response.getValue();
    }

    /**
     * Greet server. If provided, the first element of {@code args} is the name to use in the
     * greeting.
     */
    public static void main(String[] args) throws Exception {
        RockRedis rockRedis = new RockRedis("localhost", 50051);
        try {
            String key = "hehe";
            rockRedis.set(key, "haha");
            LOG.info(rockRedis.get(key));
            LOG.info(rockRedis.get("Nothing"));
        } finally {
            rockRedis.shutdown();
        }
    }
}
