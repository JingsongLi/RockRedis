package com.jslee.rockredis.grpc;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.0.0)",
    comments = "Source: server.proto")
public class RockServerGrpc {

  private RockServerGrpc() {}

  public static final String SERVICE_NAME = "server.RockServer";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.jslee.rockredis.grpc.SetRequest,
      com.jslee.rockredis.grpc.SetReply> METHOD_SET =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "server.RockServer", "Set"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.jslee.rockredis.grpc.SetRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.jslee.rockredis.grpc.SetReply.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.jslee.rockredis.grpc.GetRequest,
      com.jslee.rockredis.grpc.GetReply> METHOD_GET =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "server.RockServer", "Get"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.jslee.rockredis.grpc.GetRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.jslee.rockredis.grpc.GetReply.getDefaultInstance()));

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static RockServerStub newStub(io.grpc.Channel channel) {
    return new RockServerStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static RockServerBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new RockServerBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary and streaming output calls on the service
   */
  public static RockServerFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new RockServerFutureStub(channel);
  }

  /**
   */
  public static abstract class RockServerImplBase implements io.grpc.BindableService {

    /**
     */
    public void set(com.jslee.rockredis.grpc.SetRequest request,
        io.grpc.stub.StreamObserver<com.jslee.rockredis.grpc.SetReply> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SET, responseObserver);
    }

    /**
     */
    public void get(com.jslee.rockredis.grpc.GetRequest request,
        io.grpc.stub.StreamObserver<com.jslee.rockredis.grpc.GetReply> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET, responseObserver);
    }

    @java.lang.Override public io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_SET,
            asyncUnaryCall(
              new MethodHandlers<
                com.jslee.rockredis.grpc.SetRequest,
                com.jslee.rockredis.grpc.SetReply>(
                  this, METHODID_SET)))
          .addMethod(
            METHOD_GET,
            asyncUnaryCall(
              new MethodHandlers<
                com.jslee.rockredis.grpc.GetRequest,
                com.jslee.rockredis.grpc.GetReply>(
                  this, METHODID_GET)))
          .build();
    }
  }

  /**
   */
  public static final class RockServerStub extends io.grpc.stub.AbstractStub<RockServerStub> {
    private RockServerStub(io.grpc.Channel channel) {
      super(channel);
    }

    private RockServerStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected RockServerStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new RockServerStub(channel, callOptions);
    }

    /**
     */
    public void set(com.jslee.rockredis.grpc.SetRequest request,
        io.grpc.stub.StreamObserver<com.jslee.rockredis.grpc.SetReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SET, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void get(com.jslee.rockredis.grpc.GetRequest request,
        io.grpc.stub.StreamObserver<com.jslee.rockredis.grpc.GetReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class RockServerBlockingStub extends io.grpc.stub.AbstractStub<RockServerBlockingStub> {
    private RockServerBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private RockServerBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected RockServerBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new RockServerBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.jslee.rockredis.grpc.SetReply set(com.jslee.rockredis.grpc.SetRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SET, getCallOptions(), request);
    }

    /**
     */
    public com.jslee.rockredis.grpc.GetReply get(com.jslee.rockredis.grpc.GetRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class RockServerFutureStub extends io.grpc.stub.AbstractStub<RockServerFutureStub> {
    private RockServerFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private RockServerFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected RockServerFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new RockServerFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.jslee.rockredis.grpc.SetReply> set(
        com.jslee.rockredis.grpc.SetRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SET, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.jslee.rockredis.grpc.GetReply> get(
        com.jslee.rockredis.grpc.GetRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET, getCallOptions()), request);
    }
  }

  private static final int METHODID_SET = 0;
  private static final int METHODID_GET = 1;

  private static class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final RockServerImplBase serviceImpl;
    private final int methodId;

    public MethodHandlers(RockServerImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SET:
          serviceImpl.set((com.jslee.rockredis.grpc.SetRequest) request,
              (io.grpc.stub.StreamObserver<com.jslee.rockredis.grpc.SetReply>) responseObserver);
          break;
        case METHODID_GET:
          serviceImpl.get((com.jslee.rockredis.grpc.GetRequest) request,
              (io.grpc.stub.StreamObserver<com.jslee.rockredis.grpc.GetReply>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    return new io.grpc.ServiceDescriptor(SERVICE_NAME,
        METHOD_SET,
        METHOD_GET);
  }

}
