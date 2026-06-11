package com.weekend.core.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletRequest;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(AemContextExtension.class)
class UrlApiServletTest {

    private String mockResponseData = "[]";
    private boolean throwExceptionOnRead = false;

    private UrlApiServlet fixture = new UrlApiServlet() {
        @Override
        protected String readUrl(String apiUrl) throws IOException {
            if (throwExceptionOnRead) {
                throw new IOException("Connection failed");
            }
            return mockResponseData;
        }
    };

    @Test
    void doGet_emptyUrl(AemContext context) throws ServletException, IOException {
        MockSlingHttpServletRequest request = context.request();
        MockSlingHttpServletResponse response = context.response();

        // No URL parameter
        fixture.doGet(request, response);

        assertTrue(response.getContentType().startsWith("application/json"));
        assertEquals("UTF-8", response.getCharacterEncoding());
        assertEquals("{\"error\":\"URL Empty\"}", response.getOutputAsString());
    }

    @Test
    void doGet_withArrayResponse_noLimit(AemContext context) throws ServletException, IOException {
        mockResponseData = "[{\"car\":\"Tesla\"},{\"car\":\"BMW\"},{\"car\":\"Audi\"}]";
        throwExceptionOnRead = false;

        MockSlingHttpServletRequest request = context.request();
        request.setParameterMap(java.util.Collections.singletonMap("url", new String[]{"http://example.com/api"}));
        MockSlingHttpServletResponse response = context.response();

        fixture.doGet(request, response);

        assertEquals(mockResponseData, response.getOutputAsString());
    }

    @Test
    void doGet_withArrayResponse_withLimit(AemContext context) throws ServletException, IOException {
        mockResponseData = "[{\"car\":\"Tesla\"},{\"car\":\"BMW\"},{\"car\":\"Audi\"}]";
        throwExceptionOnRead = false;

        MockSlingHttpServletRequest request = context.request();
        java.util.Map<String, Object> params = new java.util.HashMap<>();
        params.put("url", "http://example.com/api");
        params.put("limit", "2");
        request.setParameterMap(params);
        MockSlingHttpServletResponse response = context.response();

        fixture.doGet(request, response);

        assertEquals("[{\"car\":\"Tesla\"},{\"car\":\"BMW\"}]", response.getOutputAsString());
    }

    @Test
    void doGet_withWrappedArrayResponse_withLimit(AemContext context) throws ServletException, IOException {
        mockResponseData = "{\"cars\":[{\"car\":\"Tesla\"},{\"car\":\"BMW\"},{\"car\":\"Audi\"}]}";
        throwExceptionOnRead = false;

        MockSlingHttpServletRequest request = context.request();
        java.util.Map<String, Object> params = new java.util.HashMap<>();
        params.put("url", "http://example.com/api");
        params.put("limit", "1");
        request.setParameterMap(params);
        MockSlingHttpServletResponse response = context.response();

        fixture.doGet(request, response);

        assertEquals("{\"cars\":[{\"car\":\"Tesla\"}]}", response.getOutputAsString());
    }

    @Test
    void doGet_withException(AemContext context) throws ServletException, IOException {
        throwExceptionOnRead = true;

        MockSlingHttpServletRequest request = context.request();
        request.setParameterMap(java.util.Collections.singletonMap("url", new String[]{"http://example.com/api"}));
        MockSlingHttpServletResponse response = context.response();

        fixture.doGet(request, response);

        assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response.getStatus());
        assertTrue(response.getOutputAsString().contains("error"));
        assertTrue(response.getOutputAsString().contains("Connection failed"));
    }
}
