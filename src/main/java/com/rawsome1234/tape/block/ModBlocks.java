package com.rawsome1234.tape.block;

import com.rawsome1234.tape.Tape;
import com.rawsome1234.tape.block.custom.*;
import com.rawsome1234.tape.item.ModItemGroup;
import com.rawsome1234.tape.item.ModItems;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS
            = DeferredRegister.create(ForgeRegistries.BLOCKS, Tape.MOD_ID);

    public static final RegistryObject<Block> TAPE_BLOCK = registerBlock("tape_block",
            () -> new Block(AbstractBlock.Properties.of(Material.WEB).harvestLevel(1)
                    .harvestTool(ToolType.HOE).requiresCorrectToolForDrops().strength(3,3)));

    public static final RegistryObject<Block> LAUNCHPAD = registerBlock("launchpad",
            () -> new Launchpad(AbstractBlock.Properties.of(Material.METAL).harvestLevel(1)
                    .harvestTool(ToolType.PICKAXE).sound(SoundType.ANVIL)
                    .strength(5,6).requiresCorrectToolForDrops().dynamicShape()));

//    public static final RegistryObject<Block> KIDNAPPING_SACK = BLOCKS.register("kidnapping_sack",
//            () -> new KidnappingSack(AbstractBlock.Properties.of(Material.WOOL).harvestLevel(0)
//                    .harvestTool(ToolType.AXE).sound(SoundType.WOOL).
//                    strength(.5f,1f).dynamicShape()));
//
//    public static final RegistryObject<BlockItem> SACK = ModItems.ITEMS.register("kidnapping_sack",
//            () -> new Sack(KIDNAPPING_SACK.get().getBlock(),
//                    new Item.Properties().tab(ModItemGroup.TAB_TAPE).stacksTo(1)));

    public static final RegistryObject<Block> COFFEE_MAKER = registerBlock("coffee_maker",
            () -> new CoffeeMakerBlock(AbstractBlock.Properties.of(Material.METAL).harvestLevel(1)
                .harvestTool(ToolType.PICKAXE).sound(SoundType.ANVIL)
                    .strength(5, 6).requiresCorrectToolForDrops().dynamicShape()));

    public static final RegistryObject<Block> COFFEE_CROP = BLOCKS.register("coffee_crop",
            () -> new CoffeeCrop(AbstractBlock.Properties.of(Material.PLANT).noCollission()));

    public static final RegistryObject<Block> MOLZEN_CROP = BLOCKS.register("molzen_crop",
            () -> new MolzenCrop(AbstractBlock.Properties.of(Material.PLANT).noCollission()));

    public static final RegistryObject<Block> FROLEN_CROP = BLOCKS.register("frolen_crop",
            () -> new FrolenCrop(AbstractBlock.Properties.of(Material.PLANT).noCollission()));




    private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block){
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block){
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().tab(ModItemGroup.TAB_TAPE)));
    }

    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }

}
