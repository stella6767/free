@import de.tschuehly.spring.viewcomponent.jte.ViewContext




<!-- Open the modal using ID.showModal() method -->


<!-- Open the modal using ID.showModal() method -->
<dialog id="login_modal" class="modal">
    <form method="dialog" class="modal-box">
        <h1 class="font-bold text-center text-2xl mb-5">Social Login</h1>

        <div class="bg-white shadow w-full rounded-lg divide-y divide-gray-200">

            <div class="p-5">
                <div class="grid grid-cols-3 gap-1">
                    <button type="button"
                            class="transition duration-200 border border-gray-200 text-gray-500 w-full py-2.5 rounded-lg text-sm shadow-sm hover:shadow-md font-normal text-center inline-block"
                            onclick="location.href='/oauth2/authorization/facebook'"

                    >Facebook</button>
                    <button type="button"
                            class="transition duration-200 border border-gray-200 text-gray-500 w-full py-2.5 rounded-lg text-sm shadow-sm hover:shadow-md font-normal text-center inline-block"
                            onclick="location.href='/oauth2/authorization/google'"
                    >Google</button>
                    <button type="button"
                            class="transition duration-200 border border-gray-200 text-gray-500 w-full py-2.5 rounded-lg text-sm shadow-sm hover:shadow-md font-normal text-center inline-block"
                            onclick="location.href='/oauth2/authorization/github'"
                    >Github</button>
                </div>
            </div>

        </div>


        <div class="modal-action">
            <!-- if there is a button in form, it will close the modal -->
            <button class="btn">Close</button>
        </div>
    </form>
</dialog>


