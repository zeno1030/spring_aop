package hello.aop.internalcall;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 참고: 생성자 주입은 순환 사이클을 만들기 때문에 실패한다.
 */
@Slf4j
@Component
public class CallServiceV2 {

	// private final ApplicationContext applicationContext;
	private final ObjectProvider<CallServiceV2> callServiceV2Provider;

	public CallServiceV2(ObjectProvider<CallServiceV2> callServiceV2Provider) {
		this.callServiceV2Provider = callServiceV2Provider;
	}

	public void external() {
		log.info("call external");
		CallServiceV2 callServiceV2 = callServiceV2Provider.getObject();
		callServiceV2.internal(); //외부 메서드 호출
	}

	public void internal() {
		log.info("call internal");
	}
}