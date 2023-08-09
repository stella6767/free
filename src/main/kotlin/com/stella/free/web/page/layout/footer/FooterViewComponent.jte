
@param String creator
@param String creatorEmail
@param String githubUrl



</br>

<footer class="footer footer-center p-4 bg-base-300 text-base-content ">
    <div>
        <p>Created by ${creator}</p>
        <p>📞 ${creatorEmail}</p>
        <a href="${githubUrl}" target='_blank' style="text-decoration: none;" >GitHub</a>
    </div>
</footer>


<script>

    document.body.addEventListener('htmx:responseError', function (evt) {

        console.log("check error",evt.detail.xhr.responseText)
        alert(JSON.parse(evt.detail.xhr.responseText).detail);

    });

    htmx.logger = function(elt, event, data) {
        if(console) {
            console.log(event, elt, data);
        }
    }

</script>

</body>


</html>