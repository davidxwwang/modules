package com.david.module.util.commonUseDTO;

import lombok.Data;

@Data
public class CommonRespDTO<T> extends CommonBaseRespDTO {

    public final static int HTTP_SUCCESS = 200;

    private static final long serialVersionUID = -1L;

    private boolean success = true;

    private String message;

    private Integer msgCode;

    private T data;

    public CommonRespDTO() {
    }

    public CommonRespDTO(boolean success, Integer msgCode, T data) {
        this.success = success;
        this.msgCode = msgCode;
        this.data = data;
    }

    public static <T> CommonRespDTO<T> withSuccess(T data) {
        CommonRespDTO<T> resp = new CommonRespDTO<>();
        resp.setData(data);
        resp.setSuccess(true);
        return resp;
    }

    public static <T> CommonRespDTO<T> withError(String msg, Integer msgcode) {
        CommonRespDTO<T> resp = new CommonRespDTO<>();
        resp.setData(null);
        resp.setSuccess(false);
        resp.setMsgCode(msgcode);
        return resp;

    }

}
