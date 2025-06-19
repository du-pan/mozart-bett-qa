package com.mozart.bett.qa.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertNotNull;

@Slf4j
public class ObjectConverterUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static JSONObject getEntityJsonObject(final String fileSource) {
        log.info("Construct an JSON Object.");
        if (fileSource.matches("^(.+)/([^/]+)(.json)$")) {
            log.info("Get JSON Object from file: ['{}']", fileSource);
            String fileContent = "";
            try {
                log.info("Read from file [{}]", fileSource);
                fileContent = new String(Files.readAllBytes(Path.of(fileSource)));
            } catch (IOException e) {
                log.error("IOException exception detected.");
                log.error("File cannot be read.");
            }
            final JSONObject jsonObject = new JSONObject(fileContent);
            assertNotNull(jsonObject);
            return jsonObject;
        } else {
            log.info("File source is not valid OR body has already been sent as JSONObject.");
            log.info("Returning empty JSONObject.");
            return new JSONObject();
        }
    }

    public static String extractFromJsonResponse(Response response, final String jsonPath) {
        return response.body().jsonPath().getString(jsonPath).replace("\"", "");
    }

    /**
     * Edit values of JSON body. @Important: List<String> values has to have same size as String...
     * jsonPath length
     *
     * @param fileSource JSON file source path
     * @param values     Values to be assigned to JSON properties
     * @param jsonPath   Array of JSON properties which requires values change
     * @return JSONObject
     */
    public static JSONObject editJsonObjectPropValue(
            final String fileSource, final List<String> values, final String... jsonPath) {
        final JSONObject jsonObject = getEntityJsonObject(fileSource);
        assertNotNull(jsonObject);

        for (int i = 0; i < jsonPath.length; i++) {
            jsonObject.remove(jsonPath[i]);
            jsonObject.put(jsonPath[i], values.get(i));
        }

        return jsonObject;
    }

    public static JSONObject editJsonObjectPropValue(
            final String fileSource, final String value, final String jsonPath) {
        return editJsonObjectPropValue(fileSource, List.of(value), jsonPath);
    }

    public static List<Map<String, Object>> getResponseJsonNodeObject(
            final Response response, final String nodeName) {
        List<Map<String, Object>> expectedNodeList = new ArrayList<>();
        try {
            final JsonNode rootNode = objectMapper.readTree(response.asString());
            final JsonNode expectedNode = rootNode.get(nodeName);
            expectedNodeList = objectMapper.readValue(expectedNode.traverse(), new TypeReference<>() {
            });
        } catch (IOException e) {
            log.error("IOException exception detected.");
            e.printStackTrace();
        }
        return expectedNodeList;
    }

    public static List<String> getJsonStringList(final JSONObject jsonObject, final String jsonPath) {
        final JSONArray jsonArray = jsonObject.getJSONArray(jsonPath);
        final List<String> stringList = new ArrayList<>();

        jsonArray.forEach(value -> stringList.add(value.toString()));

        return stringList;
    }
}
