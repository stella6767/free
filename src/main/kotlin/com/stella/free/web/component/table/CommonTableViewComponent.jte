@import java.util.*
@import com.stella.free.core.scrap.service.DummyDataJenService.*

@param  List<String> fieldNames
@param List<DummyPerson> datas


<div class="overflow-x-auto" id="dummy-table">
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

        @for(DummyPerson person: datas)
            <tr>
                <td>${person.getName()}</td>
                <td>${person.getEmail()}</td>
                <td>${person.getDomain()}</td>
                <td>${person.getIpaddr()}</td>
                <td>${person.getCompanyName()}</td>
                <td>${person.getCatchPhrase()}</td>
                <td>${person.getStreetAddress()}</td>
                <td>${person.getAddress()}</td>
            </tr>

        @endfor
        </tbody>
    </table>
</div>