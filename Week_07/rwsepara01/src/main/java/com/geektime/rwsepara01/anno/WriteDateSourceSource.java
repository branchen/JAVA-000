package com.geektime.rwsepara01.anno;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WriteDateSourceSource {
    String name() default "";
}
