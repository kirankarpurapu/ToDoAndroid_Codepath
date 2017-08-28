package com.example.kirank.todo.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kirank on 8/26/17.
 */

public class ParentTodoDataSource {

    static List<String> todoParentItems;

    static {
        todoParentItems = new ArrayList<>();
        todoParentItems.add("Parent 1");
        todoParentItems.add("Parent 2");
        todoParentItems.add("Parent 3");
        todoParentItems.add("Parent 4");
        todoParentItems.add("Parent 5");
        todoParentItems.add("Parent 1");
        todoParentItems.add("Parent 2");
        todoParentItems.add("Parent 3");
        todoParentItems.add("Parent 4");

    }

    public static List<String> getTodoParentItems() {
        return todoParentItems;
    }

    public static String getParentItem(int pos) {
        return todoParentItems.get(pos);
    }
}
