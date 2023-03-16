package team.teampotato.IFMODLOADWARN;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.util.List;
public class GUIS implements ModInitializer {


        public static class GUI2 extends Screen {
            public GUI2() {
                super(new LiteralText("Screen"));
            }

            @Override
            protected void init() {
                super.init();

                // 添加两个按钮
                int buttonWidth = 100;
                int buttonHeight = 20;
                int buttonSpacing = 10;
                int centerX = (this.width - buttonWidth * 2 - buttonSpacing) / 2;
                int centerY = (this.height - buttonHeight) / 2;
                this.addCustomButton(new ButtonWidget(centerX, centerY, buttonWidth, buttonHeight, new TranslatableText("ifmodloadwarn.yes"), button -> {
                    // 点击“接受”按钮的操作
                    this.client.setScreen(null); // 返回主界面
                }));
                this.addCustomButton(new ButtonWidget(centerX + buttonWidth + buttonSpacing, centerY, buttonWidth, buttonHeight, new TranslatableText("ifmodloadwarn.no"), button -> {
                    // 点击“取消”按钮的操作
                    MinecraftClient.getInstance().scheduleStop();
                }));



            }

            @Override
            public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
                this.renderBackground(matrices); // 渲染背景
                super.render(matrices, mouseX, mouseY, delta);

                // 绘制文本
                List<String> blacklistedMods = IFMODLOADWARN.modId();
                String blacklistedModsText = String.join(", ", blacklistedMods);
                Text text = new TranslatableText("ifmodloadwarn.title", blacklistedModsText);
                int textWidth = this.textRenderer.getWidth(text);
                int textX = (this.width - textWidth) / 2;
                int textY = this.height / 4;
                this.textRenderer.draw(matrices, text, textX, textY, 0xFFFFFF);
            }

            public void addCustomButton(ButtonWidget button) {
                this.addDrawableChild(button);
            }

        }
    @Override
    public void onInitialize() {
}
}