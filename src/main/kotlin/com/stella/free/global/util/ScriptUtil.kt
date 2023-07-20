package com.stella.free.global.util

object ScriptUtil {

    fun alertErrorAndHistoryBack(msg: String): String {

        val script = """
            <script>
            alert('$msg');
            history.back();
            </script>
        """.trimIndent()

        return script
    }


    fun alertError(msg: String): String {
        return """
            <script>
            alert('$msg');          
            </script>
        """.trimIndent()
    }





}