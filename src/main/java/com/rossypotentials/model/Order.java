package com.rossypotentials.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data


public class Order extends Product{
    private int orderId;
    private int uId;
    private int quantity;
    private String date;

}
