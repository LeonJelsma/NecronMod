package com.calenaur.necron.util;


import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.Property;

import java.util.Collection;
import java.util.Optional;

public class StringProperty extends Property<String> {

    protected StringProperty(String name, String value) {
        super(name, String.class);
    }

    public static com.calenaur.necron.util.StringProperty create(String name, String value) {
        return new com.calenaur.necron.util.StringProperty(name, value);
    }

    public boolean equals(Object object) {
        return this == object;
    }

    @Override
    public Collection<String> getAllowedValues() {
        return null;
    }

    @Override
    public Optional<String> parseValue(String value) {
        return Optional.empty();
    }

    @Override
    public String getName(String value) {
        return null;
    }
}