package br.com.magazineluiza.v1.customers.zipkin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import zipkin2.Span;
import zipkin2.reporter.AsyncReporter;
import zipkin2.reporter.Reporter;
import zipkin2.reporter.Sender;
import zipkin2.reporter.okhttp3.OkHttpSender;

@Configuration
@ConditionalOnProperty(name = "monitor.mcp.zipkin.enabled", havingValue = "true")
class TracingReportToZipkinConfig {

    @Bean
    Sender sender(@Value("${monitor.mcp.zipkin.url}") String zipkinSenderUrl) {
        return OkHttpSender.create(zipkinSenderUrl);
    }

    @Bean
    Reporter<Span> spanReporter(Sender sender) {
        return AsyncReporter.create(sender);
    }
}