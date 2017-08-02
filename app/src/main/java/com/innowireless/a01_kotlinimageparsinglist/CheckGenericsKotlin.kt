package com.innowireless.a01_kotlinimageparsinglist

/**
 * Created by 혜림 on 2017-07-30.
 *
 * http://thdev.tech/kotlin/android/2016/09/08/Kotlin-Generics.html
 */
//class Foo<T>(t: T) {
//    var value = t
//}
//
//val fooInt: Foo<Int> = Foo<Int>(1)
//val fooString: Foo<String> = Foo("fooString")       // 타입 추론
//
///**
// * out T    : ? extends T
// * in t     : ? super T
// */
//data class Generic<T> (var isArgument: Boolean = false) {
//    var value: Any? = null
//}
//
//fun useGeneric() {
//    val itemList = ArrayList<Generic<String>>()
//
//    itemList.add(Generic<String>(true))     // write
//    itemList.add(Generic<String>(false))
//
//    itemList[0]     // read
//    itemList[1]
//}
//                            // extends : Read only
//fun printAll(itemList: ArrayList<out Generic<String>>) {
//    itemList.add(Generic<String>(true))
//
//    for(item in itemList) {
//        if (item.isArgument)        // Read
//            print(item.value)
//    }
//
//    itemList
//            .filter { it.isArgument }
//            .forEach { println(it) }
//
//    itemList.add(null)      // Error Kotlin does not provide null
//}
//
//                            // super : Write only
//fun addAll(itemList: ArrayList<in Generic<String>>) {
//    itemList.add(Generic<String>(true))
//    itemList.add(Generic<String>(false))
//    itemList.add(null)
//
//    for (item in itemList) {
//        if (item.isArgument)            //
//            println(item)
//    }
//
//}
//
///** in/out 명시하지 않아도 되는 경우 */
//var item: String = ""
//    get() = "Hello"
//    set(value) { if (value.isEmpty()) field = "Hello"}
//
//
//fun copy(from: Array<Any>, to: Array<Any>) {
//    for (i in from.indices)
//        to[i] = from[i]
//}
//
//                    // read only
//fun copy2(from: Array<out Any>, to: Array<Any>) {
//    for (i in from.indices)
//        to[i] = from[i]
//}
//
//
//fun copyList() {
//    val ints: Array<Int> = arrayOf(1, 2, 3)
//    val any = Array<Any>(3)
//
//    copy2(ints, any)     // Type mismatch
//    any[2] = 10
//    any[3] = "Hello"
//}
//
//fun add(value: String, to: ArrayList<in String>) {
//    to.add(value)
//}
//
///**
// * object : *
// */
//
//class Generic2<T> (var value: T)
//fun addObject() {
//    val items = ArrayList<Generic2<*>>()
//    items.add(Generic2<String>("Hello"))
//    items.add(Generic2<Boolean>(false))
//    items.add(Generic2<Int>(30))
//
//    when (items[0].value) {
//        is String -> println("String value = ${items[0].value}")
//        is Boolean -> println("Boolean value = ${items[0].value}")
//        is Int -> println("Int value = ${items[0].value}")
//    }
//
//}
//
//fun <T> getList() : List<T> {
//    return ArrayList()
//}

