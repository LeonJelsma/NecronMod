package com.calenaur.necron.item;

import com.calenaur.necron.Custom.HungryMetalGroupRegistry;
import com.calenaur.necron.block.Blocks;
import com.calenaur.necron.tileentity.TileEntityHungryMetal;
import com.calenaur.necron.util.StringProperty;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.state.IProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.LanguageMap;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;

public class ItemHungryMetal extends BlockItem {

    IProperty<String> targetBlock = StringProperty.create("targetBlockName", "minecraft:air");

    public static int NO_TARGET = -1;
    public static String TARGET = "hungry_metal_target";
    public static String TARGETS = "hungry_metal_targets";
    public static String RADIUS = "hungry_metal_radius";
    public static String SPEED = "hungry_metal_radius";

    public ItemHungryMetal() {
        super(Blocks.HUNGRY_METAL, new Properties().group(ItemGroup.NECRON).maxStackSize(1));
        setRegistryName("hungry_metal");
    }

    @Override
    public void onCreated(ItemStack itemStack, World world, PlayerEntity playerEntity) {
        super.onCreated(itemStack, world, playerEntity);
    }

    protected boolean onBlockPlaced(BlockPos pos, World worldIn, @Nullable PlayerEntity player, ItemStack stack, BlockState state) {
        if (!worldIn.isRemote()) {

            HashSet<Block> targets = new HashSet<>();
            targets.add(net.minecraft.block.Blocks.DIRT);

            HungryMetalGroupRegistry.addGroup(
                    pos,
                    targets,
                    //getRadius(stack),
                    30,
                    getSpeed(stack)
            );

            //TileEntityHungryMetal tileEntityHungryMetal = (TileEntityHungryMetal) worldIn.getTileEntity(pos);
            //tileEntityHungryMetal.setTargetBlock(getTarget(stack));
            //tileEntityHungryMetal.setMaxDistance(getRadius(stack));
            //tileEntityHungryMetal.setDelay(getSpeed(stack));
            //tileEntityHungryMetal.setStartingPos(pos);
            //tileEntityHungryMetal.activate();


        }
        return setTileEntityNBT(worldIn, player, pos, stack);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> list, ITooltipFlag flag) {
        if(isTargetSet(stack)) {

                list.add(new StringTextComponent("Target: " + LanguageMap.getInstance()
                        .translateKey(Block.getStateById(Integer.parseInt(stack.getTag()
                                .get(TARGET).getString()))
                                .getBlock()
                                .getTranslationKey()
                        )));
                list.add(new StringTextComponent("Radius: " + Integer.parseInt(stack.getTag().get(RADIUS).getString())));
            } else {
                list.add(new StringTextComponent("Target: Unconfigured"));
            }
        super.addInformation(stack, world, list, flag);
    }

    public static boolean isTargetSet(ItemStack stack){
        if(stack.getTag() != null) {
            if (stack.getTag().get(TARGET) != null) {
                if (Integer.parseInt(stack.getTag().get(TARGET).getString()) != NO_TARGET) {
                    return true;
                }
            }
        }
        return false;
    }

    public static ItemStack configure(ItemStack itemStack, BlockState target, int radius, int speed){
        CompoundNBT configuration;
        if (itemStack.getTag() != null){
            configuration = itemStack.getTag();
        } else {
            configuration = new CompoundNBT();
        }
        configuration.put(TARGET, IntNBT.func_229692_a_(Block.getStateId(target)));
        configuration.put(RADIUS, IntNBT.func_229692_a_(radius));
        configuration.put(SPEED, IntNBT.func_229692_a_(speed));
        itemStack.setTag(configuration);
        return  itemStack;
    }


    private Block getTarget(ItemStack stack){
        return Block.getStateById(Integer.parseInt(stack.getTag().get(TARGET).getString())).getBlock();
    }

    private int getRadius(ItemStack stack){
        return Integer.parseInt(stack.getTag().get(RADIUS).getString());
    }

    private int getSpeed(ItemStack stack){
        return Integer.parseInt(stack.getTag().get(SPEED).getString());
    }
}
