package com.ma.pedidos.command;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class OrderCreateCommandTests {

    private static Validator validator;

    @BeforeAll
    public static void setupValidator() {

        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForValidateCommandTest")
    public void validateCommandTest(OrderCreateCommand input, String expected, String msg) {

        Set<ConstraintViolation<OrderCreateCommand>> violations = validator.validate(input);

        assertEquals(1, violations.size(), msg);

        ConstraintViolation<OrderCreateCommand> violation = violations.iterator().next();

        assertEquals(expected, violation.getMessage(), msg);
    }

    private static Stream<Arguments> provideArgumentsForValidateCommandTest() {

        return Stream.of(
            Arguments.of(buildToAddressTest(null), "The address is required", "Test null address"),
            Arguments.of(buildToAddressTest(""), "The address is required", "Test empty addres"),
            Arguments.of(buildToAddressTest(" "), "The address is required", "Test blank address"),
            Arguments.of(buildToEmailTest(), "The Email should be valid", "Test invalid email"),
            Arguments.of(buildToTimeTest(), "The time is required", "Test null time"),
            Arguments.of(buildToDetailTest(null), "The detail is required", "Test null detail"),
            Arguments.of(buildToDetailTest(new ArrayList<>()),
                "The detail must include at least one product",
                "Test empty detail"));
    }

    private static OrderCreateCommand buildToAddressTest(String address) {

        OrderCreateCommand command = new OrderCreateCommand();
        command.setTime(LocalTime.now());
        command.setAddress(address);
        command.setDetail(buildValidDefaultDetail());

        return command;
    }

    private static OrderCreateCommand buildToEmailTest() {

        OrderCreateCommand command = new OrderCreateCommand();
        command.setAddress("validAdrress");
        command.setEmail("invalidEmail.com");
        command.setTime(LocalTime.now());
        command.setDetail(buildValidDefaultDetail());

        return command;
    }

    private static OrderCreateCommand buildToTimeTest() {

        OrderCreateCommand command = new OrderCreateCommand();
        command.setAddress("validAdrress");
        command.setDetail(buildValidDefaultDetail());

        return command;
    }

    private static OrderCreateCommand buildToDetailTest(List<OrderProductCreateCommand> detail) {

        OrderCreateCommand command = new OrderCreateCommand();
        command.setAddress("validAdrress");
        command.setEmail("validEmail@dominio.com");
        command.setTime(LocalTime.now());
        command.setDetail(detail);

        return command;
    }

    private static List<OrderProductCreateCommand> buildValidDefaultDetail() {

        List<OrderProductCreateCommand> detail = new ArrayList<>();
        OrderProductCreateCommand command = new OrderProductCreateCommand();
        command.setProduct("product");
        command.setQuantity(1);
        detail.add(command);
        
        return detail;
    }
}
