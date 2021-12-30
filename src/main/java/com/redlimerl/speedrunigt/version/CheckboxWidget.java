package com.redlimerl.speedrunigt.version;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.util.Identifier;
import org.lwjgl.opengl.GL11;

/**
 * @author Void_X_Walker
 * @reason Added a checkbox
 */
@Environment(EnvType.CLIENT)
public class CheckboxWidget extends ButtonWidget {
    private static final Identifier TEXTURE = new Identifier("textures/gui/checkbox.png");
    private boolean checked;
    private final boolean field_24253;

    public CheckboxWidget(int id, int x, int y, int width, int height, String text, boolean checked) {
        this(id,x, y, width, height, text, checked, true);

    }

    public CheckboxWidget(int id,int i, int j, int k, int l, String text, boolean bl, boolean bl2) {
        super(id,i, j, k, l, text);
        this.checked = bl;
        this.field_24253 = bl2;
    }

    public void onPress() {
        this.checked = !this.checked;
    }

    public void mouseReleased(int mouseX, int mouseY) {
        onPress();
    }
    public boolean isChecked() {
        return this.checked;
    }

    public void render( MinecraftClient client,int mouseX, int mouseY) {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        minecraftClient.getTextureManager().bindTexture(TEXTURE);
        TextRenderer textRenderer = minecraftClient.textRenderer;
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glBlendFunc(770, 771);
        drawTexture(this.x, this.y, this.hovered ? 20.0F : 0.0F, this.checked ? 20.0F : 0.0F, 20, this.height, 64, 64);
        this.renderBg(minecraftClient, mouseX, mouseY);
        if (this.field_24253) {
            drawCenteredString(textRenderer, this.message, this.x + 24, this.y + (this.height - 8) / 2, 14737632 | 255 << 24);
        }

    }
}
