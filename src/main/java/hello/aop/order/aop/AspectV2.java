package hello.aop.order.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
public class AspectV2 {

	@Pointcut("execution(* hello.aop.order..*(..))")
	private void allOrder() {} //pointcut signature
	@Around("allOrder()")
	public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable{
		log.info("[log] {}", joinPoint.getSignature()); //join point 시그니쳐
		return joinPoint.proceed();
	}
}
