package com.david.module.domain.anotation;

import com.david.module.proto.helloworld.HelloReply;
import com.david.module.proto.helloworld.HelloRequest;
import com.david.module.proto.helloworld.HelloServiceGrpc;
import com.google.gson.Gson;
import io.grpc.Grpc;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;


public class HelloWorldService extends HelloServiceGrpc.HelloServiceImplBase {

    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {

        HashMap<String, String> result = new HashMap<>();
        result.put("name", "david");
        result.put("ret", "very good");
        result.put("greet", "我回复你了");
        String r  = new Gson().toJson(result, HashMap.class);

//            try {
//                Thread.sleep(15000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

        HelloReply reply = HelloReply.newBuilder().setMessage(r).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
