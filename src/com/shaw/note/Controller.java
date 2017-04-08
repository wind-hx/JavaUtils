package com.shaw.note;

import java.lang.annotation.*;

/**
 * Created by Administrator on 2017/2/22 0022.
 *
 * 控制器注解
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Controller {

    String value() default "";

}
