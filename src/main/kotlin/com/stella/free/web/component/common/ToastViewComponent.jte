@param String message
@param String duration


<!-- JavaScript version -->
<!DOCTYPE html>
<html lang="en">
<div id="toast-element" class="fixed bg-gray-800 w-1/2 bottom-8 left-1/4 py-4 px-8 text-center text-white">
    <script>
        console.log("Hello")
        setTimeout(() =>
                document.getElementById('toast-element').style.display = 'none',
                ${duration}
        )
    </script>

    <div class="flex justify-between">
        <span>${message}</span>
        <button class="strong"
                onclick="document.getElementById('toast-element').style.display = 'none'">
            X
        </button>
    </div>
</div>
</html>


<!-- AlpineJS version -->
<%--<!DOCTYPE html>--%>
<%--<html lang="en" xmlns:th="http://www.thymeleaf.org">--%>
<%--<div th:attr="x-init='setTimeout(() => $el.style.display = \'none\',' + ${duration} + ')'"--%>
<%--     class="fixed bg-gray-800 w-1/2 bottom-8 left-1/4 py-4 px-8 text-center text-white"--%>
<%--     x-data="{}"--%>
<%--     x-ref="info">--%>
<%--  <div class="flex  justify-between">--%>

<%--    <span>${message}</span>--%>
<%--    <button @click="$refs.info.style.display = 'none'" class="strong">--%>
<%--      X--%>
<%--    </button>--%>
<%--  </div>--%>
<%--</div>--%>
<%--</html>--%>
