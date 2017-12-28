package com.innowireless.a01_kotlinimageparsinglist

import com.innowireless.a01_kotlinimageparsinglist.view.image.presenter.ImageContract
import com.innowireless.a01_kotlinimageparsinglist.view.image.presenter.ImagePresenter
import io.reactivex.Observable
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(MockitoJUnitRunner::class)
class ExampleUnitTest {

    lateinit var mImageView: ImageContract.View
    lateinit var mImagePresenter: ImageContract.Presenter

    var isFinish: Boolean = false

    @Before
    fun setUp() {
        mImageView = mock(ImageContract.View::class.java)
        mImagePresenter = ImagePresenter(mImageView)

    }

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }


    @Test
    fun loadImages() {
        mImagePresenter.checkPassword("1111")

        doAnswer { mock ->
            isFinish = true
            println("LoadImagesWithRxJavaRetorit is finished!!")
            mock
        }.`when`(mImageView).notifyDataSetChanged()


        Observable.timer(5, TimeUnit.SECONDS)
                .test().await()


        println("After 5 seconds")
    }

    @Test
    fun verifyTest() {
        val mockedList = mock(ArrayList::class.java) as ArrayList<String>
        mockedList.add("one")
        mockedList.clear()

        verify(mockedList).add("one")
        verify(mockedList).clear()
    }

    @Test
    fun stubbingTest() {
        // concrete classes, not just interfaces
        val mockedList = mock(LinkedList::class.java)

        // stubbing
        `when`(mockedList[0]).thenReturn("first")
        `when`(mockedList[1]).thenThrow(RuntimeException())

        println(mockedList[0])
//        println(mockedList[1])
        println(mockedList[999])


        verify(mockedList)[0]
    }

    @Test
    fun thenVerb() {
        val list = mock(ArrayList::class.java) as ArrayList<String>

//        `when`(list[0]).thenThrow(NullPointerException())
//        `when`(list.clear()).thenThrow(RuntimeException())

//        doThrow(RuntimeException()).`when`(list)[0]
        doNothing().`when`(list).clear()



        println(list[0])
        println(list.clear())

        // thenAnswer
        // Mockito 에서 제공하지 않는 특성 customizing
        // thenReturn(), thenThrow() 쓰길 추천하고, 간단히 테스트하기에도 충분하지만 다른 동작을 원할 경우 Answer interface 사용
        list[0] = "One!!"
        `when`(list[0]).thenAnswer {
            val args = it.arguments
            val mock = it.mock
            "called width arguments: ${args.toString()}"
        }

        println(list[0])

        /* Stubbing consecutive calls */
        `when`(list[0])
                .thenReturn("One", "Two", "Three")
//                .thenCallRealMethod()
                .thenAnswer { println("list[0] is called tree times") }
                .thenThrow(RuntimeException())

        println(list[0])
        println(list[0])
        println(list[0])
        println(list[0])
        println(list[0])
        println(list[0])
    }


    /**
     * You can use doThrow(), doAnswer(), doNothing(), doReturn() and doCallRealMethod() in place of the corresponding call with when(), for any method.
     * It is necessary when you
     *  * stub void methods
     *  * stub methods on spy objects
     *  * stub the same method more than once, to change the behavior of a mock in the middle of a test
     *
     *  when(instance.returnMethod()).thenVerb()
     *  when(instnace.returnVoidMethod()) -> use doVerb()
     *  doVerb().when(instance).returnVoidMethod()
     * */
    @Test
    fun doVerb() {
        val mockedList = mock(ArrayList::class.java) as ArrayList<String>

        // Stubbing void methods requires a different approach from when(object) becuz the compiler does not like void methods inside brackets
        // when parameter 로 void method 가 들어오는 경우 when parameter 로 객체를 삽입하고, doVerb() 사용
//        doThrow(RuntimeException()).`when`(mockedList).clear()
        /*
        * org.mockito.exceptions.base.MockitoException:
        *   Only void methods can doNothing()!
        *    Example of correct use of doNothing():
        *       doNothing().
        *        doThrow(new RuntimeException())
        *       .when(mock).someVoidMethod();
        *    Above means:
        *    someVoidMethod() does nothing the 1st time but throws an exception the 2nd time is called
        *
        * */
        doNothing().doReturn("Nothing").`when`(mockedList).clear()
        doReturn("Second value").`when`(mockedList)[1]
//        doCallRealMethod().`when`(mockedList)[2]
//        doAnswer { println("This is fourth value") }.`when`(mockedList[3])

        mockedList.clear()
        println(mockedList[0])
    }

    /**
     * Argument matchers allow flexible verification or stubbing
     * */
    @Test
    fun argumentTest() {
        // Mockito verifies argument values in natural java style
        //  : by using an equals() method. Sometimes, when extra flexibility is required then you might use argument matchers

        // Stubbing using built-in anyInt() argument matcher
        val mockedList = mock(ArrayList::class.java) as ArrayList<String>
        `when`(mockedList[ArgumentMatchers.anyInt()]).thenReturn("element")

        // stubbing using custom matcher (let's say isValid() returns your own matcher implementation)
//        `when`(mockedList.contains(argThat()))

        println(mockedList[999])

        // you can also verify using an argument matcher
        verify(mockedList)[ArgumentMatchers.anyInt()]

        // argument matchers can also be written as Java 8 Lamdas
        verify(mockedList).add(argThat { sth -> sth.length > 5})

        /*
        * ArgumentMatcher
        * ArgumentCapture
        * ArgumentThat
        * */
    }

    /**
     * You can create spies of real objects. When you use the spy then the real methods are called (unless a method was stubbed).
     * Real spies should be used carefully and occasionally, for example when dealing with legacy code.
     * */
    @Test
    fun spyTest() {

        val list = arrayListOf<String>()
        val spyList = spy(list)

        // you can stub out some methods
        `when`(spyList.size).thenReturn(200)

        // using the spy calls *real* methods
        spyList.add("one")
        spyList.add("two")

        println(spyList[0])
        println(spyList.size)

        verify(spyList).add("one")
        verify(spyList).add("two")

//        assertEquals(2, spyList.size)
        assertEquals(200, spyList.size)

        // Sometimes it's impossible or impractcal to use when(Object) for subbing spies.
        // Therefore when using spies please consider doReturn|Answer|Throw() family of methods for stubbing

        // Impossible : real method is called so spy.get(0) throws IndexOutOfBoundsException (the list is yet empty)
        `when`(spyList[2]).thenReturn("foo2")
        println(spyList[2])

        // You have to use doReturn() for stubbing
        doReturn("foo1").`when`(spyList)[2]
        println(spyList[2])

        /* Mockito *does not* delegate calls to the passed real instance,
        * instead it actually creates a copy of it.
        * So if you keep the real instance and interact with it,
        * don't expect the spied to be aware of those interaction
        * and their effect on real instance state.
        * The corollary(결과) is that when an *unstubbed* method is called *on the spy* but *not on the real instance*,
        * you won't see any effects on the real instance.
        * Watch out for final methods.
        * Mockito doesn't mock final methods so the bottom line is:
        * when you spy on real objects + you try to stub a final method = trouble.
        * Also you won't be able to verify those method as well.
        * */
    }


    /**
     * https://www.toptal.com/java/a-guide-to-everyday-mockito
     * In the previous sections, we configured our mocked methods with exact values as arguments
     * In those cases, Mockito just calls equals() internally to check if the expected values are equal to the actual values.
     *
     * Sometimes, though, we don’t know these values beforehand.
     *
     * Maybe we just don’t care about the actual value being passed as an argument,
     * or maybe we want to define a reaction for a wider range of values.
     * All these scenarios (and more) can be addressed with argument matchers.
     * The idea is simple: Instead of providing an exact value,
     * you provide an argument matcher for Mockito to match method arguments against.
     *
     * anyInt(), anyBoolean(), anyString(), isNull()
     *
     * Warning:
     * If you are using argument matchers, all arguments have to be privided matchers]
     * */
    @Test
    fun argumentsTest() {
        val mockedList = mock(ArrayList::class.java) as ArrayList<String>
        mockedList[0] = "One"
        mockedList[1] = "Two"

        `when`(mockedList[ArgumentMatchers.anyInt()])
                .thenReturn("One", "Two")

        `when`(mockedList[ArgumentMatchers.isNull()])
                .thenReturn("It's null!!")

        doReturn(100).`when`(mockedList).size
        println(mockedList[0])
        println(mockedList[1])
        println(mockedList[2])

//        println(mockedList[null]) -> print It's null


        assertEquals(2, mockedList.size)
    }

    // @RunWith(MockitoJUnitRunner::class)
    @Spy
    val spyList = arrayListOf<String>()

    @Test
    fun spyAnnotationTest() {
//        val spyList = arrayListOf<String>()
        spyList.add("one")
        spyList.add("two")

        verify(spyList).add("one")
        verify(spyList).add("two")

        assertEquals(2, spyList.size)
    }


}
