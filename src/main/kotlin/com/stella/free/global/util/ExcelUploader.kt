package com.stella.free.global.util


import org.dhatim.fastexcel.Color
import org.dhatim.fastexcel.Workbook
import org.dhatim.fastexcel.Worksheet
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream


object ExcelUploader {

    private val log = logger()

    fun createExcelFileFromHashMap(objs: MutableList<MutableMap<String, Any>>): ByteArrayInputStream {

        val os = ByteArrayOutputStream()
        val wb = Workbook(os, "TEST", "1.0")
        val ws: Worksheet = wb.newWorksheet("Sheet 1")

        val excelHeader: List<String> = objs.first().map { entrie ->
            entrie.key
        }
        createWorkSheetHeader(excelHeader, ws)
        objs.forEachIndexed { index, mutableMap ->
            excelHeader.forEachIndexed { index2, field ->
                val field = mutableMap.get(field) ?: ""
                ws.value(index + 1, index2, field.toString())
            }

        }

        ws.flush()
        wb.finish()

        return ByteArrayInputStream(os.toByteArray())
    }


    private fun createWorkSheetHeader(headers: List<String>, ws: Worksheet) {
        for (i in headers.indices) {
            ws.value(0, i, headers[i])
        }
        ws.range(0, 0, 0, headers.size - 1).style().horizontalAlignment("center").set()
        ws.range(0, 0, 0, headers.size - 1).style().fillColor(Color.LIGHT_GREEN).set()
    }




}