package com.innowireless.a01_kotlinimageparsinglist

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.util.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @Before
    fun setUp() {

    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        System.setProperty("dexmaker.dexcache", appContext.cacheDir.path)

        assertEquals("com.innowireless.a01_kotlinimageparsinglist", appContext.packageName)

        MockitoAnnotations.initMocks(this)

        /* mock() : Class 를 직접 넘겨주고 생성된 mock 은 자신의 모든 행동 기억.
        verify() 로 원하는 메서ㄷ소드가 특정 조건으로 실행됐는 지 검증 가능 */

        val list = mock(ArrayList<Int>()::class.java)

        list.add(1)
        list.clear()

        // verify : 실행여부 확인
        verify(list).add(1)
        verify(list).clear()

        /* 클래스랑 인터페이스랑 다른 점?

        클래스 뿐 아니라 인터페이스도 mock 가능 */
        val linkedList = mock(LinkedList::class.java)
        `when`(linkedList[0]).thenReturn(1)
//        `when`(linkedList[1]).thenThrow(RuntimeException())

        println(linkedList[0])

        /** ArgumentMatcher, ArgumentCaptor 확인
         * VerifyWithTimeout
         * MethodChanging -> .thenReturn, thenReturn
         *
         * */
    }
}
