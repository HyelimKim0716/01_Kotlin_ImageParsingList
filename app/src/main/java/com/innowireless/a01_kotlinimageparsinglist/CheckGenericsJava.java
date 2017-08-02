package com.innowireless.a01_kotlinimageparsinglist;

/**
 * Created by 혜림 on 2017-07-30.
 *
 * Generic : 클래스 내부에서 사용할 데이터 타입을 외부에서 지정함
 */

public class CheckGenericsJava {

//    List<String> stringList = new ArrayList<>();
//    List<Object> objectList = stringList;
//
//    private void addStringList() {
//        objectList.add(1);
//
//        objectList.get(0);
//        String string = stringList.get(0);
//    }
//
//    class Generic<T> {
//        boolean isArgument = false;
//        String value = "Hello";
//    }
//
//    private void addOutputArg() {
//        ArrayList<Generic<? extends String>> items = new ArrayList<>();
//
//        items.add(new Generic<String>());
//        items.add(new Generic<String>());
//        items.add(new Generic<String>());
//
//        items.get(0);
//        items.get(1);
//        items.get(2);
//    }
//
//    /**
//     * ? extends T  : Read only
//     * ? super T    : Write only
//     */
//
//    private void printAll(ArrayList<? extends Generic<String>> itemList) {
//        for (Generic<String> item : itemList) {
//            if (item.isArgument)
//                Log.d("Generic", "item = " + item);
//
//        }
//
//        // Write 불가
//        itemList.add(new Generic<String>());
//
//        // Write: null 허용
//        itemList.add(null);
//
//        Log.d("Generic", "item 0 = " + itemList.get(0));
//        Log.d("Generic", "item 1 = " + itemList.get(1));        // null
//    }
//
//    private void addAll(ArrayList<? super Generic<String>> itemList) {
//        itemList.add(new Generic<String>());
//        itemList.add(null);
//
//        for (Generic<String> item : itemList) {
//            if (item.isArgument)
//                Log.d("Generic", "item = " + item);
//        }
//    }
//
//    /** Filter */
//    private void filterList(ArrayList<Generic<String>> itemList, Generic<? super Generic<String>> filter) {
//
//        for (int i = 0; i < itemList.size(); i++) {
//            if (filter.isArgument(itemList.get(i))) {
//
//            }
//        }
//    }
}
