package xyz.artsna.toolswap.core.message.data.entities;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Message {

    private final String key;
    private final String[] values;
    private final Map<String, String> replaces = new HashMap<>();

    public Message(String key, String... values) {
        this.key = key;
        this.values = values;
    }

    public Message(String key, @NotNull List<String> values) {
        this.key = key;
        this.values = values.toArray(new String[0]);
    }

    public String getKey() {
        return key;
    }

    public String[] getValues() {
        return Arrays.stream(values).map(v -> replaces.entrySet().stream().reduce(v, (acc, entry) -> acc.replaceAll(entry.getKey(), entry.getValue()), (a, b) -> b)).toArray(String[]::new);
    }

    public String getFirst() {
        return values[0];
    }

    public Message replace(String key, String value) {
        replaces.put(key, value);
        return this;
    }

    @Override
    public String toString() {
        return key + " (" + String.join(", ", values) + ")";
    }

}