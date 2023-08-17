@import de.tschuehly.spring.viewcomponent.jte.ViewContext

@param ViewContext footer
@param ViewContext header
@param ViewContext nestedView


<style>
    #content-body{
        height : auto;
        min-height: 100%;
    }
</style>



${header}

<div id="content-body">


    ${nestedView}

</div>

${footer}