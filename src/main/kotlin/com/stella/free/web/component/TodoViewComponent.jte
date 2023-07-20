@import com.stella.free.entity.Todo
@import java.util.*

@param Todo todo



<div id="todo-container-${todo.getId()}">
    <div class="flex mb-4 items-center" id="todo-box">


        <p class="w-full text-grey-darkest ${todo.getStatus() == true ? "line-through" : "" }">${todo.getContent()}</p>
        <button class="flex-no-shrink p-2 ml-4 mr-2 border-2 rounded hover:text-white text-green border-green hover:bg-green"
                hx-trigger="click"
                hx-put="/todo/${todo.getId()}"
                hx-target="#todo-container-${todo.getId()}"
                hx-swap="innerHTML"
        >
            ${todo.getStatus() == true ? "Yet" : "Done"}
        </button>
        <button class="flex-no-shrink p-2 ml-2 border-2 rounded text-red border-red hover:text-white hover:bg-red"
                hx-confirm="Are you sure?"
                hx-swap="outerHTML swap:1s"
                hx-trigger="click"
                hx-delete="/todo/${todo.getId()}"
                hx-target="closest div"
        >
            Remove
        </button>

    </div>
</div>