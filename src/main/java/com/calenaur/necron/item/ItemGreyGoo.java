package com.calenaur.necron.item;

import com.calenaur.necron.block.Blocks;
import com.calenaur.necron.util.StringProperty;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IProperty;

public class ItemGreyGoo extends BlockItem {

    IProperty<String> targetBlock = StringProperty.create("targetBlockName", "minecraft:air");

    public ItemGreyGoo() {
        super(Blocks.GREY_GOO, new Properties().group(ItemGroup.NECRON));
        setRegistryName("grey_goo");
    }
}
