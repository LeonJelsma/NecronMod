package com.calenaur.necron.item;

import com.calenaur.necron.block.Blocks;
import com.calenaur.necron.tileentity.TileEntityHungryMetal;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.LanguageMap;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;

public class ItemHungryMetal extends BlockItem {
    public static final String TARGETS = "hungry_targets";
    public static final String RADIUS = "hungry_radius";
    public static final String SPEED = "speed_speed";

    public ItemHungryMetal() {
        super(Blocks.HUNGRY_METAL, new Properties().group(ItemGroup.NECRON).maxStackSize(1));
        setRegistryName("hungry_metal");
    }

    @Override
    public void onCreated(ItemStack itemStack, World world, PlayerEntity playerEntity) {
        super.onCreated(itemStack, world, playerEntity);
    }

    protected boolean onBlockPlaced(BlockPos pos, World worldIn, @Nullable PlayerEntity player, ItemStack stack, BlockState state) {
        if (!worldIn.isRemote())
            if (worldIn.getTileEntity(pos) instanceof TileEntityHungryMetal)
                if (isTargetSet(stack)) {
                    TileEntityHungryMetal tileEntityHungryMetal = (TileEntityHungryMetal) worldIn.getTileEntity(pos);
                    if (tileEntityHungryMetal == null)
                        return false;

                    tileEntityHungryMetal.setTargetBlocks(getTargets(stack));
                    tileEntityHungryMetal.setMaxDistance(getRadius(stack));
                    tileEntityHungryMetal.setDelay(getSpeed(stack));
                    tileEntityHungryMetal.setStartingPos(pos);
                    tileEntityHungryMetal.activate();
                }

        return setTileEntityNBT(worldIn, player, pos, stack);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> list, ITooltipFlag flag) {
        if (isTargetSet(stack)) {
            list.add(new StringTextComponent("Target: " + getTargetNames(stack)));
            list.add(new StringTextComponent("Radius: " + getRadius(stack)));
        } else
            list.add(new StringTextComponent("Target: Unconfigured"));

        super.addInformation(stack, world, list, flag);
    }

    public static boolean isTargetSet(ItemStack stack){
        if (stack.getTag() != null) {
            int[] items = stack.getTag().getIntArray(TARGETS);
            return items.length > 0;
        }

        return false;
    }

    public static ItemStack configure(ItemStack itemStack, HashSet<BlockState> targets, int radius, int speed){
        CompoundNBT configuration;
        if (itemStack.getTag() != null){
            configuration = itemStack.getTag();
        } else
            configuration = new CompoundNBT();

        configuration.putIntArray(TARGETS, TileEntityHungryMetal.getIntArrayFromTargetBlocks(targets));
        configuration.put(RADIUS, IntNBT.func_229692_a_(radius));
        configuration.put(SPEED, IntNBT.func_229692_a_(speed));
        itemStack.setTag(configuration);
        return  itemStack;
    }


    private HashSet<BlockState> getTargets(ItemStack stack){
        if (stack.getTag() == null)
            return new HashSet<>();

        return TileEntityHungryMetal.getTargetBlocksFromIntArray(stack.getTag().getIntArray(TARGETS));
    }

    private String getTargetNames(ItemStack stack){
        StringBuilder targets = new StringBuilder();
        if (isTargetSet(stack)){
            HashSet<BlockState> states = getTargets(stack);

            int i = states.size()-2;
            for (BlockState state: states){
                i++;
                targets.append(LanguageMap.getInstance().translateKey(state.getBlock().getTranslationKey()));
                if (i >0)
                    targets.append(", ");
            }
        }
        return targets.toString();
    }

    private int getRadius(ItemStack stack){
        if (stack.getTag() == null)
            return TileEntityHungryMetal.DEFAULT_RADIUS;

        INBT radius = stack.getTag().get(RADIUS);
        if (radius == null)
            return TileEntityHungryMetal.DEFAULT_RADIUS;

        return Integer.parseInt(radius.getString());
    }

    private int getSpeed(ItemStack stack){
        if (stack.getTag() == null)
            return TileEntityHungryMetal.DEFAULT_SPEED;

        INBT speed = stack.getTag().get(SPEED);
        if (speed == null)
            return TileEntityHungryMetal.DEFAULT_SPEED;

        return Integer.parseInt(speed.getString());
    }



}
