package molteneq.test.mod.items;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.List;

public class TestItem extends Item {

    public TestItem(Properties properties) {
        super(properties);
    }

    //Extra Tool tip hozzáadása, fordítható
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(stack,level,list,flag);
        list.add(new TranslatableComponent("message.test_item", Integer.toString(10)));
    }
}
