package com.gtf.library.model;

import com.gtf.library.entity.Book;
import com.gtf.library.entity.BookRent;
import com.gtf.library.entity.Member;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pojo.tester.api.assertion.Method;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

@ExtendWith(MockitoExtension.class)
class ModelPropertiesTest {

    @Test
    void propertiesClassesDataTest() {
        final Class<?>[] propertiesClasses = {
                BookDto.class,
                MemberDto.class,
                RentDto.class
        };
        for (Class<?> propertiesClass : propertiesClasses)
            assertPojoMethodsFor(propertiesClass).testing(
                    Method.GETTER,
                    Method.SETTER,
                    Method.TO_STRING,
                    Method.EQUALS,
                    Method.CONSTRUCTOR,
                    Method.HASH_CODE
            ).areWellImplemented();
    }

    @Test
    void propertiesClassesTest() {
        final Class<?>[] propertiesClasses = {
                Book.class,
                Member.class,
                BookRent.class
        };
        for (Class<?> propertiesClass : propertiesClasses)
            assertPojoMethodsFor(propertiesClass).testing(
                    Method.GETTER,
                    Method.SETTER,
                    Method.CONSTRUCTOR
            ).areWellImplemented();
    }
}
