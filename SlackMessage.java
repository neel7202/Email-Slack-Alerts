package com.alertslambda.objects;

import java.io.Serializable;
import lombok.*;

@AllArgsConstructor
@Builder(builderClassName = "Builder")
@Getter

public class SlackMessage implements Serializable {
    private String username;
    private String text;
}
