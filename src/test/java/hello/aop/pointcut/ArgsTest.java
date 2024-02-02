package hello.aop.pointcut;

import static org.assertj.core.api.Assertions.*;

import java.lang.reflect.Method;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import hello.aop.member.MemberServiceImpl;

public class ArgsTest {
	AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
	Method helloMethod;

	@BeforeEach
	public void init() throws NoSuchMethodException {
		helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
	}

	private AspectJExpressionPointcut pointcut(String expression) {
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression(expression);
		return pointcut;
	}

	@Test
	void args() {
		// hello(String)과 매칭
		assertThat(pointcut("args(String)")
			.matches(helloMethod, MemberServiceImpl.class)).isTrue();
		assertThat(pointcut("args(Object)")
			.matches(helloMethod, MemberServiceImpl.class)).isTrue();
		assertThat(pointcut("args()")
			.matches(helloMethod, MemberServiceImpl.class)).isFalse();
		assertThat(pointcut("args(..)")
			.matches(helloMethod, MemberServiceImpl.class)).isTrue();
		assertThat(pointcut("args(*)")
			.matches(helloMethod, MemberServiceImpl.class)).isTrue();
		assertThat(pointcut("args(String, ..)")
			.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}

	/**
	 * execution(* *(java.io.Serializable)): 메서드의 시그니처로 판단 (정적)
	 * args(java.io.Serializable): 런타임에 전달된 인수로 판단 (동적)
	 */
	@Test
	void argsVsExecution() {
		// Args : 상위 타입 호환
		assertThat(pointcut("args(String)")
			.matches(helloMethod, MemberServiceImpl.class)).isTrue();
		assertThat(pointcut("args(java.io.Serializable)")
			.matches(helloMethod, MemberServiceImpl.class)).isTrue();
		assertThat(pointcut("args(Object)")
			.matches(helloMethod, MemberServiceImpl.class)).isTrue();

		// Execution : 상위 타입 불가
		assertThat(pointcut("execution(* *(String))")
			.matches(helloMethod, MemberServiceImpl.class)).isTrue();
		assertThat(pointcut("execution(* *(java.io.Serializable))")
			.matches(helloMethod, MemberServiceImpl.class)).isFalse(); // 매칭 불가
		assertThat(pointcut("execution(* *(Object))")
			.matches(helloMethod, MemberServiceImpl.class)).isFalse(); // 매칭 불가
	}
}