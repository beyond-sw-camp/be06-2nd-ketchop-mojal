package com.example.mojal2ndproject2.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

//@Slf4j
@Aspect //@Aspect: 이 클래스가 AOP 관점(Aspect)임을 나타냅니다.
@Component
public class MethodExecutionTimer {
    private static final Logger logger = LoggerFactory.getLogger(MethodExecutionTimer.class);
    //LoggerFactory.getLogger() 메서드를 사용하여 MethodExecutionTimer 클래스에 대한 로거를 생성합니다.
    // 이 로거는 실행 시간을 로그로 남기기 위해 사용

    @Pointcut("@annotation(com.example.mojal2ndproject2.common.annotation.Timer)")
    //특정 포인트컷을 정의합니다. 여기서는 @Timer 애너테이션이 붙은 메서드를 타겟으로
    //포인트컷은 AOP에서 어드바이스(Advice)가 적용될 메서드나 조인포인트를 정의
    //어드바이스는 포인트컷으로 정의된 조인포인트에서 실행되는 코드
    //조인포인트는 애플리케이션 실행 중에 특정 지점을 의미하며, 일반적으로 메서드 실행, 생성자 호출, 필드 접근 등이 포함됩니다.
    private void timerPointcut() { // 단순히 포인트컷을 식별하는 이름으로 사용됩니다.
    }

    @Around("timerPointcut()") //timerPointcut() 포인트컷 주위에서 실행될 어드바이스를 정의
    public Object traceTime(ProceedingJoinPoint joinPoint) throws Throwable {
        //ProceedingJoinPoint: 현재 실행 중인 조인포인트를 나타내며, 이를 통해 대상 메서드 호출을 제어할 수 있습니다.
        StopWatch stopWatch = new StopWatch();

        try {
            stopWatch.start();
            return joinPoint.proceed();
            //스톱워치를 시작하고, 대상 메서드를 실행합니다.
        } finally {
            stopWatch.stop();//stopWatch.stop(): 스톱워치를 멈춥니다.
            logger.info("{} - 시간 - {}ms ", joinPoint.getSignature().toString(), stopWatch.getTotalTimeMillis());
            //스톱워치를 멈추고, 실행 시간을 로그로 남깁니다.
        }
    }
}
