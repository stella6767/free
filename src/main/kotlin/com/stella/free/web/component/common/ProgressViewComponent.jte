
<style>
    .my-indicator{
        display:none;
    }
    .htmx-request .my-indicator{
        display:inline;
    }
    .htmx-request.my-indicator {
        display: inline;
    }

</style>


<div id="loading-spinner"
        class="my-indicator absolute right-1/2 bottom-1/2  transform translate-x-1/2 translate-y-1/2 z-20">
    <div
            class="border-t-transparent border-solid animate-spin  rounded-full border-blue-400 border-8 h-64 w-64"></div>
</div>