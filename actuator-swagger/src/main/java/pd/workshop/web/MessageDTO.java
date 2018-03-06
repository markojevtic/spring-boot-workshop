package pd.workshop.web;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Greeting", description = "Represents a message.")
public class MessageDTO {

    @ApiModelProperty(value = "Message text, etc...", example = "Servus!")
    private final String text;

    public MessageDTO(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
