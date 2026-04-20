package dev.wren.crowsnest.internal.argument.types;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import dev.wren.crowsnest.internal.argument.types.subtypes.Coordinates4;
import dev.wren.crowsnest.internal.argument.types.subtypes.LocalCoordinates4;
import dev.wren.crowsnest.internal.argument.types.subtypes.SuggestionUtil;
import dev.wren.crowsnest.internal.argument.types.subtypes.WorldCoordinates4;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaterniond;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

public class QuaterniondArgument implements ArgumentType<Coordinates4> {

    private static final Collection<String> EXAMPLES = Arrays.asList("0 0 0 0", "~ ~ ~ ~", "^ ^ ^ ^", "^1 ^ ^-5 ^-1", "0.1 -0.5 .9 2", "~0.5 ~1 ~-5 ~2");
    public static final SimpleCommandExceptionType ERROR_NOT_COMPLETE = new SimpleCommandExceptionType(Component.translatable("argument.pos4d.incomplete"));
    public static final SimpleCommandExceptionType ERROR_MIXED_TYPE = new SimpleCommandExceptionType(Component.translatable("argument.pos.mixed"));
    private final boolean centerCorrect;

    public QuaterniondArgument(boolean pCenterCorrect) {
        this.centerCorrect = pCenterCorrect;
    }

    public static QuaterniondArgument quaterniond() {
        return new QuaterniondArgument(true);
    }

    public static QuaterniondArgument quaterniond(boolean pCenterCorrect) {
        return new QuaterniondArgument(pCenterCorrect);
    }

    public static Quaterniond getQuaterniond(CommandContext<CommandSourceStack> pContext, String pName) {
        Vec3 vec3 = pContext.getArgument(pName, Coordinates4.class).getPosition(pContext.getSource());
        Vec2 vec2 = pContext.getArgument(pName, Coordinates4.class).getRotation(pContext.getSource());
        return new Quaterniond(vec3.x, vec3.y, vec3.z, vec2.x);
    }

    public static Coordinates4 getCoordinates4(CommandContext<CommandSourceStack> pContext, String pName) {
        return pContext.getArgument(pName, Coordinates4.class);
    }

    public Coordinates4 parse(StringReader p_120843_) throws CommandSyntaxException {
        return (Coordinates4)(p_120843_.canRead() && p_120843_.peek() == '^' ? LocalCoordinates4.parse(p_120843_) : WorldCoordinates4.parseDouble(p_120843_, this.centerCorrect));
    }

    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> pContext, SuggestionsBuilder pBuilder) {
        if (!(pContext.getSource() instanceof SharedSuggestionProvider)) {
            return Suggestions.empty();
        } else {
            String s = pBuilder.getRemaining();
            Collection<Coordinates4.Text> collection;
            if (!s.isEmpty() && s.charAt(0) == '^') {
                collection = Collections.singleton(Coordinates4.Text.DEFAULT_LOCAL);
            } else {
                collection = Collections.singleton(Coordinates4.Text.DEFAULT_GLOBAL);
            }

            return SuggestionUtil.suggestCoordinates4(s, collection, pBuilder, Commands.createValidator(this::parse));
        }
    }



    public Collection<String> getExamples() {
        return EXAMPLES;
    }

}
