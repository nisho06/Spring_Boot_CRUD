package com.example.springBootCRUD;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class SpringBootCRUDApplicationTests {

    Calculator calculator = new Calculator();

    class Calculator{
        public int add(int a, int b){
            return a+b;
        }


    }

    @Test
    void itShouldAddTwoNumbers() {
        // given
        int number1 = 10;
        int number2 = 30;

        //when
        int result = calculator.add(number1, number2);

        //then
        Assertions.assertEquals(40, result);
    }

}
