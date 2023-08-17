@import com.stella.free.web.component.table.CommonTableViewComponent
@import java.util.List

@param CommonTableViewComponent commonTableViewComponent




<div class="container mx-auto px-4">


    <form hx-post="/dummy" hx-target="#dummy-table" hx-indicator="#loading-spinner">
        <div class="flex justify-between mb-5">
            <input type="number" name="size" placeholder="number" class="input input-bordered w-full max-w-xs"/>

            <select class="select select-ghost w-full max-w-xs" name="type" required>
                <option disabled selected>Pick the Type</option>
                <option value="COROUTINE">Coroutine</option>
                <option value="TASK">Thread Pool</option>
                <option value="SINGLE">Single Thread</option>
            </select>

            <button type="submit" class="btn">생성</button>

        </div>
    </form>



    ${commonTableViewComponent.render(List.of())}

</div>







