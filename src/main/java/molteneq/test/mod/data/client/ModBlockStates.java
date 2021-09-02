package molteneq.test.mod.data.client;

import io.netty.handler.codec.http2.Http2FrameLogger;
import molteneq.test.mod.ExampleMod;
import molteneq.test.mod.registration.ModBlockRegistry;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.common.Mod;

import java.util.function.Function;

public class ModBlockStates extends BlockStateProvider {

    public ModBlockStates(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, ExampleMod.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        // egyszerű blokk esetén
        // simpleBlock(ModBlockRegistry.GEN_BLOCK.get());

    }

    private void regGeneneratorBlock(){
        ResourceLocation side = new ResourceLocation(ExampleMod.MODID, "block/gen_block_side");
        ResourceLocation front = new ResourceLocation(ExampleMod.MODID, "block/gen_block_front");
        ResourceLocation front_powered = new ResourceLocation(ExampleMod.MODID, "block/gen_block_front_powered");

        // a blokknak 6 oldala van
        BlockModelBuilder modelGenerator = models().cube("generator",
                side,
                side,
                front, //északi
                side,
                side,
                side);
        BlockModelBuilder modelGeneratorPowered = models().cube("generator_powered",
                side,
                side,
                front_powered,
                side,
                side,
                side);
        orientedBlock(ModBlockRegistry.GEN_BLOCK.get(), state -> {
            if (state.getValue(BlockStateProperties.POWERED))
                return modelGeneratorPowered;
            else return modelGenerator;
        });
    }

    // ó, egy jó kis lambdás kifejezés :D (+ egy methódus, ami blokkálopt alapján meghatározza a modellt)
    private void orientedBlock(Block block, Function<BlockState, ModelFile> modelFunc){
        getVariantBuilder(block)
                .forAllStates(blockState -> {
                    Direction direction = blockState.getValue(BlockStateProperties.FACING);
                    // Mi a... ezt át kell majd nagyon nézni :O
                    return ConfiguredModel.builder()
                            .modelFile(modelFunc.apply(blockState))
                            .rotationX(direction.getAxis() == Direction.Axis.Y ? direction.getAxisDirection().getStep() * -90 : 0)
                            .rotationY(direction.getAxis() != Direction.Axis.Y ? ((direction.get2DDataValue() + 2) % 4) * 90 : 0)
                            .build();
                });
    }
}
