package tools.redstone.redstonetools.macros.gui.screen;

import tools.redstone.redstonetools.macros.gui.MaroCommandSuggestor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;


public class CommandEditScreen extends GameOptionsScreen {

    private final TextFieldWidget commandField;
    private final MaroCommandSuggestor commandMaroCommandSuggestor;
    private boolean changed = false;

    public CommandEditScreen(Screen parent, GameOptions gameOptions, TextFieldWidget commandField) {
        super(parent, gameOptions, Text.of(""));
        this.commandField = commandField;
        client = MinecraftClient.getInstance();
        this.commandMaroCommandSuggestor = new MaroCommandSuggestor(client, parent, commandField,client.textRenderer,true,false, commandField.y -20,5,-805306368);

        commandField.setChangedListener((s) -> changed = true);
        commandMaroCommandSuggestor.setWindowActive(true);
        commandMaroCommandSuggestor.refresh();
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        parent.render(matrices, mouseX, mouseY, delta);

        this.fillGradient(matrices, 0, 0, this.width, this.height, -1072689136, -804253680);

        commandField.render(matrices, mouseX, mouseY, delta);

        commandMaroCommandSuggestor.render(matrices, mouseX, mouseY);
        if (changed) {
            commandMaroCommandSuggestor.refresh();
            changed = false;
        }

        super.render(matrices, mouseX, mouseY, delta);

    }

    @Override
    public void tick() {
        super.tick();
        commandField.tick();
    }

    @Override
    public void resize(MinecraftClient client, int width, int height) {
        parent.resize(client,width,height);
    }

    @Override
    public void close() {
        super.close();
        commandField.setTextFieldFocused(false);
        commandField.setChangedListener(null);
        commandMaroCommandSuggestor.setWindowActive(false);
        commandMaroCommandSuggestor.refresh();
        commandMaroCommandSuggestor.close();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (!commandField.mouseClicked(mouseX, mouseY, button)) {
            if (!commandMaroCommandSuggestor.mouseClicked(mouseX, mouseY, button)) {
                close();
            } else {
                commandField.setTextFieldFocused(true);
            }
            return false;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        return commandMaroCommandSuggestor.mouseScrolled(amount);
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        return commandField.charTyped(chr,modifiers);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == InputUtil.GLFW_KEY_ESCAPE || keyCode == InputUtil.GLFW_KEY_ENTER || keyCode == InputUtil.GLFW_KEY_KP_ENTER) {
            close();
            return true;
        }
        commandMaroCommandSuggestor.keyPressed(keyCode, scanCode, modifiers);

        return commandField.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        return commandField.keyReleased(keyCode, scanCode, modifiers);
    }
}
