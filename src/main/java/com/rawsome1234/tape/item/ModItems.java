package com.rawsome1234.tape.item;

import com.rawsome1234.tape.Tape;
import com.rawsome1234.tape.block.ModBlocks;
import com.rawsome1234.tape.item.custom.FlexTape;
import com.rawsome1234.tape.item.custom.MaskingTape;
import com.rawsome1234.tape.item.custom.Sack;
import com.rawsome1234.tape.item.custom.SuggestionSlip;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Tape.MOD_ID);

    public static final RegistryObject<Item> MASKING_TAPE = ITEMS.register("masking_tape",
            () -> new MaskingTape(new Item.Properties().stacksTo(64).tab(ModItemGroup.TAB_TAPE)
                    .food(new Food.Builder().fast().alwaysEat()
                            .effect(() -> new EffectInstance(Effects.CONFUSION, 100, 0), 1f).nutrition(1).saturationMod(.1f).build())));

    public static final RegistryObject<Item> FLEX_TAPE = ITEMS.register("flex_tape",
            () -> new FlexTape(new Item.Properties().stacksTo(64).tab(ModItemGroup.TAB_TAPE)
                    .food(new Food.Builder().alwaysEat()
                        .effect(() -> new EffectInstance(Effects.DAMAGE_BOOST, 100, 0), .5f).nutrition(2).saturationMod(.2f).build())));

    public static final RegistryObject<Item> TAPE_SWORD = ITEMS.register("tape_sword",
            () -> new SwordItem(ModItemTier.TAPE, 3, -1f,
                    new Item.Properties().tab(ModItemGroup.TAB_TAPE)));

    public static final RegistryObject<Item> TAPE_PICKAXE = ITEMS.register("tape_pickaxe",
            () -> new PickaxeItem(ModItemTier.TAPE, 1, -2.8f,
                    new Item.Properties().tab(ModItemGroup.TAB_TAPE)));

    public static final RegistryObject<Item> TAPE_AXE = ITEMS.register("tape_axe",
            () -> new AxeItem(ModItemTier.TAPE, 5f, -2.f,
                    new Item.Properties().tab(ModItemGroup.TAB_TAPE)));

    public static final RegistryObject<Item> TAPE_SHOVEL = ITEMS.register("tape_shovel",
            () -> new ShovelItem(ModItemTier.TAPE, 1.5f, -3f,
                    new Item.Properties().tab(ModItemGroup.TAB_TAPE)));

    public static final RegistryObject<Item> TAPE_HOE = ITEMS.register("tape_hoe",
            () -> new HoeItem(ModItemTier.TAPE, 0, 1f,
                    new Item.Properties().tab(ModItemGroup.TAB_TAPE)));

    public static final RegistryObject<Item> SUGGESTION_SLIP = ITEMS.register("suggestion_slip",
            () -> new SuggestionSlip(new Item.Properties().tab(ModItemGroup.TAB_TAPE)));

    public static final RegistryObject<Item> SACK = ITEMS.register("kidnapping_sack",
            () -> new Sack(new Item.Properties().tab(ModItemGroup.TAB_TAPE).stacksTo(1)));

    public static final RegistryObject<Item> MUG = ITEMS.register("mug",
            () -> new Item(new Item.Properties().tab(ModItemGroup.TAB_TAPE)));

    public static final RegistryObject<Item> COFFEE_BEANS = ITEMS.register("coffee_beans",
            () -> new BlockItem(ModBlocks.COFFEE_CROP.get(),
                    new Item.Properties().tab(ModItemGroup.TAB_TAPE).rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> MOLZEN_BEANS = ITEMS.register("molzen_beans",
            () -> new BlockItem(ModBlocks.MOLZEN_CROP.get(),
                    new Item.Properties().tab(ModItemGroup.TAB_TAPE).rarity(Rarity.RARE)));

    public static final RegistryObject<Item> FROLEN_BEANS = ITEMS.register("frolen_beans",
            () -> new BlockItem(ModBlocks.FROLEN_CROP.get(),
                    new Item.Properties().tab(ModItemGroup.TAB_TAPE).rarity(Rarity.RARE)));

    public static final RegistryObject<Item> COFFEE_ITEM = ITEMS.register("coffee_item",
            () -> new Item(new Item.Properties().tab(ModItemGroup.TAB_TAPE)
                    .food(new Food.Builder().nutrition(4).saturationMod(.375f)
                            .build())));

    public static final RegistryObject<Item> LATTE = ITEMS.register("latte",
            () -> new Item(new Item.Properties().tab(ModItemGroup.TAB_TAPE)
                    .food(new Food.Builder().nutrition(7).saturationMod(1f)
                            .build())));

    public static final RegistryObject<Item> ESPRESSO = ITEMS.register("espresso",
            () -> new Item(new Item.Properties().tab(ModItemGroup.TAB_TAPE)
                    .food(new Food.Builder().nutrition(5).saturationMod(.625f)
                            .effect(() -> new EffectInstance(Effects.MOVEMENT_SPEED, 100, 0), 1f)
                            .build())));

    public static final RegistryObject<Item> GARBAGE = ITEMS.register("garbage",
            () -> new Item(new Item.Properties().tab(ModItemGroup.TAB_TAPE)
                    .food(new Food.Builder().nutrition(3).saturationMod(.5f)
                            .effect(() -> new EffectInstance(Effects.CONFUSION, 100, 0), 1f)
                            .build())));

    public static final RegistryObject<Item> CHILLY_SWEET_COFFEE = ITEMS.register("chilly_sweet_coffee",
            () -> new Item(new Item.Properties().tab(ModItemGroup.TAB_TAPE)
                    .food(new Food.Builder().nutrition(4).saturationMod(.5f)
                            .effect(() -> new EffectInstance(Effects.REGENERATION, 200, 0), 1f)
                            .build())));

    public static final RegistryObject<Item> COOKIE_AND_CREAM_COFFEE = ITEMS.register("cookie_and_cream_coffee",
            () -> new Item(new Item.Properties().tab(ModItemGroup.TAB_TAPE)
                    .food(new Food.Builder().nutrition(7).saturationMod(1f)
                            .effect(() -> new EffectInstance(Effects.DAMAGE_RESISTANCE, 200, 0), 1f)
                            .build())));

    public static final RegistryObject<Item> FROSTY_BREW = ITEMS.register("frosty_brew",
            () -> new Item(new Item.Properties().tab(ModItemGroup.TAB_TAPE)
                    .food(new Food.Builder().nutrition(10).saturationMod(2f)
                            .effect(() -> new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 100, 0), 1f)
                            .effect(() -> new EffectInstance(Effects.DAMAGE_BOOST, 200, 0), 1f)
                            .effect(() -> new EffectInstance(Effects.DAMAGE_RESISTANCE, 200, 0), 1f)
                            .build())));

    public static final RegistryObject<Item> SUMMER_BREW = ITEMS.register("summer_brew",
            () -> new Item(new Item.Properties().tab(ModItemGroup.TAB_TAPE)
                    .food(new Food.Builder().nutrition(4).saturationMod(.5f)
                            .effect(() -> new EffectInstance(Effects.REGENERATION, 200, 0), 1f)
                            .build())));

    public static final RegistryObject<Item> POWER_COFFEE = ITEMS.register("power_coffee",
            () -> new Item(new Item.Properties().tab(ModItemGroup.TAB_TAPE)
                    .food(new Food.Builder().nutrition(7).saturationMod(1f)
                            .effect(() -> new EffectInstance(Effects.DAMAGE_BOOST, 200, 0), 1f)
                            .build())));

    public static final RegistryObject<Item> HEART_ATTACK = ITEMS.register("heart_attack",
            () -> new Item(new Item.Properties().tab(ModItemGroup.TAB_TAPE)
                    .food(new Food.Builder().nutrition(10).saturationMod(2f)
                            .effect(() -> new EffectInstance(Effects.CONFUSION, 100, 0), 1f)
                            .effect(() -> new EffectInstance(Effects.MOVEMENT_SPEED, 200, 0), 1f)
                            .effect(() -> new EffectInstance(Effects.DAMAGE_BOOST, 200, 0), 1f)
                            .build())));


    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }




}
