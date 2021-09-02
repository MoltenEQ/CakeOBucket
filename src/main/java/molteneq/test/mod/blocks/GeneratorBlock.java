package molteneq.test.mod.blocks;

import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Material;

import javax.annotation.Nullable;

public class GeneratorBlock extends Block {

    public GeneratorBlock() {
        super(Properties.of(Material.METAL) //Típus
                .sound(SoundType.METAL) //Hang
                .lightLevel(state -> state.getValue(BlockStateProperties.POWERED) ? 14 : 0) // ha el van látva árammal, akkor 14-es erősségű fényt bocsájt ki
                .strength(2.0f) //Mennyi idő csákánnyal bányászni
        );
    }

    // Blokkállapotok hozzáadása
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.FACING, BlockStateProperties.POWERED);
    }

    //Lerakásnál melyik blokkállapotban legyen
    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        // A játékos "nézésével szemben" legyen a blokk mutatója
        return defaultBlockState().setValue(BlockStateProperties.FACING, context.getNearestLookingDirection().getOpposite());
    }
}
