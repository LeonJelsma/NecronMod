package com.calenaur.necron.multiblock;

import com.calenaur.necron.NecronMod;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class ScreenPlasmaGenerator extends ContainerScreen<ContainerPlasmaGenerator> {

    public static final int WIDTH = 180;
    public static final int HEIGHT = 152;

    private static final ResourceLocation GUI = new ResourceLocation(NecronMod.MOD_ID, "textures/gui/plasma_generator.png");

    public ScreenPlasmaGenerator(Container container, PlayerInventory inventory, ITextComponent textComponent) {
        super((ContainerPlasmaGenerator)container, inventory, textComponent);

        xSize = WIDTH;
        ySize = HEIGHT;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.minecraft.getTextureManager().bindTexture(GUI);
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        super.render(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
    }

}
