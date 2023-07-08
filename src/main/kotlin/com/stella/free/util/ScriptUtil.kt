package com.stella.free.util

object ScriptUtil {

    fun alertError(msg: String): String {

        val script = """
            <script>
            alert('$msg');
            history.back();
            </script>
        """.trimIndent()

        return script
    }


}