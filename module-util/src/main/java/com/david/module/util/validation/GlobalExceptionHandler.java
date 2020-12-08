//package com.david.module.util.validation;
//
//import org.apache.tomcat.util.buf.StringUtils;
//import org.springframework.stereotype.Component;
//import org.springframework.validation.BindException;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.ObjectError;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.validation.ConstraintViolation;
//import javax.validation.ConstraintViolationException;
//import javax.validation.ValidationException;
//import javax.xml.ws.http.HTTPException;
//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
//import java.util.List;
//import java.util.Set;
//
//// 如果全部异常处理返回json，那么可以使用 @RestControllerAdvice 代替 @ControllerAdvice
////@RestController
//@ControllerAdvice
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler(ConstraintViolationException.class)
//    public String httpException(HttpServletRequest request, ConstraintViolationException ex) {
//        Set<ConstraintViolation<?>> errors = ex.getConstraintViolations();
//
//        return "david--ConstraintViolationException";
//    }
//
////    @ExceptionHandler(ConstraintViolationException.class)
////    public String httpExceptionww( ConstraintViolationException ex){
////        Set<ConstraintViolation<?>> errors = ex.getConstraintViolations();
////
////        return "david--error";
////    }
//
//    @ExceptionHandler(Exception.class)
//    public String httpExceptionw(HttpServletRequest request, BindException ex) {
//        List<ObjectError> errors = ex.getAllErrors();
//
//        return "david--Exception";
//    }
//
//    @ExceptionHandler(BindException.class)
//    @ResponseBody
//    public String handleMethodArgumentNotValidException(BindException ex) {
//        // logger.error("BindException：{}", ex.getMessage());
//        String message = ex.getFieldErrors().get(0).getDefaultMessage();
//        return this.encode(message);
//    }
//
//    // 对返回数据进行截取和编码处理，防止中文乱码和返回数据量过大
//    private String encode(String errorMsg) {
//        String rtnMessage = errorMsg;
//        if (errorMsg.isEmpty()) {
//            rtnMessage = "{\"message\":\"error\",\"status\":\"error\"}";
//            return rtnMessage;
//        } else {
//            if (errorMsg.length() > 100) {
//                rtnMessage = errorMsg.substring(0, 100);
//            }
//            // 特殊字符编码，解决中文乱码问题
//            try {
//                rtnMessage = URLEncoder.encode(rtnMessage, "UTF-8");
//            } catch (UnsupportedEncodingException e) {
//                //logger.error("URLEncoder报错：", e);
//            }
//            rtnMessage = "{\"message\":\"" + rtnMessage + "\",\"status\":\"error\"}";
//            return rtnMessage;
//        }
//    }
//    // https://blog.csdn.net/wangpeng322/article/details/81237886
//}
