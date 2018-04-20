package com.code10.isa.service;


import com.code10.isa.model.Bid;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "test")
public class BidServiceTest {

    @Autowired
    private BidService bidService;

    @Autowired
    private OfferService offerService;

    private static final long EXISTING_OFFER_ID = 1;

    @Test
    public void createBidWithValidDataShouldReturnCreatedBid() {
        Bid bid = new Bid(null, 20, new Date(), null);
        Bid createdBid = bidService.create(bid, offerService.findById(EXISTING_OFFER_ID));
        assertThat(createdBid).isNotNull();
    }
}
