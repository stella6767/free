@import com.stella.free.entity.Todo
@import com.stella.free.view.component.TodoViewComponent
@import com.stella.free.view.component.pagination.PaginationViewComponent
@import de.tschuehly.spring.viewcomponent.jte.ViewContext
@import org.springframework.data.domain.Page
@import java.util.*

@param Page<Todo> todos
@param TodoViewComponent todoViewComponent
@param PaginationViewComponent paginationViewComponent


<div class="h-100 w-full flex items-center justify-center bg-teal-lightest font-sans">
    <div class="bg-white rounded shadow p-6 m-4 w-full lg:w-3/4 lg:max-w-lg">
        <div class="mb-4">
            <h1 class="text-5xl font-bold">Todo List</h1>
            <div class="flex mt-4">
                <input class="shadow appearance-none border rounded w-full py-2 px-3 mr-4 text-grey-darker"
                       autofocus="autofocus"
                       id="new-todo"
                       name="newTodo"
                       placeholder="To do..."
                       required type="text"
                >
                <button class="flex-no-shrink p-2 border-2 rounded text-teal border-teal hover:text-white hover:bg-teal"

                        hx-include="#new-todo"
                        hx-trigger="click"
                        hx-post="/todo"
                        hx-target="#todo-list"
                        hx-swap="afterbegin"

                >Add
                </button>
            </div>
        </div>


        <div id="todo-list">

            @for(Todo todo : todos)

                    ${todoViewComponent.render(todo)}

            @endfor
        </div>
    </div>


</div>

${paginationViewComponent.render(todos, "todos")}
