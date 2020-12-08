package com.david.module.util.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.lang.annotation.Annotation;


@RestController
@RequestMapping(value = "/valid")

// 告诉MethodValidationPostProcessor此Bean需要开启方法级别验证支持
@Validated  // 打开校验开关 只有加了这个，下面的testString入参检测才起作用
public class ValidationController {

    @Resource
    @Qualifier("item2david")
    Item2 item2;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/test")
    public String doTest(@Validated Item item) {
//        if (bindingResult.hasErrors()){
//            return bindingResult.getAllErrors().toString();
//        }
        return "通过验证";
    }

    @RequestMapping("/testString")
    public Object testString(@NotNull(message = "name不能为null") String message) {

//        ValidClass validClass = new ValidClass();
//        return validClass.testName(null);
        Annotation[] annotations = this.getClass().getAnnotations();
        for (Annotation annotation : annotations) {
            System.out.print(annotation);
            System.out.print("\n");
            if (annotation instanceof RequestMapping) {
                RequestMapping requestMapping = (RequestMapping) annotation;
                logger.info("请求 {}", requestMapping.value());

            }
        }
        return null;

    }

    @RequestMapping("/testPOJO")
    public Object testPOJO() {
        Item item = this.test(new Item());
        return test2(-1L);
//        Item item = new Item();
//        Thread currentThread = Thread.currentThread();
//        logger.info("当前线程信息：" + Thread.currentThread().toString());
//        return this.test(item);
    }

    @RequestMapping("/testBean")
    public Object testBean() {
        return item2.setName("david", -1L);
    }

    private Item test(@Validated Item item) {
        item.setId(4);
        return item;
    }

    public Long test2(@Min(value = 0, message = "最小为0") Long id) {
        return id * 2;
    }

}
