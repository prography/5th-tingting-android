package com.example.tintint_jw.Model

import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.io.IOException
import java.lang.reflect.Type


class ToStringConverterFactory : Converter.Factory(){
    private val MEDIA_TYPE = MediaType.parse("text/plain")


    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        return if (String::class.java == type) {
            object : Converter<ResponseBody, String> {
                @Throws(IOException::class)
                override fun convert(value: ResponseBody): String {
                    return value.string()
                }
            }
        } else null
    }

    fun requestBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody>? {

        return if (String::class.java == type) {
            object : Converter<String, RequestBody> {
                @Throws(IOException::class)
                override fun convert(value: String): RequestBody {
                    return RequestBody.create(MEDIA_TYPE, value)
                }
            }
        } else null
    }
}