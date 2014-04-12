package in.regalauction.infrastructure.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);
	
	@Around("execution(* in.regalauction..*.*(..))")
	public void traceMethodInvocation(ProceedingJoinPoint joinPoint) throws Throwable {
	
		StringBuilder builder = new StringBuilder();
		builder.append(joinPoint.getTarget().getClass().getName())
			.append(".")
			.append(joinPoint.getSignature().getName())
			.append("(");
		
		// Append args
		Object[] args = joinPoint.getArgs();
		for (int i = 0; i < args.length; i++)
			builder.append(args[i]).append(",");
		
		if (args.length > 0)
			builder.deleteCharAt(builder.length() - 1);
		
		builder.append(")");
		
		String methodCall = builder.toString();
		
		joinPoint.getSignature().getName();
		LOGGER.trace("Entering {}", methodCall);
		
		joinPoint.proceed();
		
		LOGGER.trace("Exiting {}", methodCall);
	}
}
