package hello.aop.member;

import org.springframework.context.annotation.ComponentScan;

import hello.aop.member.annotation.ClassAop;
import hello.aop.member.annotation.MethodAop;

@ClassAop
@ComponentScan
public class MemberServiceImpl implements MemberService {

	public String internal(String param) {
		return "ok";
	}

	@Override
	@MethodAop("test value")
	public String hello(String param) {
		return "ok";
	}
}
