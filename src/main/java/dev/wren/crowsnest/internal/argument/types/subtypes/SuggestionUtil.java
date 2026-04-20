package dev.wren.crowsnest.internal.argument.types.subtypes;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

import static net.minecraft.commands.SharedSuggestionProvider.suggest;

public class SuggestionUtil {

    public static CompletableFuture<Suggestions> suggestCoordinates4(String pRemaining, Collection<Coordinates4.Text> pCoordinates, SuggestionsBuilder pBuilder, Predicate<String> pValidator) {
        List<String> list = Lists.newArrayList();
        if (Strings.isNullOrEmpty(pRemaining)) {
            for (Coordinates4.Text c4 : pCoordinates) {
                String s = c4.x + " " + c4.y + " " + c4.z + " " + c4.w;
                if (pValidator.test(s)) {
                    list.add(c4.x);
                    list.add(c4.x + " " + c4.y);
                    list.add(c4.w + " " + c4.y + " " + c4.z);
                    list.add(s);
                }
            }
        } else {
            String[] astring = pRemaining.split(" ");
            if (astring.length == 1) {
                for (Coordinates4.Text c4 : pCoordinates) {
                    String s1 = astring[0] + " " + c4.y + " " + c4.z + " " + c4.w;
                    if (pValidator.test(s1)) {
                        list.add(astring[0] + " " + c4.y);
                        list.add(astring[0] + " " + c4.y + " " + c4.z);
                        list.add(s1);
                    }
                }
            } else if (astring.length == 2) {
                for (Coordinates4.Text c4 : pCoordinates) {
                    String s2 = astring[0] + " " + astring[1] + " " + c4.z + " " + c4.w;
                    if (pValidator.test(s2)) {
                        list.add(astring[0] + " " + astring[1] + " " + c4.z);
                        list.add(astring[0] + " " + astring[1] + " " + c4.z + " " + c4.w);
                        list.add(s2);
                    }
                }
            } else if (astring.length == 3) {
                for (Coordinates4.Text c4 : pCoordinates) {
                    String s3 = astring[0] + " " + astring[1] + " " + astring[2] + " " + c4.w;
                    if (pValidator.test(s3)) {
                        list.add(s3);
                    }
                }
            }
        }

        return suggest(list, pBuilder);
    }
}
