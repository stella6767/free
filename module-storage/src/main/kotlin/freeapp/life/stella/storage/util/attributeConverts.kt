package freeapp.life.stella.storage.util


import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter(autoApply = true) //true 면 모든 boolean 타입에 적용 @Convert를 붙이지 않더라도.
class SBooleanToYNConverter : AttributeConverter<Boolean?, String> {
    override fun convertToDatabaseColumn(attribute: Boolean?): String {
        return if (attribute != null && attribute) "Y" else "N"
    }

    override fun convertToEntityAttribute(dbData: String?): Boolean {
        if (dbData == null) return false
        return "Y" == dbData
    }
}

