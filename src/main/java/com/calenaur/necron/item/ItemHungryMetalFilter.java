package com.calenaur.necron.item;

import com.calenaur.necron.inventory.container.ContainerHungryMetalFilter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.LanguageMap;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ItemHungryMetalFilter extends Item implements INamedContainerProvider {
	public ItemHungryMetalFilter() {
		super(new Properties().maxStackSize(1).group(ItemGroup.NECRON));
		setRegistryName("hungry_metal_filter");
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		playerIn.openContainer(this);
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

	@Override
	public ITextComponent getDisplayName() {
		return new StringTextComponent(LanguageMap.getInstance().translateKey(getTranslationKey()));
	}

	@Nullable
	@Override
	public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity player) {
		ItemStack itemStack = null;
		if (player.getHeldItemMainhand().getItem() == this)
			itemStack = player.getHeldItemMainhand();

		if (player.getHeldItemOffhand().getItem() == this)
			itemStack = player.getHeldItemMainhand();

		if (itemStack == null)
			return null;

		return ContainerHungryMetalFilter.fromItemStack(id, playerInventory, itemStack);
	}
}
