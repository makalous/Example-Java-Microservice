package com.example.microservice.app.qos;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class QoSFilter extends OncePerRequestFilter {
    private static final String START_HEADER = "X-Start-Req";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        long tServerStart = System.nanoTime(); // t2 ≈ request enters service

        filterChain.doFilter(request, response);

        long tServerEnd = System.nanoTime();   // t3 ≈ response ready
        long latencyNs = tServerEnd - tServerStart;

        // Response Time = now - client start
        String startHeader = request.getHeader(START_HEADER);
        if (startHeader != null) {
            try {
                long tClientStartMs = Long.parseLong(startHeader);
                long responseTimeMs =
                        System.currentTimeMillis() - tClientStartMs;

                log.info(
                        "QoS | path={} | status={} | latency={} ms | responseTime={} ms",
                        request.getRequestURI(),
                        response.getStatus(),
                        latencyNs / 1_000_000,
                        responseTimeMs
                );
            } catch (NumberFormatException e) {
                log.warn("Invalid X-Start-Req header: {}", startHeader);
            }
        } else {
            log.info(
                    "QoS | path={} | status={} | latency={} ms",
                    request.getRequestURI(),
                    response.getStatus(),
                    latencyNs / 1_000_000
            );
        }
        int status = response.getStatus();
        boolean success = !String.valueOf(status).startsWith("5");
        ReliabilityTracker.getInstance().recordRequest(success);
        ThroughputTracker.getInstance().recordRequest();
    }
}