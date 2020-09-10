package com.jy.infonet.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;

/**
 * 序列化和反序列化set
 */
public class JacksonUtil {
    public static HashSet<Integer> deserializeToSet(String object) {
        try {
            return new ObjectMapper().readValue(object, new TypeReference<HashSet<Integer>>() {});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String serializeToString(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
