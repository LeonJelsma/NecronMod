package com.calenaur.necron.gui;

import com.calenaur.necron.NecronMod;
import com.calenaur.necron.inventory.container.ContainerGooMaker;
import com.calenaur.necron.inventory.container.ContainerMoteProcessor;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class ScreenGooMaker extends ContainerScreen<ContainerGooMaker> {

    private ResourceLocation GUI = new ResourceLocation(NecronMod.MOD_ID, "textures/gui/goo_maker_gui.png");

    public ScreenGooMaker(Container container, PlayerInventory inventory, ITextComponent textComponent) {
        super((ContainerGooMaker) container, inventory, textComponent);
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks){
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        drawString(Minecraft.getInstance().fontRenderer, "Goo maker", 44 ,4, 0xffffff);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(GUI);
        int i = this.guiLeft;
        int j = this.guiTop;
        this.blit(i, j, 0, 0, this.xSize, this.ySize);


        }

}
