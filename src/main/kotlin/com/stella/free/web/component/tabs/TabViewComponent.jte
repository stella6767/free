@import java.util.*

@param List<String> categories



<div class="tabs tabs-boxed">
    @for(String category : categories)
        <a
                class="cursor-pointer tab tab-lifted"
                hx-get="/"

        >${category}</a>
    @endfor
</div>