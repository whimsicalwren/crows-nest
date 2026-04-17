package dev.wren.crowsnest.internal.argument.types;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.coordinates.Coordinates;
import net.minecraft.commands.arguments.coordinates.LocalCoordinates;
import net.minecraft.commands.arguments.coordinates.WorldCoordinates;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3d;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

public class Vector3dArgument implements ArgumentType<Coordinates> {

    private static final Collection<String> EXAMPLES = Arrays.asList("0 0 0", "~ ~ ~", "^ ^ ^", "^1 ^ ^-5", "0.1 -0.5 .9", "~0.5 ~1 ~-5");
    public static final SimpleCommandExceptionType ERROR_NOT_COMPLETE = new SimpleCommandExceptionType(Component.translatable("argument.pos3d.incomplete"));
    public static final SimpleCommandExceptionType ERROR_MIXED_TYPE = new SimpleCommandExceptionType(Component.translatable("argument.pos.mixed"));
    private final boolean centerCorrect;

    public Vector3dArgument(boolean pCenterCorrect) {
        this.centerCorrect = pCenterCorrect;
    }

    public static Vector3dArgument vector3d() {
        return new Vector3dArgument(true);
    }

    public static Vector3dArgument vector3d(boolean pCenterCorrect) {
        return new Vector3dArgument(pCenterCorrect);
    }

    public static Vector3d getVector3d(CommandContext<CommandSourceStack> pContext, String pName) {
        Vec3 vec3 = pContext.getArgument(pName, Coordinates.class).getPosition(pContext.getSource());
        return new Vector3d(vec3.x, vec3.y, vec3.z);
    }

    public static Coordinates getCoordinates(CommandContext<CommandSourceStack> pContext, String pName) {
        return pContext.getArgument(pName, Coordinates.class);
    }

    public Coordinates parse(StringReader p_120843_) throws CommandSyntaxException {
        return (Coordinates)(p_120843_.canRead() && p_120843_.peek() == '^' ? LocalCoordinates.parse(p_120843_) : WorldCoordinates.parseDouble(p_120843_, this.centerCorrect));
    }

    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> pContext, SuggestionsBuilder pBuilder) {
        if (!(pContext.getSource() instanceof SharedSuggestionProvider)) {
            return Suggestions.empty();
        } else {
            String s = pBuilder.getRemaining();
            Collection<SharedSuggestionProvider.TextCoordinates> collection;
            if (!s.isEmpty() && s.charAt(0) == '^') {
                collection = Collections.singleton(SharedSuggestionProvider.TextCoordinates.DEFAULT_LOCAL);
            } else {
                collection = ((SharedSuggestionProvider)pContext.getSource()).getAbsoluteCoordinates();
            }

            return SharedSuggestionProvider.suggestCoordinates(s, collection, pBuilder, Commands.createValidator(this::parse));
        }
    }

    public Collection<String> getExamples() {
        return EXAMPLES;
    }

}
