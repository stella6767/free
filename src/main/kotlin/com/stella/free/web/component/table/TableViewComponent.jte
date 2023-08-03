
@import com.stella.free.core.openapi.dto.*
@import java.util.*

@param  List<Entry> entries
@param  List<String> fieldNames


<div class="overflow-x-auto">
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
            <tr>
                <td>${entry.getAPI()}</td>
                <td>${entry.getAuth()}</td>
                <td>${entry.getCategory()}</td>
                <td>${entry.getCors()}</td>
                <td>${entry.getDescription()}</td>
                <td>${entry.getHTTPS()}</td>
                <td>${entry.getLink()}</td>
            </tr>

        @endfor
        </tbody>
    </table>
</div>