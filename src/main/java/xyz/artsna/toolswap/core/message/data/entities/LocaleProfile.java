package xyz.artsna.toolswap.core.message.data.entities;

import xyz.artsna.toolswap.core.message.data.constraints.Locale;

import java.util.UUID;

public class LocaleProfile {

    private final UUID id;
    private Locale locale;

    public LocaleProfile(UUID id, Locale locale) {
        this.id = id;
        this.locale = locale;
    }

    public UUID getId() {
        return id;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

}
