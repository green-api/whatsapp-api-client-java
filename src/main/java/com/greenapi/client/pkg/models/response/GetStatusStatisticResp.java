package com.greenapi.client.pkg.models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetStatusStatistic {
    private String id;
    private String name;
    private String contactName;
    private String type;
}
