@import org.springframework.data.domain.Page

@param Page<Object> pages
@param String endpoint

<div class="flex justify-center">

    <div class="join ">

        !{var maxPage = 5;}
        !{var start =  Math.floor (pages.getNumber() / maxPage) * maxPage + 1;}
        !{var end = (start + maxPage -1 ) < pages.getTotalPages() ? start + maxPage -1 : pages.getTotalPages(); }

        <button class="join-item btn" onclick="location.href='/${endpoint}?page=0'">First</button>


        @if(pages.isFirst() )
            <button class="join-item btn" disabled>&lt;</button>
        @else
            <button class="join-item btn"
                    onclick="location.href='/${endpoint}?page=${pages.getNumber()-1}'">&lt;
            </button>
        @endif


        @for(int i = (int) start; i <= end;  i++ )

            @if( i == (pages.getNumber() + 1))
                <input class="join-item btn btn-square"
                       type="radio"
                       name="options"
                       aria-label="${i}"
                       onclick="window.location.href='/${endpoint}?page=${i -1}'"
                       checked
                />

            @else
                <input class="join-item btn btn-square"
                       type="radio"
                       name="options"
                       aria-label="${i}"
                       onclick="window.location.href='/${endpoint}?page=${i - 1}'"
                />

            @endif

        @endfor

        @if(pages.isLast() )
            <button class="join-item btn" disabled>&gt;</button>
        @else
            <button class="join-item btn"
                    onclick="location.href='/${endpoint}?page=${pages.getNumber()+1}'"
            >&gt;
            </button>
        @endif

        <button class="join-item btn"
                onclick="location.href='/${endpoint}?page=${pages.getTotalPages() -1}'">Last
        </button>

    </div>

</div>


