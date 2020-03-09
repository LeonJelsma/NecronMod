package com.calenaur.necron.item;

import com.calenaur.necron.block.BlockGreyGoo;
import com.calenaur.necron.block.Blocks;
import com.calenaur.necron.tileentity.TileEntityGreyGoo;
import com.calenaur.necron.util.StringProperty;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.state.IProperty;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.io.DataOutput;

public class ItemGreyGoo extends BlockItem {

    IProperty<String> targetBlock = StringProperty.create("targetBlockName", "minecraft:air");

    public ItemGreyGoo() {
        super(Blocks.GREY_GOO, new Properties().group(ItemGroup.NECRON));
        setRegistryName("grey_goo");
    }

    @Override
    public void onCreated(ItemStack itemStack, World world, PlayerEntity playerEntity) {
        super.onCreated(itemStack, world, playerEntity);
        CompoundNBT compoundnbt = new CompoundNBT();
        compoundnbt.put("target", StringNBT.func_229705_a_("testvalue"));
        itemStack.setTag(compoundnbt);
    }

    protected boolean onBlockPlaced(BlockPos pos, World worldIn, @Nullable PlayerEntity player, ItemStack stack, BlockState state) {
        if (!worldIn.isRemote()) {
            if (worldIn.getTileEntity(pos) instanceof TileEntityGreyGoo) {
                //BlockState blockState = worldIn.getBlockState(pos);
                TileEntityGreyGoo tileEntityGreyGoo = (TileEntityGreyGoo) worldIn.getTileEntity(pos);
                tileEntityGreyGoo.setTargetBlock(net.minecraft.block.Blocks.DIRT);
                tileEntityGreyGoo.setMaxDistance(50);
                tileEntityGreyGoo.setStartingPos(pos);
            }
        }
        return setTileEntityNBT(worldIn, player, pos, stack);
    }
}
