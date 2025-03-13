package com.app.loggin;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Slf4j
@Component
public class LogginInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LogginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Envolver el request y response para poder leer el cuerpo múltiples veces
        if (!(request instanceof ContentCachingRequestWrapper)) {
            request = new ContentCachingRequestWrapper(request);
        }
        if (!(response instanceof ContentCachingResponseWrapper)) {
            response = new ContentCachingResponseWrapper(response);
        }

        request.setAttribute("startTime", System.currentTimeMillis());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // Obtener las solicitudes y respuestas envueltas
        ContentCachingRequestWrapper requestWrapper = (ContentCachingRequestWrapper) request;
        ContentCachingResponseWrapper responseWrapper = (ContentCachingResponseWrapper) response;

        // Obtener la IP del cliente y el método de solicitud
        String clientIp = request.getRemoteAddr();
        String requestMethod = request.getMethod();
        String requestUri = request.getRequestURI();

        String requestBody = getRequestBody(requestWrapper);
        String responseBody = getResponseBody(responseWrapper);

        // Obtener el estado de la respuesta
        int status = response.getStatus();

        // Calcular la duración del proceso
        long startTime = (Long) request.getAttribute("startTime");
        long duration = System.currentTimeMillis() - startTime;

        logger.info("[IP: {}] [{}] {} | RequestBody: {} | Estado: {} | ResponseBody: {} | Duracion: {}ms",
                clientIp, requestMethod, requestUri, requestBody, responseWrapper.getStatus(), responseBody, duration);

        // Copiar el cuerpo de la respuesta de vuelta al response original
        responseWrapper.copyBodyToResponse();
    }

    // Método para obtener el cuerpo de la solicitud
    private String getRequestBody(ContentCachingRequestWrapper requestWrapper) {
        try {
            byte[] contentAsByteArray = requestWrapper.getContentAsByteArray();
            if (contentAsByteArray.length > 0) {
                return new String(contentAsByteArray, requestWrapper.getCharacterEncoding());
            }
            return "Vacio";
        } catch (Exception e) {
            return "Error al extraer el body";
        }
    }

    // Método para obtener el cuerpo de la respuesta (JSON o texto)
    private String getResponseBody(ContentCachingResponseWrapper responseWrapper) {
        try {
            byte[] contentAsByteArray = responseWrapper.getContentAsByteArray();
            if (contentAsByteArray.length > 0) {
                return new String(contentAsByteArray, responseWrapper.getCharacterEncoding());
            }
            return "Vacio";
        } catch (Exception e) {
            return "Error al extraer el body";
        }
    }
}

