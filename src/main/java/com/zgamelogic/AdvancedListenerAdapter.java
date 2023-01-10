package com.zgamelogic;

import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.EntitySelectInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.events.message.react.GenericMessageReactionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.Objects;

import static java.util.Arrays.asList;

/**
 * An advanced version of the ListenerAdapter class.
 * Highly annotative allowing automatic method calling.
 * @see AutoCompleteResponse
 * @see UserInteractionResponse
 * @see MessageInteractionResponse
 * @see ButtonResponse
 * @see EmoteResponse
 * @see ModalResponse
 * @see SlashResponse
 * @see EntitySelectionResponse
 * @see StringSelectionResponse
 * @author Ben Shabowski
 */
@Slf4j
public abstract class AdvancedListenerAdapter extends ListenerAdapter {

    public AdvancedListenerAdapter(){
        log.info("Registering annotated methods for class: " + this.getClass().getName());
        for(Method m : getAnnotatedMethods()){
            log.info("\t\t" + m.getName());
        }
    }

    @Override
    public void onCommandAutoCompleteInteraction(@NotNull CommandAutoCompleteInteractionEvent event) {
        for(Method m : getAnnotatedMethods(AutoCompleteResponse.class, AutoCompleteResponses.class)){
            LinkedList<AutoCompleteResponse> acrs = new LinkedList<>();
            for(Annotation a: m.getAnnotations()){
                if(a instanceof AutoCompleteResponse) acrs.add((AutoCompleteResponse) a);
                if(a instanceof AutoCompleteResponses) acrs.addAll(asList(((AutoCompleteResponses) a).value()));
            }
            for(AutoCompleteResponse ar: acrs) {
                if (!ar.slashCommandId().equals(event.getName())) continue;
                if (!ar.slashSubCommandId().isEmpty() && !ar.slashSubCommandId().equals(event.getSubcommandName()))
                    continue;
                if (!ar.focusedOption().equals(event.getFocusedOption().getName())) continue;
                try {
                    log.debug("Calling method: " + m.getName() + " for an CommandAutoCompleteInteractionEvent");
                    log.debug("Guild id: " + ((event.getGuild() != null) ? event.getGuild().getId() : "null"));
                    log.debug("User id: " + event.getUser().getId());
                    m.setAccessible(true);
                    m.invoke(this, event);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    log.error("Guild id: " + ((event.getGuild() != null) ? event.getGuild().getId() : "null"));
                    log.error("User id: " + event.getUser().getId());
                    log.error("Error in onCommandAutoCompleteInteraction", e);
                }
            }
        }
    }

    @Override
    public void onUserContextInteraction(@NotNull UserContextInteractionEvent event) {
        for(Method m : getAnnotatedMethods(UserInteractionResponse.class, UserInteractionResponses.class)){
            LinkedList<UserInteractionResponse> uirs = new LinkedList<>();
            for(Annotation a: m.getAnnotations()){
                if(a instanceof UserInteractionResponse) uirs.add((UserInteractionResponse) a);
                if(a instanceof UserInteractionResponses) uirs.addAll(asList(((UserInteractionResponses) a).value()));
            }
            for(UserInteractionResponse ur: uirs) {
                if (!ur.value().equals(event.getInteraction().getName())) continue;
                try {
                    log.debug("Calling method: " + m.getName() + " for an UserContextInteractionEvent");
                    log.debug("Guild id: " + ((event.getGuild() != null) ? event.getGuild().getId() : "null"));
                    log.debug("User id: " + event.getUser().getId());
                    m.setAccessible(true);
                    m.invoke(this, event);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    log.error("Guild id: " + ((event.getGuild() != null) ? event.getGuild().getId() : "null"));
                    log.error("User id: " + event.getUser().getId());
                    log.error("Error in onUserContextInteraction", e);
                }
            }
        }
    }

    @Override
    public void onMessageContextInteraction(@NotNull MessageContextInteractionEvent event) {
        for(Method m : getAnnotatedMethods(MessageInteractionResponse.class, MessageInteractionResponses.class)){
            LinkedList<MessageInteractionResponse> mirs = new LinkedList<>();
            for(Annotation a: m.getAnnotations()){
                if(a instanceof MessageInteractionResponse) mirs.add((MessageInteractionResponse) a);
                if(a instanceof MessageInteractionResponses) mirs.addAll(asList(((MessageInteractionResponses) a).value()));
            }
            for(MessageInteractionResponse mr: mirs) {
                if (!mr.value().equals(event.getInteraction().getName())) continue;
                try {
                    log.debug("Calling method: " + m.getName() + " for an MessageContextInteractionEvent");
                    log.debug("Guild id: " + ((event.getGuild() != null) ? event.getGuild().getId() : "null"));
                    log.debug("User id: " + event.getUser().getId());
                    m.setAccessible(true);
                    m.invoke(this, event);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    log.error("Guild id: " + ((event.getGuild() != null) ? event.getGuild().getId() : "null"));
                    log.error("User id: " + event.getUser().getId());
                    log.error("Channel id: " + event.getChannel().getId());
                    log.error("Error in onMessageContextInteraction", e);
                }
            }
        }
    }

    @Override
    public void onStringSelectInteraction(@NotNull StringSelectInteractionEvent event) {
        for(Method m : getAnnotatedMethods(StringSelectionResponse.class, StringSelectionResponses.class)){
            LinkedList<StringSelectionResponse> ssrs = new LinkedList<>();
            for(Annotation a: m.getAnnotations()){
                if(a instanceof StringSelectionResponse) ssrs.add((StringSelectionResponse) a);
                if(a instanceof StringSelectionResponses) ssrs.addAll(asList(((StringSelectionResponses) a).value()));
            }
            for(StringSelectionResponse ssr: ssrs) {
                if (!ssr.value().equals(event.getSelectMenu().getId())) continue;
                if (!ssr.selectedOptionValue().isEmpty() && !
                        ssr.selectedOptionValue().equals(event.getSelectedOptions().get(0).getValue())) continue;
                try {
                    log.debug("Calling method: " + m.getName() + " for an StringSelectInteractionEvent");
                    log.debug("Guild id: " + ((event.getGuild() != null) ? event.getGuild().getId() : "null"));
                    log.debug("User id: " + event.getUser().getId());
                    m.setAccessible(true);
                    m.invoke(this, event);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    log.error("Guild id: " + ((event.getGuild() != null) ? event.getGuild().getId() : "null"));
                    log.error("User id: " + event.getUser().getId());
                    log.error("Message id: " + event.getMessageId());
                    log.error("Channel id: " + event.getChannel().getId());
                    log.error("Error in onStringSelectInteraction", e);
                }
            }
        }
    }

    @Override
    public void onEntitySelectInteraction(@NotNull EntitySelectInteractionEvent event) {
        for(Method m : getAnnotatedMethods(EntitySelectionResponse.class, EntitySelectionResponses.class)){
            LinkedList<EntitySelectionResponse> esrs = new LinkedList<>();
            for(Annotation a: m.getAnnotations()){
                if(a instanceof EntitySelectionResponse) esrs.add((EntitySelectionResponse) a);
                if(a instanceof EntitySelectionResponses) esrs.addAll(asList(((EntitySelectionResponses) a).value()));
            }
            for(EntitySelectionResponse esr: esrs) {
                if (!esr.value().equals(event.getSelectMenu().getId())) continue;
                try {
                    log.debug("Calling method: " + m.getName() + " for an EntitySelectInteractionEvent");
                    log.debug("Guild id: " + ((event.getGuild() != null) ? event.getGuild().getId() : "null"));
                    log.debug("User id: " + event.getUser().getId());
                    m.setAccessible(true);
                    m.invoke(this, event);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    log.error("Guild id: " + ((event.getGuild() != null) ? event.getGuild().getId() : "null"));
                    log.error("User id: " + event.getUser().getId());
                    log.error("Message id: " + event.getMessageId());
                    log.error("Channel id: " + event.getChannel().getId());
                    log.error("Error in onEntitySelectInteraction", e);
                }
            }
        }
    }

    @Override
    public void onModalInteraction(@NotNull ModalInteractionEvent event) {
        for(Method m : getAnnotatedMethods(ModalResponse.class, ModalResponses.class)){
            LinkedList<ModalResponse> mrs = new LinkedList<>();
            for(Annotation a: m.getAnnotations()){
                if(a instanceof ModalResponse) mrs.add((ModalResponse) a);
                if(a instanceof ModalResponses) mrs.addAll(asList(((ModalResponses) a).value()));
            }
            for(ModalResponse mr: mrs) {
                if (!mr.value().equals(event.getModalId())) continue;
                try {
                    log.debug("Calling method: " + m.getName() + " for an ModalInteractionEvent");
                    log.debug("Guild id: " + ((event.getGuild() != null) ? event.getGuild().getId() : "null"));
                    log.debug("User id: " + event.getUser().getId());
                    m.setAccessible(true);
                    m.invoke(this, event);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    log.error("Guild id: " + ((event.getGuild() != null) ? event.getGuild().getId() : "null"));
                    log.error("User id: " + event.getUser().getId());
                    log.error("Error in onModalInteraction", e);
                }
            }
        }
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        for(Method m : getAnnotatedMethods(SlashResponse.class, SlashResponses.class)){
            LinkedList<SlashResponse> srs = new LinkedList<>();
            for(Annotation a: m.getAnnotations()){
                if(a instanceof SlashResponse) srs.add((SlashResponse) a);
                if(a instanceof SlashResponses) srs.addAll(asList(((SlashResponses) a).value()));
            }
            for(SlashResponse sr: srs) {
                if (!sr.value().equals(event.getName())) continue;
                if (!sr.subCommandName().isEmpty() && !sr.subCommandName().equals(event.getSubcommandName())) continue;
                try {
                    log.debug("Calling method: " + m.getName() + " for an SlashCommandInteractionEvent");
                    log.debug("Guild id: " + ((event.getGuild() != null) ? event.getGuild().getId() : "null"));
                    log.debug("User id: " + event.getUser().getId());
                    m.setAccessible(true);
                    m.invoke(this, event);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    log.error("Guild id: " + ((event.getGuild() != null) ? event.getGuild().getId() : "null"));
                    log.error("User id: " + event.getUser().getId());
                    log.error("Channel id: " + event.getChannel().getId());
                    log.error("Error in onSlashCommandInteraction", e);
                }
            }
        }
    }

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        for(Method m : getAnnotatedMethods(ButtonResponse.class, ButtonResponses.class)){
            LinkedList<ButtonResponse> brs = new LinkedList<>();
            for(Annotation a: m.getAnnotations()){
                if(a instanceof ButtonResponse) brs.add((ButtonResponse) a);
                if(a instanceof ButtonResponses) brs.addAll(asList(((ButtonResponses) a).value()));
            }
            for(ButtonResponse br: brs) {
                if (!br.value().equals(event.getButton().getId())) continue;
                try {
                    log.debug("Calling method: " + m.getName() + " for an ButtonInteractionEvent");
                    log.debug("Guild id: " + ((event.getGuild() != null) ? event.getGuild().getId() : "null"));
                    log.debug("User id: " + event.getUser().getId());
                    m.setAccessible(true);
                    m.invoke(this, event);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    log.error("Guild id: " + ((event.getGuild() != null) ? event.getGuild().getId() : "null"));
                    log.error("User id: " + event.getUser().getId());
                    log.error("Message id: " + event.getMessageId());
                    log.error("Channel id: " + event.getChannel().getId());
                    log.error("Error in onButtonInteraction", e);
                }
            }
        }
    }

    @Override
    public void onGenericMessageReaction(@NotNull GenericMessageReactionEvent event) {
        for(Method m : getAnnotatedMethods(EmoteResponse.class, EmoteResponses.class)){
            LinkedList<EmoteResponse> ers = new LinkedList<>();
            for(Annotation a: m.getAnnotations()){
                if(a instanceof EmoteResponse) ers.add((EmoteResponse) a);
                if(a instanceof EmoteResponses) ers.addAll(asList(((EmoteResponses) a).value()));
            }
            for(EmoteResponse er: ers) {
                if (!er.value().equals(event.getReaction().getEmoji().getName())) continue;
                if (!Objects.requireNonNull(event.getRawData()).hasKey("t")) continue;
                if (er.isAdding() != event.getRawData().get("t").equals("MESSAGE_REACTION_ADD")) continue;
                try {
                    log.debug("Calling method: " + m.getName() + " for an GenericMessageReactionEvent");
                    log.debug("Guild id: " + ((event.getGuild() != null) ? event.getGuild().getId() : "null"));
                    log.debug("User id: " + event.getUser().getId());
                    m.setAccessible(true);
                    m.invoke(this, event);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    log.error("Guild id: " + ((event.getGuild() != null) ? event.getGuild().getId() : "null"));
                    log.error("User id: " + event.getUser().getId());
                    log.error("Message id: " + event.getMessageId());
                    log.error("Channel id: " + event.getChannel().getId());
                    log.error("Error in onGenericMessageReaction", e);
                }
            }
        }
    }

    public LinkedList<Method> getAnnotatedMethods(Class...classes){
        LinkedList<Method> methods = new LinkedList<>();
        for(Method m : this.getClass().getDeclaredMethods()){
            if(classes.length == 0 && m.getAnnotations().length > 0) {
                for (Class current : this.getClass().getSuperclass().getDeclaredClasses()) {
                    if (m.isAnnotationPresent(current)) {
                        methods.add(m);
                        break;
                    }
                }
            } else {
                for (Class current : classes) {
                    if (m.isAnnotationPresent(current)) {
                        methods.add(m);
                        break;
                    }
                }
            }
        }
        return methods;
    }

    /**
     * Annotation for CommandAutoCompleteInteractionEvent
     * Here is a code example of a slash command with options
     * <pre>{@code
     * Commands.slash("test", "Test command with fruit")
     *     .addOption(OptionType.STRING, "fruit", "Fruit to pick", true, true);
     * }</pre>
     * and here is an example of the method that will get called
     * <pre>{@code
     * {@literal @}AutoCompleteResponse(slashCommandId = "fruit", focusedOption = "name")
     * private void autocompleteExample(CommandAutoCompleteInteractionEvent event){
     *     String[] words = new String[]{"apple", "apricot", "banana", "cherry", "coconut", "cranberry"};
     *     List$&#123;Command.Choice$&#125; options = Stream.of(words)
     *             .filter(word -$&#125; word.startsWith(event.getFocusedOption().getValue())) // only display words that start with the user's current input
     *             .map(word -$&#125; new Command.Choice(word, word)) // map the words to choices
     *             .collect(Collectors.toList());
     *     event.replyChoices(options).queue();
     * }
     * }</pre>
     * If your annotated methods are not getting called, perhaps you overrode CommandAutoCompleteInteractionEvent in this class already.
     * Make sure to super call the method first so your annotated methods get called.
     * <pre>{@code
     * super.CommandAutoCompleteInteractionEvent(event);
     * }</pre>
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @Repeatable(AutoCompleteResponses.class)
    public @interface AutoCompleteResponse {
        /**
         * Slash command ID
         * @return slashCommandId
         */
        String slashCommandId();

        /**
         * Focused Option
         * @return focusedOption
         */
        String focusedOption();

        String slashSubCommandId() default "";
    }

    /**
     * Annotation to allow for multiple Auto complete responses on one method
     * @see AutoCompleteResponse
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface AutoCompleteResponses { AutoCompleteResponse[] value(); }

    /**
     * Annotation for UserContextInteractionEvent
     * Here is a code example of a MessageContextInteractionEvent
     * <pre>{@code
     * Commands.context(Command.Type.USER, "Test"));
     * }</pre>
     * and here is a code example of the method that will get called when a user does this interaction
     * <pre>{@code
     * {@literal @}UserInteractionResponse("test")
     * private void userInteractionExample(UserContextInteractionEvent event){
     *     // TODO respond to event
     * }
     * }</pre>
     * If your annotated methods are not getting called, perhaps you overrode onUserContextInteraction in this class already.
     * Make sure to super call the method first so your annotated methods get called.
     * <pre>{@code
     * super.onUserContextInteraction(event);
     * }</pre>
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @Repeatable(UserInteractionResponses.class)
    public @interface UserInteractionResponse {
        /**
         * ID of the interaction
         * @return interactionId
         */
        String value();
    }

    /**
     * Annotation to allow for multiple User interaction responses to be put on one method
     * @see UserInteractionResponse
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface UserInteractionResponses { UserInteractionResponse[] value(); }

    /**
     * Annotation for MessageContextInteractionEvent
     * Here is a code example of a MessageContextInteractionEvent
     * <pre>{@code
     * Commands.context(Command.Type.MESSAGE, "Test"));
     * }</pre>
     * and here is a code example of the method that will get called when a user does this interaction
     * <pre>{@code
     * {@literal @}MessageInteractionResponse("test")
     * private void messageInteractionExample(MessageContextInteractionEvent event){
     *     // TODO respond to event
     * }
     * }</pre>
     * If your annotated methods are not getting called, perhaps you overrode onMessageContextInteraction in this class already.
     * Make sure to super call the method first so your annotated methods get called.
     * <pre>{@code
     * super.onMessageContextInteraction(event);
     * }</pre>
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @Repeatable(MessageInteractionResponses.class)
    public @interface MessageInteractionResponse {

        /**
         * ID of the interaction
         * @return interactionId
         */
        String value();
    }

    /**
     * Annotation to allow for multiple Message interaction responses on one method
     * @see MessageInteractionResponse
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface MessageInteractionResponses{ MessageInteractionResponse[] value(); }

    /**
     * Annotation for ButtonInteractionEvent
     * Here is a code example of a button
     * <pre>{@code
     * Button.success("example_button", "Example");
     * }</pre>
     * and here is a code example of the method that will get called when a user hits that button
     * <pre>{@code
     * {@literal @}ButtonResponse("example_button")
     * private void exampleButtonResponse(ButtonInteractionEvent event){
     *     // TODO respond to event
     * }
     * }</pre>
     * If your annotated methods are not getting called, perhaps you overrode onButtonInteraction in this class already.
     * Make sure to super call the method first so your annotated methods get called.
     * <pre>{@code
     * super.onButtonInteraction(event);
     * }</pre>
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @Repeatable(ButtonResponses.class)
    public @interface ButtonResponse {
        /**
         * ID of the button that was pressed
         * @return button Id
         */
        String value();
    }

    /**
     * Annotation to allow for multiple Button responses on one method
     * @see ButtonResponse
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface ButtonResponses{ ButtonResponse[] value(); }

    /**
     * Annotation for onGenericMessageReaction
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @Repeatable(EmoteResponses.class)
    public @interface EmoteResponse {
        /**
         * Name of the reaction emoji
         * @return Name of the reaction emoji
         */
        String value();

        /**
         * Boolean to trigger method if the reaction is being added.
         * True if method should trigger on reaction add.
         * False if the method should trigger on reaction remove.
         * @return if this method should be called if the reaction is added
         */
        boolean isAdding();
    }

    /**
     * Annotation to allow for multiple Button responses on one method
     * @see EmoteResponse
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface EmoteResponses{ EmoteResponse[] value(); }

    /**
     * Annotation for ModalInteractionEvent
     * Here is a code example of a modal response
     * <pre>{@code
     * Modal.create("example_modal", "Example Modal Title")
     *         .addActionRow(TextInput.create("example_input", "Input", TextInputStyle.SHORT).build())
     * .build();
     * }</pre>
     * and here is a code example of the method that will get called when a user submits that modal
     * <pre>{@code
     * {@literal @}ModalResponse("example_modal")
     * private void exampleModalResponse(ModalInteractionEvent event){
     *    // TODO respond to event
     * }
     * }</pre>
     * If your annotated methods are not getting called, perhaps you overrode onModalInteraction in this class already.
     * Make sure to super call the method first so your annotated methods get called.
     * <pre>{@code
     * super.onModalInteraction(event);
     * }</pre>
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @Repeatable(ModalResponses.class)
    public @interface ModalResponse {
        /**
         * Name of the modal
         * @return name of the modal
         */
        String value();
    }

    /**
     * Annotation to allow for multiple modal responses on one method
     * @see ModalResponse
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface ModalResponses{ ModalResponse[] value(); }

    /**
     * Annotation for SlashCommandInteractionEvent.
     * Here is a code example of a slash command
     * <pre>{@code
     *  Commands.slash("test", "This is a description");
     * }</pre>
     * and here is a code example of the method that will get called when a user triggers that command
     * <pre>{@code
     * {@literal @}SlashResponse("test")
     * private void testMethod(SlashCommandInteractionEvent event){
     *     // TODO respond to event
     * }
     * }</pre>
     * Here is a code example of a slash command with a sub command
     * <pre>{@code
     * Commands.slash("test", "This is a description")
     *    .addSubcommands(new SubcommandData("sub","This is a subcommand"));
     * }</pre>
     * and here is a code example of the method that will get called when a user triggers that command
     * <pre>{@code
     * {@literal @}SlashResponse(value = "test", subCommandName = "sub")
     * private void testMethodSub(SlashCommandInteractionEvent event){
     *     // TODO respond to event
     * }
     * }</pre>
     * If your annotated methods are not getting called, perhaps you overrode onSlashCommandInteraction in this class already.
     * Make sure to super call the method first so your annotated methods get called.
     * <pre>{@code
     * super.onSlashCommandInteraction(event);
     * }</pre>
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @Repeatable(SlashResponses.class)
    public @interface SlashResponse {
        /**
         * Command name
         * @return Command name
         */
        String value();

        /**
         * Optional, use only if there is a subcommand
         * @return Subcommand name
         */
        String subCommandName() default "";
    }

    /**
     * Annotation to allow for multiple Slash responses on one method
     * @see SlashResponse
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface SlashResponses { SlashResponse[] value(); }

    /**
     * Annotation for EntitySelectInteractionEvent
     * Here is a code example of an entity selection
     * <pre>{@code
     *   EntitySelectMenu.create("test_selection", EntitySelectMenu.SelectTarget.USER).setMinValues(1).setMaxValues(25).build();
     * }</pre>
     * and here is a code example of the method that will get called when a user triggers that entity selection
     * {@literal @}EntitySelectionResponse("test_selection")
     * private void entitySelRes(EntitySelectInteractionEvent event){
     *     // TODO respond to event
     * }
     * If your annotated methods are not getting called, perhaps you overrode onEntitySelectInteraction in this class already.
     * Make sure to super call the method first so your annotated methods get called.
     * <pre>{@code
     * super.onEntitySelectInteraction(event);
     * }</pre>
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @Repeatable(EntitySelectionResponses.class)
    public @interface EntitySelectionResponse {
        /**
         * Menu ID for the menu
         * @return menu ID
         */
        String value();
    }

    /**
     * Annotation to allow for multiple Entity selection responses on one method
     * @see EntitySelectionResponse
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface EntitySelectionResponses{ EntitySelectionResponse[] value(); }

    /**
     * Annotation for StringSelectInteractionEvent
     * Here is a code example of a string selection
     * <pre>{@code
     * StringSelectMenu.create("food_selection")
     *         .addOption("Strawberry", "strawberry")
     *         .addOption("Banana", "banana")
     *         .addOption("Pear", "pear")
     * .build();
     * }</pre>
     * and here is a code example of the methods that will get called when a user triggers that string selection
     * <pre>{@code
     * {@literal @}StringSelectionResponse("food_selection")
     * private void foodSelection(StringSelectInteractionEvent event){
     *     // TODO respond to event
     * }
     * }</pre>
     * You can also annotate your methods with the exact option that got picked by doing something like this
     * <pre>{@code
     * {@literal @}StringSelectionResponse(value = "food_selection", selectedOptionValue = "banana")
     * private void foodSelectionBanana(StringSelectInteractionEvent event){
     *     // TODO respond to event
     * }
     * }</pre>
     * If your annotated methods are not getting called, perhaps you overrode onStringSelectInteraction in this class already.
     * Make sure to super call the method first so your annotated methods get called.
     * <pre>{@code
     * super.onStringSelectInteraction(event);
     * }</pre>
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @Repeatable(StringSelectionResponses.class)
    public @interface StringSelectionResponse {
        /**
         * Menu id for the menu
         * @return menu Id
         */
        String value();

        /**
         * Optional, selected value of the menu
         * @return selected value
         */
        String selectedOptionValue() default "";
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface StringSelectionResponses{ StringSelectionResponse[] value(); }
}
