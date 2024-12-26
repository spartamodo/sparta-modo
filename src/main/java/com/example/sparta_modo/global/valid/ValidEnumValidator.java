package com.example.sparta_modo.global.valid;

import com.example.sparta_modo.global.annotation.ValidEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class ValidEnumValidator implements ConstraintValidator<ValidEnum, String> {
	private Set<String> enumValues;

	@Override
	public void initialize(ValidEnum annotation) {
		// Enum 클래스에서 모든 값의 이름을 가져와 Set으로 저장
		enumValues = Arrays.stream(annotation.enumClass().getEnumConstants())
			.map(Enum::name)
			.collect(Collectors.toSet());
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		// 필드가 null이거나 비어 있을 경우 유효성을 true로 반환
		if (value == null || value.isEmpty()) {
			return true;
		}
		// 필드의 값이 Enum 값들에 포함되어 있는지 확인
		return enumValues.contains(value);
	}
}
