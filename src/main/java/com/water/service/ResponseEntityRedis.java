package com.water.service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.io.Serial;
import java.io.Serializable;


public class ResponseEntityRedis extends ResponseEntity<Object> implements Serializable {
    public ResponseEntityRedis() {
        super(null, null);  // Chama o construtor da classe pai com valores nulos ou padrões
    }

    public ResponseEntityRedis(Object body, HttpStatusCode status) {
        super(body, status);
    }
}
