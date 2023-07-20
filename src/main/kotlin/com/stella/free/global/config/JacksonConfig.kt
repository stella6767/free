package com.stella.free.global.config

import com.stella.free.global.util.logger
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.databind.cfg.MapperConfig
import com.fasterxml.jackson.databind.introspect.AnnotatedField
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod
import com.fasterxml.jackson.datatype.hibernate5.jakarta.Hibernate5JakartaModule
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.addDeserializer
import com.fasterxml.jackson.module.kotlin.addSerializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.apache.commons.lang3.reflect.FieldUtils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Configuration
class JacksonConfig {

    private val log = logger()


    //https://stackoverflow.com/questions/26744885/jackson-objectmapper-upper-lower-case-issues
    class MyPropertyNamingStrategy : PropertyNamingStrategy() {
        override fun nameForField(config: MapperConfig<*>?, field: AnnotatedField, defaultName: String?): String {
            return field.name
        }

        override fun nameForGetterMethod(
            config: MapperConfig<*>?,
            method: AnnotatedMethod,
            defaultName: String,
        ): String {
            return convert(method, defaultName)
        }

        override fun nameForSetterMethod(
            config: MapperConfig<*>?,
            method: AnnotatedMethod,
            defaultName: String,
        ): String {
            return convert(method, defaultName)
        }

        private fun convert(method: AnnotatedMethod, defaultName: String): String {
            val clazz = method.declaringClass
            val flds = FieldUtils.getAllFieldsList(clazz)
            for (fld in flds) {
                if (fld.name.equals(defaultName, ignoreCase = true)) {
                    return fld.name
                }
            }
            return defaultName
        }
    }

    @Bean
    fun objectMapper(): ObjectMapper {

        val objectMapper = ObjectMapper()
        val javaTimeModule = JavaTimeModule()


        //LocalDateTime 원하는 커스텀 포맷으로 직/역직렬화
        javaTimeModule.addSerializer(LocalDateTime::class, CustomLocalDateTimeSerializer())
        javaTimeModule.addDeserializer(LocalDateTime::class, CustomLocalDateTimeDeSerializer())


        objectMapper.registerModule(javaTimeModule)
        //objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        //아직 불러오지 않은 엔티티에 대해 null값을 내려주는 모듈이다. lazy loading
        objectMapper.registerModule(Hibernate5JakartaModule())



        // 모르는 property에 대해 무시하고 넘어간다.
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)


        objectMapper.registerModule(
            KotlinModule.Builder()
                .withReflectionCacheSize(512)
                .configure(KotlinFeature.NullToEmptyCollection, false)
                .configure(KotlinFeature.NullToEmptyMap, false)
                .configure(KotlinFeature.NullIsSameAsDefault, false)
                .configure(KotlinFeature.SingletonSupport, false)
                .configure(KotlinFeature.StrictNullChecks, false)
                .build())

        // 시간 관련 객체(LocalDateTime, java.util.Date)를 직렬화 할 때 timestamp 숫자값이 아닌 포맷팅 문자열로 한다.
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)

        // Set PropertyNamingStrategy to use original field names
        objectMapper.propertyNamingStrategy = MyPropertyNamingStrategy()

        return objectMapper
    }



    class CustomLocalDateTimeSerializer(): JsonSerializer<LocalDateTime>() {
        override fun serialize(value: LocalDateTime, gen: JsonGenerator, serializers: SerializerProvider) {
            gen.writeString(formatter.format(value))
        }
    }


    class CustomLocalDateTimeDeSerializer(): JsonDeserializer<LocalDateTime>(){
        override fun deserialize(p: JsonParser, ctxt: DeserializationContext): LocalDateTime {
            return LocalDateTime.parse(p.text, formatter)
        }
    }




    companion object {
        private val dateTimeFormat = "yyyy-MM-dd HH:mm:ss"
        private val formatter = DateTimeFormatter.ofPattern(dateTimeFormat, Locale.KOREA)

    }

}