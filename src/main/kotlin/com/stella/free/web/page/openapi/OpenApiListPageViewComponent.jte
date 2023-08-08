
@import java.util.*

@param com.stella.free.web.component.table.TableViewComponent tableViewComponent
@param com.stella.free.web.component.tabs.TabViewComponent tabViewComponent

@param java.util.List<String> categories


<div class="container mx-auto px-4">

    <div class="tabs tabs-boxed" x-data="{ show: false }">
        @for(String category : categories)
            ${tabViewComponent.render(category)}
        @endfor
    </div>

    ${tableViewComponent.render(categories.get(0))}

</div>


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