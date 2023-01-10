import com.zgamelogic.AdvancedListenerAdapter;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.EntitySelectInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.events.message.react.GenericMessageReactionEvent;

public class TestListener extends AdvancedListenerAdapter {

    @ButtonResponses({
            @ButtonResponse("test1"),
            @ButtonResponse("test2")
    })
    private void buttonMethod(ButtonInteractionEvent event){}

    @AutoCompleteResponse(slashCommandId = "test", focusedOption = "o1")
    private void autoCompleteResponseMethod(CommandAutoCompleteInteractionEvent event){}

    @UserInteractionResponse("test")
    private void userInteractionResponseMethod(UserContextInteractionEvent event){}

    @MessageInteractionResponse("test")
    private void messageInteractionResponseMethod(MessageContextInteractionEvent event){}

    @EmoteResponse(value = "", isAdding = true)
    private void messageEmoteResponseMethod(GenericMessageReactionEvent event){}

    @ModalResponse("test")
    private void modalResponseMethod(ModalInteractionEvent event){}

    @SlashResponse("test")
    private void slashResponseMethod(SlashCommandInteractionEvent event){}

    @EntitySelectionResponse("test")
    private void entitySelectionResponseMethod(EntitySelectInteractionEvent event){}

    @StringSelectionResponse("test")
    private void stringSelectionResponseMethod(StringSelectInteractionEvent event){}
}
