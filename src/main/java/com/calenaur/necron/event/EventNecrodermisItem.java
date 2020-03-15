package com.calenaur.necron.event;

import com.calenaur.necron.item.Items;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

import java.util.List;
import java.util.Set;

@Mod.EventBusSubscriber
public class EventNecrodermisItem {
	private static Set<ItemStack> NECRODERMIS_ITEMS;
	private static final int TICKS_PER_SECOND = 20;
	private static final int REGEN_INTERVAL_SECONDS = 10;
	private static final int REGEN_REPAIR_AMOUNT = 2;
	private static final int REGEN_INTERVAL_TICKS = TICKS_PER_SECOND * REGEN_INTERVAL_SECONDS;
	private int tick = 0;

	@SubscribeEvent
	public void regenerativeTick(TickEvent.PlayerTickEvent event) {
		if (event.phase != TickEvent.Phase.END)
			return;

		PlayerEntity player = event.player;
		World world = player.world;
		if (world.isRemote())
			return;

		tick++;
		if (tick < REGEN_INTERVAL_TICKS)
			return;

		tick = 0;
		final List<NonNullList<ItemStack>> allInventories = ImmutableList.of(player.inventory.mainInventory, player.inventory.armorInventory, player.inventory.offHandInventory);
		for (NonNullList<ItemStack> container : allInventories)
			for (ItemStack inventoryStack : container)
				if (!inventoryStack.isEmpty() && inventoryStack.getCount() == 1)
					if (player.getHeldItemMainhand() != inventoryStack && player.getHeldItemOffhand() != inventoryStack)
						for (ItemStack necronStack : NECRODERMIS_ITEMS)
							if (inventoryStack.isItemEqual(necronStack)) {
								inventoryStack.damageItem(-REGEN_REPAIR_AMOUNT, player, (playerEntity) -> playerEntity.sendBreakAnimation(playerEntity.getActiveHand()));
							}
	}

	@SubscribeEvent
	public void onServerStarting(FMLServerStartingEvent event) {
		NECRODERMIS_ITEMS = Sets.newHashSet(
				new ItemStack(Items.NECRON_AXE),
				new ItemStack(Items.NECRON_HOE),
				new ItemStack(Items.NECRON_PICKAXE),
				new ItemStack(Items.NECRON_SHOVEL)
		);
	}
}
