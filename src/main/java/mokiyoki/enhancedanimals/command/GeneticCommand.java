package mokiyoki.enhancedanimals.command;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.CompoundTagArgument;
import net.minecraft.commands.arguments.NbtPathArgument;
import net.minecraft.commands.arguments.NbtTagArgument;
import net.minecraft.nbt.*;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.commands.data.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class GeneticCommand {
    private static final SimpleCommandExceptionType ERROR_MERGE_UNCHANGED = new SimpleCommandExceptionType(new TranslatableComponent("commands.data.merge.failed"));
    private static final DynamicCommandExceptionType ERROR_GET_NOT_NUMBER = new DynamicCommandExceptionType((p_139491_) -> new TranslatableComponent("commands.data.get.invalid", new Object[]{p_139491_}));
    private static final DynamicCommandExceptionType ERROR_GET_NON_EXISTENT = new DynamicCommandExceptionType((p_139481_) -> new TranslatableComponent("commands.data.get.unknown", new Object[]{p_139481_}));
    private static final SimpleCommandExceptionType ERROR_MULTIPLE_TAGS = new SimpleCommandExceptionType(new TranslatableComponent("commands.data.get.multiple"));
    private static final DynamicCommandExceptionType ERROR_EXPECTED_LIST = new DynamicCommandExceptionType((p_139468_) -> new TranslatableComponent("commands.data.modify.expected_list", new Object[]{p_139468_}));
    private static final DynamicCommandExceptionType ERROR_EXPECTED_OBJECT = new DynamicCommandExceptionType((p_139448_) -> new TranslatableComponent("commands.data.modify.expected_object", new Object[]{p_139448_}));
    private static final DynamicCommandExceptionType ERROR_INVALID_INDEX = new DynamicCommandExceptionType((p_139402_) -> new TranslatableComponent("commands.data.modify.invalid_index", new Object[]{p_139402_}));

    public static final List<Function<String, DataCommands.DataProvider>> ALL_PROVIDERS;
    public static final List<DataCommands.DataProvider> TARGET_PROVIDERS;
    public static final List<DataCommands.DataProvider> SOURCE_PROVIDERS;

    static {
        ALL_PROVIDERS = ImmutableList.of(EntityDataAccessor.PROVIDER, BlockDataAccessor.PROVIDER, StorageDataAccessor.PROVIDER);
        TARGET_PROVIDERS = ALL_PROVIDERS.stream().map((p_139450_) -> p_139450_.apply("target")).collect(ImmutableList.toImmutableList());
        SOURCE_PROVIDERS = ALL_PROVIDERS.stream().map((p_139410_) -> p_139410_.apply("source")).collect(ImmutableList.toImmutableList());
    }
    
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher){

        LiteralArgumentBuilder<CommandSourceStack> command = (LiteralArgumentBuilder)Commands.literal("genetic").requires((p_139381_) -> p_139381_.hasPermission(2));

        for (DataCommands.DataProvider dataProvider : TARGET_PROVIDERS) {

            command.then(

                    // ── merge <nbt> ──────────────────────────────────────────────────────────
                    // Merges a raw compound tag into the target's NBT data
                    dataProvider.wrap(
                            Commands.literal("merge"),
                            (node) -> node.then(
                                    Commands.argument("nbt", CompoundTagArgument.compoundTag())
                                            .executes((ctx) ->
                                                    mergeData(
                                                            (CommandSourceStack) ctx.getSource(),
                                                            dataProvider.access(ctx),
                                                            CompoundTagArgument.getCompoundTag(ctx, "nbt")
                                                    )
                                            )
                            )
                    )

            ).then(

                    // ── get [path] [scale] ───────────────────────────────────────────────────
                    dataProvider.wrap(
                            Commands.literal("get"),
                            (node) -> node

                                    // get  →  returns full NBT of the target
                                    .executes((ctx) ->
                                            getData(
                                                    (CommandSourceStack) ctx.getSource(),
                                                    dataProvider.access(ctx)
                                            )
                                    )

                                    .then(
                                            Commands.argument("path", NbtPathArgument.nbtPath())

                                                    // get <path>  →  returns value at the given NBT path
                                                    .executes((ctx) ->
                                                            getData(
                                                                    (CommandSourceStack) ctx.getSource(),
                                                                    dataProvider.access(ctx),
                                                                    NbtPathArgument.getPath(ctx, "path")
                                                            )
                                                    )

                                                    .then(
                                                            Commands.argument("scale", DoubleArgumentType.doubleArg())

                                                                    // get <path> <scale>  →  returns numeric value multiplied by scale
                                                                    .executes((ctx) ->
                                                                            getNumeric(
                                                                                    (CommandSourceStack) ctx.getSource(),
                                                                                    dataProvider.access(ctx),
                                                                                    NbtPathArgument.getPath(ctx, "path"),
                                                                                    DoubleArgumentType.getDouble(ctx, "scale")
                                                                            )
                                                                    )
                                                    )
                                    )
                    )

            ).then(

                    // ── remove <path> ────────────────────────────────────────────────────────
                    // Deletes the tag at the given NBT path
                    dataProvider.wrap(
                            Commands.literal("remove"),
                            (node) -> node.then(
                                    Commands.argument("path", NbtPathArgument.nbtPath())
                                            .executes((ctx) ->
                                                    removeData(
                                                            (CommandSourceStack) ctx.getSource(),
                                                            dataProvider.access(ctx),
                                                            NbtPathArgument.getPath(ctx, "path")
                                                    )
                                            )
                            )
                    )

            ).then(

                    // ── modify <target-path> <operation> <source> ────────────────────────────
                    // decorateModification wraps all sub-commands with the shared
                    // "target path + data source" argument prefix.
                    decorateModification((targetNode, sourceNode) ->

                            targetNode

                                    // insert <index> <source>  →  insert into a list at the given index
                                    .then(
                                            Commands.literal("insert")
                                                    .then(
                                                            Commands.argument("index", IntegerArgumentType.integer())
                                                                    .then(
                                                                            sourceNode.create((ctx, compoundTag, nbtPath, sourceTags) -> {
                                                                                int index = IntegerArgumentType.getInteger(ctx, "index");
                                                                                return insertAtIndex(index, compoundTag, nbtPath, sourceTags);
                                                                            })
                                                                    )
                                                    )
                                    )

                                    // prepend <source>  →  insert at the front of a list (index 0)
                                    .then(
                                            Commands.literal("prepend")
                                                    .then(
                                                            sourceNode.create((ctx, compoundTag, nbtPath, sourceTags) ->
                                                                    insertAtIndex(0, compoundTag, nbtPath, sourceTags)
                                                            )
                                                    )
                                    )

                                    // append <source>  →  insert at the end of a list (index -1)
                                    .then(
                                            Commands.literal("append")
                                                    .then(
                                                            sourceNode.create((ctx, compoundTag, nbtPath, sourceTags) ->
                                                                    insertAtIndex(-1, compoundTag, nbtPath, sourceTags)
                                                            )
                                                    )
                                    )

                                    // set <source>  →  overwrite the tag at the target path with the source value
                                    .then(
                                            Commands.literal("set")
                                                    .then(
                                                            sourceNode.create((ctx, compoundTag, nbtPath, sourceTags) -> {
                                                                // Use the last resolved source tag; copy it to avoid aliasing
                                                                Tag lastTag = (Tag) Iterables.getLast(sourceTags);
                                                                Objects.requireNonNull(lastTag);
                                                                return nbtPath.set(compoundTag, lastTag::copy);
                                                            })
                                                    )
                                    )

                                    // merge <source>  →  deep-merge compound tags at the target path
                                    .then(
                                            Commands.literal("merge")
                                                    .then(
                                                            sourceNode.create((ctx, compoundTag, nbtPath, sourceTags) -> {
                                                                // getOrCreate ensures the path exists, creating a CompoundTag if absent
                                                                Collection<Tag> targetTags = nbtPath.getOrCreate(compoundTag, CompoundTag::new);
                                                                int modifiedCount = 0;

                                                                for (Tag targetTag : targetTags) {
                                                                    if (!(targetTag instanceof CompoundTag)) {
                                                                        throw ERROR_EXPECTED_OBJECT.create(targetTag);
                                                                    }

                                                                    CompoundTag target = (CompoundTag) targetTag;
                                                                    CompoundTag snapshot = target.copy(); // snapshot to detect changes

                                                                    for (Tag sourceTag : sourceTags) {
                                                                        if (!(sourceTag instanceof CompoundTag)) {
                                                                            throw ERROR_EXPECTED_OBJECT.create(sourceTag);
                                                                        }
                                                                        target.merge((CompoundTag) sourceTag);
                                                                    }

                                                                    // only count targets that were actually modified
                                                                    modifiedCount += snapshot.equals(target) ? 0 : 1;
                                                                }

                                                                return modifiedCount;
                                                            })
                                                    )
                                    )
                    )
            );
        }


        dispatcher.register(command);
    }

    private static int insertAtIndex(int p_139361_, CompoundTag compoundTag, NbtPathArgument.NbtPath nbtPath, List<Tag> tagList) throws CommandSyntaxException {
        Collection<Tag> tagCollection = nbtPath.getOrCreate(compoundTag, ListTag::new);
        int flag1 = 0;

        for(Tag collectionTag : tagCollection) {
            if (!(collectionTag instanceof CollectionTag)) {
                throw ERROR_EXPECTED_LIST.create(collectionTag);
            }

            boolean flag2 = false;
            CollectionTag<?> $$8 = (CollectionTag)collectionTag;
            int $$9 = p_139361_ < 0 ? $$8.size() + p_139361_ + 1 : p_139361_;

            for(Tag listTag : tagList) {
                try {
                    if ($$8.addTag($$9, listTag.copy())) {
                        ++$$9;
                        flag2 = true;
                    }
                } catch (IndexOutOfBoundsException var14) {
                    throw ERROR_INVALID_INDEX.create($$9);
                }
            }

            flag1 += flag2 ? 1 : 0;
        }

        return flag1;
    }

    private static ArgumentBuilder<CommandSourceStack, ?> decorateModification(BiConsumer<ArgumentBuilder<CommandSourceStack, ?>, DataManipulatorDecorator> p_139404_) {
        LiteralArgumentBuilder<CommandSourceStack> $$1 = Commands.literal("modify");

        for(DataCommands.DataProvider $$2 : TARGET_PROVIDERS) {
            $$2.wrap($$1, (p_139408_) -> {
                ArgumentBuilder<CommandSourceStack, ?> $$3 = Commands.argument("targetPath", NbtPathArgument.nbtPath());

                for(DataCommands.DataProvider $$4 : SOURCE_PROVIDERS) {
                    p_139404_.accept($$3, (DataManipulatorDecorator)(p_142807_) -> $$4.wrap(Commands.literal("from"), (p_142812_) -> p_142812_.executes((p_142830_) -> {
                        List<Tag> $$7 = Collections.singletonList($$4.access(p_142830_).getData());
                        return manipulateData(p_142830_, $$2, p_142807_, $$7);
                    }).then(Commands.argument("sourcePath", NbtPathArgument.nbtPath()).executes((p_142817_) -> {
                        DataAccessor $$7 = $$4.access(p_142817_);
                        NbtPathArgument.NbtPath $$5 = NbtPathArgument.getPath(p_142817_, "sourcePath");
                        List<Tag> $$6 = $$5.get($$7.getData());
                        return manipulateData(p_142817_, $$2, p_142807_, $$6);
                    }))));
                }

                p_139404_.accept($$3, (DataManipulatorDecorator)(p_142799_) -> Commands.literal("value").then(Commands.argument("value", NbtTagArgument.nbtTag()).executes((p_142803_) -> {
                    List<Tag> $$4 = Collections.singletonList(NbtTagArgument.getNbtTag(p_142803_, "value"));
                    return manipulateData(p_142803_, $$2, p_142799_, $$4);
                })));
                return p_139408_.then($$3);
            });
        }

        return $$1;
    }

    private static int manipulateData(CommandContext<CommandSourceStack> p_139376_, DataCommands.DataProvider p_139377_, DataManipulator p_139378_, List<Tag> p_139379_) throws CommandSyntaxException {
        DataAccessor $$4 = p_139377_.access(p_139376_);
        NbtPathArgument.NbtPath $$5 = NbtPathArgument.getPath(p_139376_, "targetPath");
        CompoundTag $$6 = $$4.getData();
        int $$7 = p_139378_.modify(p_139376_, $$6, $$5, p_139379_);
        if ($$7 == 0) {
            throw ERROR_MERGE_UNCHANGED.create();
        } else {
            $$4.setData($$6);
            ((CommandSourceStack)p_139376_.getSource()).sendSuccess($$4.getModifiedSuccess(), true);
            return $$7;
        }
    }

    private static int removeData(CommandSourceStack p_139386_, DataAccessor p_139387_, NbtPathArgument.NbtPath p_139388_) throws CommandSyntaxException {
        CompoundTag $$3 = p_139387_.getData();
        int $$4 = p_139388_.remove($$3);
        if ($$4 == 0) {
            throw ERROR_MERGE_UNCHANGED.create();
        } else {
            p_139387_.setData($$3);
            p_139386_.sendSuccess(p_139387_.getModifiedSuccess(), true);
            return $$4;
        }
    }

    private static Tag getSingleTag(NbtPathArgument.NbtPath p_139399_, DataAccessor p_139400_) throws CommandSyntaxException {
        Collection<Tag> $$2 = p_139399_.get(p_139400_.getData());
        Iterator<Tag> $$3 = $$2.iterator();
        Tag $$4 = (Tag)$$3.next();
        if ($$3.hasNext()) {
            throw ERROR_MULTIPLE_TAGS.create();
        } else {
            return $$4;
        }
    }

    private static int getData(CommandSourceStack p_139444_, DataAccessor p_139445_, NbtPathArgument.NbtPath p_139446_) throws CommandSyntaxException {
        Tag $$3 = getSingleTag(p_139446_, p_139445_);
        int $$4;
        if ($$3 instanceof NumericTag) {
            $$4 = Mth.floor(((NumericTag)$$3).getAsDouble());
        } else if ($$3 instanceof CollectionTag) {
            $$4 = ((CollectionTag)$$3).size();
        } else if ($$3 instanceof CompoundTag) {
            $$4 = ((CompoundTag)$$3).size();
        } else {
            if (!($$3 instanceof StringTag)) {
                throw ERROR_GET_NON_EXISTENT.create(p_139446_.toString());
            }

            $$4 = $$3.getAsString().length();
        }

        p_139444_.sendSuccess(p_139445_.getPrintSuccess($$3), false);
        return $$4;
    }

    private static int getNumeric(CommandSourceStack p_139390_, DataAccessor p_139391_, NbtPathArgument.NbtPath p_139392_, double p_139393_) throws CommandSyntaxException {
        Tag $$4 = getSingleTag(p_139392_, p_139391_);
        if (!($$4 instanceof NumericTag)) {
            throw ERROR_GET_NOT_NUMBER.create(p_139392_.toString());
        } else {
            int $$5 = Mth.floor(((NumericTag)$$4).getAsDouble() * p_139393_);
            p_139390_.sendSuccess(p_139391_.getPrintSuccess(p_139392_, p_139393_, $$5), false);
            return $$5;
        }
    }

    private static int getData(CommandSourceStack p_139383_, DataAccessor p_139384_) throws CommandSyntaxException {
        p_139383_.sendSuccess(p_139384_.getPrintSuccess(p_139384_.getData()), false);
        return 1;
    }

    private static int mergeData(CommandSourceStack p_139395_, DataAccessor p_139396_, CompoundTag p_139397_) throws CommandSyntaxException {
        CompoundTag $$3 = p_139396_.getData();
        CompoundTag $$4 = $$3.copy().merge(p_139397_);
        if ($$3.equals($$4)) {
            throw ERROR_MERGE_UNCHANGED.create();
        } else {
            p_139396_.setData($$4);
            p_139395_.sendSuccess(p_139396_.getModifiedSuccess(), true);
            return 1;
        }
    }

    private static int execute(CommandContext<CommandSourceStack> command){
//        if(command.getSource().getEntity() instanceof Player){
//            Player player = (Player) command.getSource().getEntity();
//            player.sendMessage(new TextComponent("wip"), Util.NIL_UUID);
//        }
//        return Command.SINGLE_SUCCESS;



        return Command.SINGLE_SUCCESS;
    }

    interface DataManipulator {
        int modify(CommandContext<CommandSourceStack> var1, CompoundTag var2, NbtPathArgument.NbtPath var3, List<Tag> var4) throws CommandSyntaxException;
    }

    interface DataManipulatorDecorator {
        ArgumentBuilder<CommandSourceStack, ?> create(DataManipulator var1);
    }
}