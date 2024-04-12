@import java.util.*
@import com.stella.free.global.util.CustomExtensionKt


@param String category




<div>
    <a
            id="category-tab-${CustomExtensionKt.removeSpecialCharacters(category)}"
            class="cursor-pointer tab tab-lifted m-tab"
            hx-get="/table/${category}"
            hx-target="#category-table"
            onclick="showActive('${CustomExtensionKt.removeSpecialCharacters(category)}')"
    >
        ${category}
    </a>

</div>

<%--@click="show = !show" :class="{ 'tab-active': show }"--%>

