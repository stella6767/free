
@import com.stella.free.core.openapi.dto.*
@import java.util.*

@param  List<Entry> entries
@param  List<String> fieldNames
@param String category


<div class="overflow-x-auto" id="category-table">
    <table class="table">
        <!-- head -->
        <thead>
        <tr>
            @for(String name: fieldNames)
                <th>${name}</th>
            @endfor
        </tr>
        </thead>
        <tbody>
        <!-- row 1 -->

        @for(Entry entry: entries)
            <tr id="${entry.getLink()}">
                <td>${entry.getAPI()}</td>
                <td>${entry.getAuth()}</td>
                <td>${entry.getCategory()}</td>
                <td>${entry.getCors()}</td>
                <td>${entry.getDescription()}</td>
                <td>${entry.getHTTPS()}</td>
                <td><a href="${entry.getLink()}">${entry.getLink()}</a></td>
            </tr>

        @endfor
        </tbody>
    </table>
</div>