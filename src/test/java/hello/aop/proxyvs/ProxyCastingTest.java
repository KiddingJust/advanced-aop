package hello.aop.proxyvs;

import hello.aop.member.MemberService;
import hello.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

@Slf4j
public class ProxyCastingTest {

    @Test
    void jdkProxy(){
        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        //아래 생략해도 기본으로는 JDK 동적 프록시 (인터페이스 있으므로)
        proxyFactory.setProxyTargetClass(false);

        //프록시를 인터페이스로 캐스팅 --> 이건 성공
        MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy();
        //해당 프록시를 Impl로 캐스팅해보면? --> 실패함. 예외 처리 안나게 변경하면,
//        MemberServiceImpl castingMemberService = (MemberServiceImpl) memberServiceProxy;
        Assertions.assertThrows(ClassCastException.class, () -> {
            MemberServiceImpl castingMemberService = (MemberServiceImpl) memberServiceProxy;
        });
    }

    @Test
    void cglibProxy(){
        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        //아래 생략해도 기본으로는 JDK 동적 프록시 (인터페이스 있으므로)
        //true로 하면 CGLIB 프록시
        proxyFactory.setProxyTargetClass(true);

        //프록시를 인터페이스로 캐스팅 --> 이건 성공
        MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy();
        //해당 프록시를 Impl로 캐스팅해보면? --> 성공
        MemberServiceImpl castingMemberService = (MemberServiceImpl) memberServiceProxy;
    }
}
