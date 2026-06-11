package com.weekend.core.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@Component(
        service = Servlet.class,
        property = {
                "sling.servlet.paths=/bin/urlapi",
                "sling.servlet.methods=GET"
        }
)
public class UrlApiServlet extends SlingAllMethodsServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(
            SlingHttpServletRequest request,
            SlingHttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String apiUrl = request.getParameter("url");
        String limitParam = request.getParameter("limit");

        if (apiUrl == null || apiUrl.isEmpty()) {
            response.getWriter().write("{\"error\":\"URL Empty\"}");
            return;
        }

        int limit = -1;
        if (limitParam != null && !limitParam.isEmpty()) {
            try {
                limit = Integer.parseInt(limitParam);
            } catch (NumberFormatException e) {
                // Ignore invalid limit parameter and proceed without limiting
            }
        }

        try {
            String apiResponse = readUrl(apiUrl);
            
            Gson gson = new Gson();
            JsonElement jsonElement = gson.fromJson(apiResponse, JsonElement.class);

            if (limit > 0) {
                if (jsonElement.isJsonArray()) {
                    JsonArray jsonArray = jsonElement.getAsJsonArray();
                    if (jsonArray.size() > limit) {
                        JsonArray limitedArray = new JsonArray();
                        for (int i = 0; i < limit; i++) {
                            limitedArray.add(jsonArray.get(i));
                        }
                        jsonElement = limitedArray;
                    }
                } else if (jsonElement.isJsonObject()) {
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    for (java.util.Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                        if (entry.getValue().isJsonArray()) {
                            JsonArray jsonArray = entry.getValue().getAsJsonArray();
                            if (jsonArray.size() > limit) {
                                JsonArray limitedArray = new JsonArray();
                                for (int i = 0; i < limit; i++) {
                                    limitedArray.add(jsonArray.get(i));
                                }
                                jsonObject.add(entry.getKey(), limitedArray);
                            }
                        }
                    }
                }
            }

            response.getWriter().write(gson.toJson(jsonElement));

        } catch (Exception e) {
            response.setStatus(SlingHttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    protected String readUrl(String apiUrl) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);
        
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"))) {
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            return result.toString();
        }
    }
}