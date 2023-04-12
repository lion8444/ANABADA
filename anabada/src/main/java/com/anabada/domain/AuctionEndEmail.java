package com.anabada.domain;

import lombok.Data;

@Data
public class AuctionEndEmail {
    String auction_id;
    String title;
    String content;
    String file;
    String seller;
    String buyer;
    String finish;
}
