package com.project.ZTI.response;

import com.project.ZTI.model.user.User;
import lombok.*;


//@Getter
//@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RestaurantRateByUsersResponse {
    private String username;
    private String comment;
    private int rate;
}
