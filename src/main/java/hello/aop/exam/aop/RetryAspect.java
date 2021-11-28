package hello.aop.exam.aop;

import hello.aop.exam.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class RetryAspect {

    //재시도 할 때 언제 joinPoint에 proceed 할지 정해야해서 Around?
    //Retry 매개변수 있으면 그냥 위에도 패키지 다 적을 필요 없이 retry만 적어주면 된다
//    @Around("@annotation(hello.aop.exam.annotation.Retry)")
    @Around("@annotation(retry)")
    public Object doRetry(ProceedingJoinPoint joinPoint, Retry retry)throws Throwable{
        log.info("[retry] {} retry={}", joinPoint.getSignature(), retry);

        int maxRetry = retry.value();
        Exception exceptionHolder = null;
        for(int retryCount = 1; retryCount <= maxRetry; retryCount++){
            try{
                log.info("[retry] retry count={}/{}", retryCount, maxRetry);
                //proceed에서 예외 터지면 아래로 감. 끝까지 return 안되면 결국 끝에 exceptionHolder로!
                return joinPoint.proceed();
            }catch (Exception e){
                exceptionHolder = e;
            }
        }
        throw exceptionHolder;
    }
}
