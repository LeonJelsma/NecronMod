package com.calenaur.necron.util;


import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.IProperty;
import net.minecraft.state.Property;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class StringProperty implements IProperty<String> {

    private String name;

    public StringProperty(String name, String value) {

        this.name = name;
    }

    public static com.calenaur.necron.util.StringProperty create(String name, String value) {
        return new com.calenaur.necron.util.StringProperty(name, value);
    }

    public boolean equals(Object object) {
        return this == object;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Collection<String> getAllowedValues() {
        Collection<String> list = new ArrayList<String>();
        list.add("all");
        list.add("all");
        return list;
    }

    @Override
    public Class<String> getValueClass() {
        return String.class;
    }

    @Override
    public Optional<String> parseValue(String s) {
        return Optional.of(s);
    }

    @Override
    public String getName(String name) {
        return name;
    }
}