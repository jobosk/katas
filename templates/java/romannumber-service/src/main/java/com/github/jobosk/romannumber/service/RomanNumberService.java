package com.github.jobosk.romannumber.service;

import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class RomanNumberService {

    public String getRoman(final int arabicNumber) {
        final AtomicInteger current = new AtomicInteger(arabicNumber);
        final StringBuilder builder = new StringBuilder();
        builder.append(applyRomanForward(current, "M", 1000));
        applyRomanBackwards(current, "CM", 900)
                .ifPresent(builder::append);
        builder.append(applyRomanForward(current, "D", 500));
        applyRomanBackwards(current, "CD", 400)
                .ifPresent(builder::append);
        builder.append(applyRomanForward(current, "C", 100));
        applyRomanBackwards(current, "XC", 90)
                .ifPresent(builder::append);
        builder.append(applyRomanForward(current, "L", 50));
        applyRomanBackwards(current, "XL", 40)
                .ifPresent(builder::append);
        builder.append(applyRomanForward(current, "X", 10));
        applyRomanBackwards(current, "IX", 9)
                .ifPresent(builder::append);
        builder.append(applyRomanForward(current, "V", 5));
        applyRomanBackwards(current, "IV", 4)
                .ifPresent(builder::append);
        builder.append(applyRomanForward(current, "I", 1));
        return builder.toString();
    }

    private String applyRomanForward(final AtomicInteger rest, final String character, final int arabicNumber) {
        final int times = rest.get() / arabicNumber;
        rest.getAndAdd(-times * arabicNumber);
        return String.valueOf(character).repeat(Math.max(0, times));
    }

    private Optional<String> applyRomanBackwards(final AtomicInteger rest, final String character, final int arabicNumber) {
        if (rest.get() / arabicNumber > 0) {
            rest.getAndAdd(-arabicNumber);
            return Optional.of(character);
        }
        return Optional.empty();
    }
}
