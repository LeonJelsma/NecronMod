package com.calenaur.necron.gui;

import com.calenaur.necron.NecronMod;
import com.calenaur.necron.inventory.container.ContainerMoteProcessor;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class ScreenMoteProcessor extends ContainerScreen<ContainerMoteProcessor> {
    private final ResourceLocation GUI = new ResourceLocation(NecronMod.MOD_ID, "textures/gui/mote_processor.png");

    public ScreenMoteProcessor(Container container, PlayerInventory inventory, ITextComponent textComponent) {
        super((ContainerMoteProcessor) container, inventory, textComponent);
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks){
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        drawString(Minecraft.getInstance().fontRenderer, "Mote Processor", 44 ,4, 0xffffff);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        if (minecraft == null)
            return;

        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(GUI);
        int i = this.guiLeft;
        int j = this.guiTop;
        this.blit(i, j, 0, 0, this.xSize, this.ySize);

        int l = getProgressScaled(24, this.container.processorData.get(2), this.container.processorData.get(1));
        int k = getProgressScaled(13, 200, this.container.processorData.get(0));

        this.blit(80 + i, 34 + j, 176, 14, l, 17);
        this.blit(44 + i, 50 + j - k, 176, 13 - k, 19, k);
    }

    public int getProgressScaled(int width, int progress, int max_value) {
        return max_value != 0 && progress != 0 ? max_value * width / progress : 0;
    }
}
