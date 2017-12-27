package com.innowireless.a01_kotlinimageparsinglist

import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.internal.util.StringUtil
import org.powermock.api.mockito.PowerMockito
import org.powermock.api.mockito.PowerMockito.*
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner
import org.powermock.modules.junit4.common.internal.PowerMockJUnitRunnerDelegate

/**
 * Created by Owner on 2017-12-27.
 */

class CollaboratorWithStaticMethods {
    companion object {
        fun firstMethod(name: String): String = "Hello $name!"
        fun secondMethod(): String = "Hello no one!"
        fun thirdMethod(): String = "Hello no one again!!"
    }

    fun helloMethod(): String = "Hello World!"
}
@RunWith(PowerMockRunner::class)
@PrepareForTest(fullyQualifiedNames = ["com.innowireless.a01_kotlinimageparsinglist.*"])
class PowerMockTest {

    @Before
    fun setUp() {

    }

    @Test
    fun decliningAge() {
        // Create a mock object using the PowerMockito
        val mock = PowerMockito.mock(CollaboratorWithStaticMethods::class.java)

        // set an expectation telling that whenever the no-arg constructor of that class is invoked,
        // a mock instance should be returned rather than a real one
        // mock all the static methods in a class called "CollaboratorWithStaticMethods"
        verifyNew(CollaboratorWithStaticMethods::class.java).withNoArguments()
//        whenNew(CollaboratorWithStaticMethods::class.java).withNoArguments().thenReturn(mock)

        // use Mockito to set up your expectation
        val expected = "first"

        val collaborator = CollaboratorWithStaticMethods()
        verifyNew(CollaboratorWithStaticMethods::class.java).withNoArguments()

        PowerMockito.`when`(collaborator.helloMethod()).thenReturn("Hello Hayley!")
        val welcome = collaborator.helloMethod()

        Mockito.verify(collaborator).helloMethod()
    }

    @Test
    fun staticTest() {
        val mock = mockStatic(CollaboratorWithStaticMethods::class.java)
        `when`(CollaboratorWithStaticMethods.firstMethod(Mockito.anyString()))
                .thenReturn("Hello Hayley!!")
        `when`(CollaboratorWithStaticMethods.secondMethod()).thenReturn("Nothing Special")

        doThrow(RuntimeException()).`when`(CollaboratorWithStaticMethods::class.java)
        CollaboratorWithStaticMethods.thirdMethod()

        val first = CollaboratorWithStaticMethods.firstMethod("Whoever")
        val second = CollaboratorWithStaticMethods.firstMethod("Whatever")

        assertEquals("Hello first!!", first)
        assertEquals("Hello Whatever !", second)

        /* How many times a method is invoked */
        verifyStatic(Mockito.times(2))
        CollaboratorWithStaticMethods.firstMethod(Mockito.anyString())

        verifyStatic(Mockito.never())
        CollaboratorWithStaticMethods.secondMethod()
    }

    class CollaboratorForPartialMocking {
        companion object {
            fun staticMethod(): String = "Static Method!"
        }

        final fun finalMethod(): String = "Final Method!"

        private fun privateMethod(): String = "Private Method"

        public fun privateMethodCaller(): String = privateMethod() + " Welcome to the Kotlin World"
    }

    @Test
    fun testPartialMocking() {
        spy(CollaboratorForPartialMocking::class)
        `when`(CollaboratorForPartialMocking.staticMethod()).thenReturn("I am a static mock method")
        val returnValue = CollaboratorForPartialMocking.staticMethod()
        verifyStatic()
        CollaboratorForPartialMocking.staticMethod()

        assertEquals("I am a static mock method", returnValue)

        // final and private method
        val collaborator = CollaboratorForPartialMocking()
        val mock = spy(collaborator)

        `when`(mock.finalMethod()).thenReturn("I am a final method")
        println(mock.finalMethod())

        Mockito.verify(mock).finalMethod()
        assertEquals("I am a final mock method", returnValue)


    }
}