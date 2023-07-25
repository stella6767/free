@import de.tschuehly.spring.viewcomponent.jte.ViewContext
@import com.stella.free.core.account.entity.type.SignType
@import java.util.Arrays
@import java.util.List


@param SignType[] signTypes

<!-- Open the modal using ID.showModal() method -->


<!-- Open the modal using ID.showModal() method -->
<dialog id="login_modal" class="modal">
    <form method="dialog" class="modal-box">
        <h1 class="font-bold text-center text-2xl mb-5">Social Login</h1>

        <div class="bg-white shadow w-full rounded-lg divide-y divide-gray-200">

            <div class="p-5">
                <div class="mt-3 grid space-y-4">

                    @for(SignType signType : signTypes)

                        <button class="group h-12 px-6 border-2 border-gray-300 rounded-full transition duration-300
 hover:border-blue-400 focus:bg-blue-50 active:bg-blue-100" onclick="location.href='${signType.getAuthorizationUrl()}'"
                        >
                            <div class="relative flex items-center space-x-4 justify-center">
                                <img src="${signType.getImgUrl()}"
                                     class="absolute left-0 w-5" alt="logo">
                                <span class="block w-max font-semibold tracking-wide text-gray-700 text-sm transition duration-300 group-hover:text-blue-600 sm:text-base">
                                    Continue with ${signType.getClientName()}
                                </span>
                            </div>
                        </button>
                    @endfor
                    
                </div>
            </div>

        </div>


        <div class="modal-action">
            <!-- if there is a button in form, it will close the modal -->
            <button class="btn">Close</button>
        </div>
    </form>
</dialog>


