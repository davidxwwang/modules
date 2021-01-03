package com.david.module.proto.helloworld;

import com.google.gson.Gson;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import org.springframework.http.converter.json.GsonFactoryBean;

import java.io.IOException;
import java.util.HashMap;

public class HelloWorldServer {

    //private Logg

    private Server gRpcServer;

    private void start() throws IOException {
        /* The port on which the server should run */
        int port = 50051;
        gRpcServer = ServerBuilder.forPort(port)
                .addService(new HelloWorldServerImpl())
                .build()
                .start();
      //  logger.info("Server started, listening on " + port);
//        Runtime.getRuntime().addShutdownHook(new Thread() {
//            @Override
//            public void run() {
//                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
//                System.err.println("*** shutting down gRPC server since JVM is shutting down");
//                try {
//                    HelloWorldServer.this.stop();
//                } catch (InterruptedException e) {
//                    e.printStackTrace(System.err);
//                }
//                System.err.println("*** server shut down");
//            }
//        });
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (gRpcServer != null) {
            gRpcServer.awaitTermination();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        HelloWorldServer helloWorldServer = new HelloWorldServer();
        helloWorldServer.start();
        helloWorldServer.blockUntilShutdown();
    }

    public static class HelloWorldServerImpl extends HelloServiceGrpc.HelloServiceImplBase{
        @Override
        public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {

            HashMap<String, String> result = new HashMap<>();
            result.put("name", "david");
            result.put("ret", "very good");
            result.put("greet", "我回复你了");
            String r  = new Gson().toJson(result, HashMap.class);

            HelloReply reply = HelloReply.newBuilder().setMessage(r).build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }
    }

}
