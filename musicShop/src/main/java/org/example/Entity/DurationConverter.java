package org.example.Entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.time.Duration;
import java.time.format.DateTimeParseException;

@Converter(autoApply = true)
public class DurationConverter implements AttributeConverter<Duration, String> {

    @Override
    public String convertToDatabaseColumn(Duration duration) {
        if (duration == null) {
            return null;
        }
        long seconds = duration.getSeconds();
        return String.format("%02d:%02d:%02d",
                seconds / 3600,
                (seconds % 3600) / 60,
                seconds % 60);
    }

    @Override
    public Duration convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }

        try {
            String[] parts = dbData.split(":");
            if (parts.length != 3) {
                throw new IllegalArgumentException("Invalid duration format");
            }

            long hours = Long.parseLong(parts[0]);
            long minutes = Long.parseLong(parts[1]);
            long seconds = Long.parseLong(parts[2]);

            return Duration.ofSeconds(hours * 3600 + minutes * 60 + seconds);
        } catch (Exception e) {
            throw new IllegalArgumentException("Could not parse duration: " + dbData, e);
        }
    }
}