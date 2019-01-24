package br.com.magazineluiza.v1.customers.util;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

public final class WebLoggingUtil {
	
	private WebLoggingUtil() {
		
	}

    public static String readPayloadRequest(HttpServletRequest request) throws IOException {
    	 String payload = null;
         ContentCachingRequestWrapper wrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
         if (wrapper != null) {
             byte[] buf = wrapper.getContentAsByteArray();
             if (buf.length > 0) {
                 payload = new String(buf, 0, buf.length, wrapper.getCharacterEncoding());
             }
         }
         return payload;
    }
    
    public static String readPayloadResponse(HttpServletResponse response) throws IOException {
    	String payload = null;
        ContentCachingResponseWrapper wrapper =
            WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
        if (wrapper != null) {
            byte[] buf = wrapper.getContentAsByteArray();
            if (buf.length > 0) {
                payload = new String(buf, 0, buf.length, wrapper.getCharacterEncoding());
                wrapper.copyBodyToResponse();
            }
        }
        return payload;
    }

}
