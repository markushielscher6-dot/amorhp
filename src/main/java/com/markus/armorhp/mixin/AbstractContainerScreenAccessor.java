package com.markus.armorhp.mixin;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/** Gibt Zugriff auf die Position des Inventar-Fensters. */
@Mixin(AbstractContainerScreen.class)
public interface AbstractContainerScreenAccessor {
	@Accessor("leftPos")
	int armorhp$getLeftPos();

	@Accessor("topPos")
	int armorhp$getTopPos();

	@Accessor("imageWidth")
	int armorhp$getImageWidth();
}
