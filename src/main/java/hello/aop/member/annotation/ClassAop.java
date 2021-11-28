package hello.aop.member.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//클래스에 붙일 수 있는 애노테이션
@Target(ElementType.TYPE)
//런타임 실행할 때까지 애노테이션 살아있는 것
@Retention(RetentionPolicy.RUNTIME)
public @interface ClassAop {
}
