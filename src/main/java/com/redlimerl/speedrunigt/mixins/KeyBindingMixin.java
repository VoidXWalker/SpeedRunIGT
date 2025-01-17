package com.redlimerl.speedrunigt.mixins;

import com.redlimerl.speedrunigt.SpeedRunIGT;
import com.redlimerl.speedrunigt.timer.InGameTimer;
import com.redlimerl.speedrunigt.timer.RunCategory;
import com.redlimerl.speedrunigt.timer.TimerStatus;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.Objects;

@Mixin(KeyBinding.class)
public class KeyBindingMixin {

    @Shadow @Final private static Map<InputUtil.Key, KeyBinding> keyToBindings;

    @Inject(method = "setKeyPressed", at = @At("TAIL"))
    private static void onPress(InputUtil.Key key, boolean pressed, CallbackInfo ci) {
        InGameTimer timer = InGameTimer.getInstance();
        KeyBinding keyBinding = keyToBindings.get(key);
        if (keyBinding != null) {
            if (keyBinding == MinecraftClient.getInstance().options.keyAdvancements // Advancement
                    || keyBinding == MinecraftClient.getInstance().options.keySprint // Sprint
                    || Objects.equals(keyBinding.getCategory(), "key.categories.inventory")
                    || Objects.equals(keyBinding.getCategory(), "key.categories.gameplay")) {
                if ((timer.getStatus() == TimerStatus.IDLE || timer.getStatus() == TimerStatus.LEAVE) && InGameTimer.checkingWorld) {
                    timer.setPause(false);
                }
                timer.updateFirstInput();
            }
            if (keyBinding == SpeedRunIGT.timerResetKeyBinding && pressed) {
                if (timer.getCategory() == RunCategory.CUSTOM && timer.isResettable()) {
                    InGameTimer.reset();
                }
            }
            if (keyBinding == SpeedRunIGT.timerStopKeyBinding && pressed) {
                if (timer.getCategory() == RunCategory.CUSTOM && timer.isStarted()) {
                    InGameTimer.complete();
                }
            }
        }
    }
}
