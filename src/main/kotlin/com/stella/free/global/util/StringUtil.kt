package com.stella.free.global.util

import java.util.regex.Pattern

object StringUtil {

    fun removeSpecialCharacters(input: String): String {
        // 정규표현식 패턴: 숫자 및 문자를 제외한 모든 특수 문자 및 공백 (A-Za-z0-9는 숫자와 문자)
        val pattern = "[^A-Za-z0-9]"
        val p = Pattern.compile(pattern)
        val m = p.matcher(input)
        // 매칭되는 패턴을 빈 문자열로 치환하여 제거
        return m.replaceAll("")
    }

}