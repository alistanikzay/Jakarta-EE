package org.example.bookapi.Application;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/api")
public class BookApplication extends Application {
    // Tom klass, men behövs för att aktivera JAX-RS med base path /api
}
