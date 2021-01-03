package com.david.module.proto.helloworld;

public class ProtoMainTest {

    public static void main(String[] args) {

       // HelloRequestOrBuilder builder = HelloRequestOrBuilder.
        HelloRequest helloRequest = HelloRequest.newBuilder().setName("david").build();

        byte[] protobuf = helloRequest.toByteArray();
//        HelloReply response ;
//        try {
//
//            response =
//        }catch (Exception ex){
//
//        }

        System.out.println("");


    }
}
