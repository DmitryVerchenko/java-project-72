package io.github.dmv04.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@Getter
@Setter
@ToString
public class Url {
    private long id;

    @ToString.Include
    private String name;

    private Instant createdAt;

    public Url(String name) {
        this.name = name;
    }
}
