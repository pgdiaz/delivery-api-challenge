package com.ma.pedidos.command;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;
import java.util.stream.Stream;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class OrderProductCreateCommandTests {
    
    private static Validator validator;

    @BeforeAll
    public static void setupValidator() {

        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForValidateCommandTest")
    public void validateCommandTest(OrderProductCreateCommand input, String expected, String msg) {

        Set<ConstraintViolation<OrderProductCreateCommand>> violations = validator.validate(input);

        assertEquals(1, violations.size(), msg);

        ConstraintViolation<OrderProductCreateCommand> violation = violations.iterator().next();

        assertEquals(expected, violation.getMessage(), msg);
    }

    private static Stream<Arguments> provideArgumentsForValidateCommandTest() {

        return Stream.of(
            Arguments.of(buildToProductTest(null), "The product is required", "Test null product"),
            Arguments.of(buildToProductTest(""), "The product is required", "Test empty product"),
            Arguments.of(buildToProductTest(" "), "The product is required", "Test blank product"),
            Arguments.of(buildToQuantityTest(null),
                "The quantity is required",
                "Test null quantity"),
            Arguments.of(buildToQuantityTest(0),
                "The quantity must be greater than 0",
                "Test min quantity"));
    }

    private static OrderProductCreateCommand buildToProductTest(String product) {

        OrderProductCreateCommand command = new OrderProductCreateCommand();
        command.setProduct(product);
        command.setQuantity(1);

        return command;
    }

    private static OrderProductCreateCommand buildToQuantityTest(Integer quantity) {

        OrderProductCreateCommand command = new OrderProductCreateCommand();
        command.setProduct("validProduct");
        command.setQuantity(quantity);

        return command;
    }
}
