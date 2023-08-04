@import java.util.*
@import com.stella.free.global.util.StringUtil

@param String category




<div>

    <a
            id="category-tab-${StringUtil.INSTANCE.removeSpecialCharacters(category)}"
            class="cursor-pointer tab tab-lifted m-tab"
            hx-get="/table/${category}"
            hx-target="#category-table"
            onclick="showActive('${StringUtil.INSTANCE.removeSpecialCharacters(category)}')"
    >
        ${category}
    </a>

</div>

<%--@click="show = !show" :class="{ 'tab-active': show }"--%>

<script defer>

    const showActive = (category) => {

        const aTeg = document.querySelectorAll('.m-tab');
        aTeg.forEach((el) => {
            el.classList.remove('tab-active');
        });

        let selector = "#category-tab-"+category;
        console.log("selector", selector)

        const element = document.querySelector(selector);
        element.classList.add("tab-active");
    }

</script>