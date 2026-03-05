package dev.amineis.gameadmin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class ScalarConfig {

  @Bean
  public RouterFunction<ServerResponse> scalarRouter() {
    return RouterFunctions.route()
        .GET(
            "/scalar.html",
            request ->
                ServerResponse.ok()
                    .headers(headers -> headers.set("Content-Type", "text/html"))
                    .body(scalarHtml()))
        .build();
  }

  private String scalarHtml() {
    return """
            <!DOCTYPE html>
            <html>
            <head>
                <title>Game Admin API - Scalar</title>
                <meta charset="utf-8" />
                <meta name="viewport" content="width=device-width, initial-scale=1" />
                <style>
                    body {
                        margin: 0;
                        padding: 0;
                    }
                </style>
            </head>
            <body>
                <script
                    id="api-reference"
                    data-url="/v3/api-docs"
                    data-configuration='{
                        "theme": "purple",
                        "layout": "modern",
                        "showSidebar": true,
                        "hideModels": false,
                        "hideDownloadButton": false,
                        "darkMode": true,
                        "searchHotKey": "k"
                    }'>
                </script>
                <script src="https://cdn.jsdelivr.net/npm/@scalar/api-reference"></script>
            </body>
            </html>
            """;
  }
}
