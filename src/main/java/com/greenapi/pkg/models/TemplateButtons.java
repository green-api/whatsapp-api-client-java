package com.greenapi.pkg.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TemplateButtons {
    private Integer index;
    private UrlButton urlButton;
    private CallButton callButton;
    private QuickReplyButton quickReplyButton;
}
