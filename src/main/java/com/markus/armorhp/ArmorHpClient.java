package com.markus.armorhp;

import com.markus.armorhp.mixin.AbstractContainerScreenAccessor;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class ArmorHpClient implements ClientModInitializer {
	private static final EquipmentSlot[] SLOTS = {
		EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET
	};

	private static final int ROW_HEIGHT = 20;
	private static final int PADDING = 6;
	private static final int GAP_TO_INVENTORY = 6;

	@Override
	public void onInitializeClient() {
		ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
			if (screen instanceof InventoryScreen) {
				ScreenEvents.afterExtract(screen).register(ArmorHpClient::drawPanel);
			}
		});
	}

	private static void drawPanel(Screen screen, GuiGraphicsExtractor graphics, int mouseX, int mouseY, float tickProgress) {
		Minecraft mc = Minecraft.getInstance();
		Player player = mc.player;
		if (player == null) {
			return;
		}

		Font font = mc.font;
		AbstractContainerScreenAccessor inv = (AbstractContainerScreenAccessor) screen;

		// Texte vorbereiten, um die Panel-Breite zu berechnen
		String title = "Rüstung";
		String[] texts = new String[SLOTS.length];
		int[] colors = new int[SLOTS.length];
		int textWidth = font.width(title);

		for (int i = 0; i < SLOTS.length; i++) {
			ItemStack stack = player.getItemBySlot(SLOTS[i]);
			if (stack.isEmpty()) {
				texts[i] = "–";
				colors[i] = 0xFF808080;
			} else if (!stack.isDamageableItem()) {
				texts[i] = "unzerstörbar";
				colors[i] = 0xFFB0B0B0;
			} else {
				int max = stack.getMaxDamage();
				int left = max - stack.getDamageValue();
				texts[i] = left + "/" + max;
				colors[i] = colorFor((float) left / (float) max);
			}
			textWidth = Math.max(textWidth, font.width(texts[i]) + 20);
		}

		int panelWidth = PADDING * 2 + textWidth;
		int panelHeight = PADDING * 2 + 12 + SLOTS.length * ROW_HEIGHT;

		// Rechts neben dem Inventar; wenn kein Platz ist, links davon
		int x = inv.armorhp$getLeftPos() + inv.armorhp$getImageWidth() + GAP_TO_INVENTORY;
		if (x + panelWidth > screen.width) {
			x = inv.armorhp$getLeftPos() - GAP_TO_INVENTORY - panelWidth;
		}
		int y = inv.armorhp$getTopPos();

		// Hintergrund und Rahmen
		graphics.fill(x, y, x + panelWidth, y + panelHeight, 0xD0101010);
		graphics.outline(x, y, panelWidth, panelHeight, 0xFF555555);

		graphics.text(font, title, x + PADDING, y + PADDING, 0xFFFFFFFF, false);

		for (int i = 0; i < SLOTS.length; i++) {
			int rowY = y + PADDING + 12 + i * ROW_HEIGHT;
			ItemStack stack = player.getItemBySlot(SLOTS[i]);
			if (!stack.isEmpty()) {
				graphics.item(stack, x + PADDING, rowY);
			}
			graphics.text(font, texts[i], x + PADDING + 20, rowY + 4, colors[i], false);
		}
	}

	/** Grün bei viel Haltbarkeit, gelb bei mittlerer, rot bei wenig. */
	private static int colorFor(float fraction) {
		if (fraction > 0.5f) {
			return 0xFF55FF55;
		} else if (fraction > 0.25f) {
			return 0xFFFFFF55;
		}
		return 0xFFFF5555;
	}
}
