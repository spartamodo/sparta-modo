package com.example.sparta_modo.global.annotation;

import com.example.sparta_modo.global.valid.ValidEnumValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidEnumValidator.class)
public @interface ValidEnum {
	String message() default "값이 유효하지 않습니다.";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};

	// 검증할 Enum 클래스를 지정하는 속성
	Class<? extends Enum<?>> enumClass();
}
