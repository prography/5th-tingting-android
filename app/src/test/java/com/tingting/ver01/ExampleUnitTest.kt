package com.tingting.ver01

import com.tingting.ver01.SearchTeam.SearchTeamData
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)

        var a = SearchTeamData(1,"123",3);

        assertEquals(a.img1,1)

    }
}
