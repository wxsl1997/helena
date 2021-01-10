package com.wxsl.helena.mq.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <a href="https://github.com/FasterXML/jackson-databind/wiki">wiki</a>
 */
@UtilityClass
public class JsonUtils {

    private static final String ISO_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private static final JsonMapper JSON_MAP = create();

    /**
     * 创建新的 objectMapper
     *
     * @return 新 objectMapper
     */
    public static JsonMapper create() {

        JsonMapper jsonMapper = JsonMapper.builder()
                // 允许空对象序列化
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                // 忽略未知字段
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                // 取消Date默认时间戳转化格式
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .defaultDateFormat(new SimpleDateFormat(ISO_TIME_PATTERN))
                // 开启属性名非标准json字符串 - "{age:25, name:'jackson'}"
                .configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)
                // 开启单引号解析属性 - "{'age':25, 'name':'jackson'}"
                .configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true)
                // 排除空值
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .build();

        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(TimeUtils.DATE_FORMATTER));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(TimeUtils.DATE_FORMATTER));
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(TimeUtils.DATE_TIME_FORMATTER));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(TimeUtils.DATE_TIME_FORMATTER));
        // 注册时间
        jsonMapper.registerModule(javaTimeModule);

        return jsonMapper;
    }

    /**
     * 获取 objectMapper
     *
     * @return objectMapper
     * @see JsonUtils#create 如果要修改 objectMapper 配置, 建议 使用 create 方法穿件新的 objectMapper
     */
    public static ObjectMapper objectMapper() {
        return JSON_MAP;
    }

    @SneakyThrows
    public static String toJson(Object obj) {
        if (Objects.isNull(obj)) {
            return StringUtils.EMPTY;
        }
        return objectMapper().writeValueAsString(obj);
    }

    @SneakyThrows
    public <T> T toObject(String json, Class<T> clazz) {

        if (StringUtils.isEmpty(json)) {
            return null;
        }
        return objectMapper().readValue(json, clazz);
    }

    @SneakyThrows
    public <T> T typeReference(String json, TypeReference<T> valueTypeRef) {

        if (StringUtils.isEmpty(json)) {
            return null;
        }
        return objectMapper().readValue(json, valueTypeRef);
    }


    @SneakyThrows
    public <T> List<T> toList(String jsonStr, Class<T> clazz) {
        ObjectMapper objectMapper = objectMapper();
        JavaType collectionType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, clazz);
        return objectMapper.readValue(jsonStr, collectionType);
    }
}
