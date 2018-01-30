package com.innowireless.a01_kotlinimageparsinglist

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import io.realm.Realm
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.find

import Todo
import android.widget.Button
import java.util.*

class TodoEditFragment : Fragment() {
    val realm = Realm.getDefaultInstance()

    var mTodo: Todo? = null
    companion object {
        val TODO_ID_KEY = "todo_id_key"

        fun newInstance(id: String, param2: String): TodoEditFragment {
            val args = Bundle()
            args.putString(TODO_ID_KEY, id)

            val fragment = TodoEditFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null && arguments.containsKey(TODO_ID_KEY)) {
            val todoId =  arguments.getString(TODO_ID_KEY)
            val todoTitle = find<EditText>(R.id.todo_title)

        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View?
    = UI {
        verticalLayout {
            padding = dip(30)
            val title = editText {
                id = R.id.todo_title
                hintResource = R.string.title_hint
            }

            var desc = editText {
                id = R.id.todo_desc
                hintResource = R.string.description_hint
            }

            button {
                id = R.id.todo_add
                textResource = R.string.add_todo
                onClick {
                    createTodoFrom(title, desc)
                }
            }
        }
    }.view

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (arguments != null && arguments.containsKey(TODO_ID_KEY)) {
            val todoId = arguments.getString(TODO_ID_KEY)
            mTodo = realm.where(Todo::class.java).equalTo("id", todoId).findFirst()
            val todoTitle = find<EditText>(R.id.todo_title)
            todoTitle.setText(mTodo?.title)
            val todoDescription = find<EditText>(R.id.todo_desc)
            todoDescription.setText(mTodo?.description)
            val add = find<Button>(R.id.todo_add)
            add.setText("Save")

        }
    }
    /**
     * TODO item 을 만드는 private 함수
     *
     * @param title - title edit text
     * @param desc - edit text 설명
     */
    private fun createTodoFrom(title: EditText, desc: EditText) {
        realm.beginTransaction()

        // Either update the edited object or create a new one.
        var todo = mTodo ?: realm.createObject(Todo::class.java)
        todo.id = mTodo?.id ?: UUID.randomUUID().toString()
        todo.title = title.text.toString()
        todo.description = desc.text.toString()
        realm.commitTransaction()

        // Go back to previous activity
        activity.supportFragmentManager.popBackStack()
    }

}