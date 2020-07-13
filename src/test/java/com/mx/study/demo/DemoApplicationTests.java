package com.mx.study.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
class DemoApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void main() {
        String info = "C:\\mx\\jj\\test.php";
        String[] array = info.split("[\\\\/]");
        System.out.println(Arrays.toString(array));
        String[] array2 = "method(:0)".split("[():]");
        System.out.println(Arrays.toString(array2));
    }

}
