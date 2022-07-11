package com.mycompany.producerservice.service;

import com.mycompany.producerservice.event.News;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Component
public class RandomNewsGenerator {

    public News getRandomly() {
        return new News(UUID.randomUUID().toString(), TITLES.get(random.nextInt(TITLES.size())), ZonedDateTime.now());
    }

    private final Random random = new SecureRandom();

    private final static List<String> TITLES = List.of(
            "The life puzzle: The location of land on a planet can affect its habitability",
            "The solar system could collapse because of a passing star, scientists predict",
            "Space Glaciers: Carbon Dioxide Ice Forms Glaciers on Mars",
            "Kathryn Brown Joins Pernod Ricard USA to Lead Content Strategy and Activations for In House Creative Agency, The Mix",
            "How to Lose a Billion Dollars in the Metaverse and Other Mysteries of Web3",
            "Fresh Prep, LLC Debuts Fair Earth Farms, a Compostable, Sustainable New Line of Organic Salad Kits and Salad Blends with Exceptional Quality and Freshness",
            "India is about to surpass China as world’s most populous country, UN report finds",
            "The Number Ones: Jennifer Lopez’s “If You Had My Love”",
            "Webb Space Telescope: NASA reveals the list of its first 'cosmic targets'",
            "Amazon SodaStream deals go live with retro-style Art model at new $84.50 low (Up to 30% off)",
            "Being Salt of the Earth… And the Plight of Children of the World. Prof. Siegwart-Horst Günther",
            "As Twitter Shares Drop 5.3% in Premarket Trading, Wedbush Securities Analyst Says the Company is ‘in a Code Red Situation’",
            "CrossTower Partners with Ripple to support NFTs minted on the XRP Ledger",
            "India to surpass China as most populous country in 2023, UN report says",
            "CrossTower Partners with Ripple to support NFTs minted on the XRP Ledger",
            "Here's Everything Coming to Netflix the Week of July 11, 2022",
            "New Theory Offers Better Insight Into Earth's Formation",
            "Is Sentient AI Upon Us?",
            "Spooky Blue Remains Of An Ancient Cosmic Explosion Captured By NASA In New Image",
            "Il travel retail in ripresa fa i conti con il caos voli e i costi più alti",
            "NASA reveals the winning shots from its Photographer of the Year awards",
            "EPA needs a Senate-confirmed enforcement chief, groups say",
            "Market value alone is selling nature short, governments told - Reuters",
            "World View Assembles Space Flight Safety Experts from NASA, Virgin Galactic, and Blue Origin to Establish Safety Program and Technical Oversight Committee",
            "India to surpass China as most populous country in 2023: UN report",
            "Focus on short-term profits fuelling global biodiversity crisis, says UN report",
            "India to surpass China as most populous country in 2023: UN report",
            "NASA: A look at the new space telescope that should be sending images to Earth soon",
            "Somalia: Drought, Environmental Degradation, and Illusive Development",
            "Humans need to value nature as well as profits to survive, UN report finds",
            "Five signs that new pandemic consumer trends are sticking around",
            "How Climate Change Is Affecting Soil Microbiomes",
            "North Highland Goes North With Second UK Office Location",
            "Raver hinterlassen 135 Kubikmeter Müll für Berliner Stadtreinigung",
            "CPower to Acquire Centrica's US Demand Response Business - PR Newswire",
            "Has The Marketing Industry Really Gone Too Far With Social Purpose? - The Drum",
            "World population to hit 8 billion this year...",
            "The Pace of the Transition to an Environmentally Sustainable Economy",
            "India to surpass China as most populous country in 2023 -UN report",
            "Meghan McCain Takes Swipe At Elon Musk And Nick Cannon Exchange And Their ‘Creepy’ ‘Impregnate The Planet’ Mentality",
            "Marvel Confirms Scarlet Witch Was Supposed to Be the Next Thanos, Describes Her as “Best Villain Ever”"
    );
}
