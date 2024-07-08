package utils;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(java.lang.annotation.ElementType.METHOD)
@Documented
/*
 * Enable dark mode for annotated tests
 */
public @interface DarkMode {

    boolean enabled() default false;
}
