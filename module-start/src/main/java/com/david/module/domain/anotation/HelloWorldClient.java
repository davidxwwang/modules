package com.david.module.domain.anotation;

import com.david.module.proto.helloworld.HelloReply;
import com.david.module.proto.helloworld.HelloRequest;
import com.david.module.proto.helloworld.HelloRequestOrBuilder;
import com.david.module.proto.helloworld.HelloServiceGrpc;
import com.google.common.util.concurrent.ListenableFuture;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class HelloWorldClient {

    // 同步请求
    HelloServiceGrpc.HelloServiceBlockingStub blockingStub;

    // 异步请求
    HelloServiceGrpc.HelloServiceStub asynStub;

    HelloServiceGrpc.HelloServiceFutureStub futureStub;

    ManagedChannel channel ;

    public HelloWorldClient(ManagedChannelBuilder<?> channelBuilder) {
        channel =channelBuilder.build();
        blockingStub = HelloServiceGrpc.newBlockingStub(channel);
        asynStub = HelloServiceGrpc.newStub(channel);

    }

    public HelloWorldClient(String host, int port){
        ManagedChannelBuilder channelBuilder = ManagedChannelBuilder.forAddress(host, port).usePlaintext();
       // this(channelBuilder);

        channel =channelBuilder.build();
        blockingStub = HelloServiceGrpc.newBlockingStub(channel);
    }

    public void blockSay(){
        HelloRequest request = HelloRequest.newBuilder().setCompany(2.1).setName("david").build();
        HelloReply reply;
        try{
            reply = blockingStub.sayHello(request);
        }catch (Exception ex){
        }
    }

    public void asynSay(){
        HelloRequest request = HelloRequest.newBuilder().setCompany(2.1).setName("david").build();
        asynStub.sayHello(request, new StreamObserver<HelloReply>() {
            @Override
            public void onNext(HelloReply value) {

            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onCompleted() {

            }
        });
    }

    public void futureSay(){
        HelloRequest request = HelloRequest.newBuilder().setCompany(2.1).setName("david").build();
        ListenableFuture<HelloReply> listenableFuture = futureStub.sayHello(request);
        try {
            listenableFuture.get(3000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

    }
}
