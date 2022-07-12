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
            "Air defence for Qatar soccer stadiums a boon for Swiss arms exports - Reuters.com",
            "Mount Pearl Municipal Workers Strike Enters Sixth Day - VOCM",
            "Kelley Blue Book: Not ready for an electric car? These future models could make you change your mind.",
            "Red Star FC Recruits Lack of Guidance for '90s-Inspired 2022/23 Kits",
            "Lack of Guidance Works Its Magic For Red Star FC 2022/23",
            "The Theragun Mini is the travel power move you're missing — get it for 20% off during Prime Day",
            "Jets' Jeremy Ruckert gets a big green homecoming, 21 years in the making",
            "Gareth Bale says he’s at LAFC to win trophies, not to retire",
            "Coach Priestman challenges Canadian women's soccer team to be better as it moves to CONCACAF semifinals",
            "Canadian women's soccer team blanks Costa Rica, takes top spot in Group B at CONCACAF Championship",
            "59 Years After The Equal Pay Act, Women Still Struggle For The Bare Minimum",
            "Bale: \"La Mls non è per pensionati, è un campionato ricco di insidie\"",
            "Chuyên gia Indonesia: 'Việt Nam, Thái Lan chẳng làm gì sai'",
            "Wayne Rooney agrees to coach DC United",
            "Southern Rep Theatre In New Orleans Closes Permanently",
            "Syntactic Foam Market: Market Segments: By Matrix Type ; By Form ; By End User ; and Region – Global Analysis of Market Size, Share & Trends for 2014– 2020 and Forecasts to 2030",
            "North American Footwear Market Report 2022-2031: Key Market Dynamics, Players and Trends",
            "No Fun Allowed! How the Left Became the Fun Police.",
            "Neues Quartett für Aufsteiger Erfurt",
            "Midnight Mania! McGregor tells Paul to move along",
            "Bill Burr Rips ‘Feminists’ For Failing The WNBA",
            "Ja Morant interview leads to spoof quote about Michael Jordan on ESPN - Commercial Appeal",
            "DAZN: Galibier y Alpe d´Huez preparan emociones fuertes",
            "Gillette Stadium Scores AI Weapons Detection Systems From Evolv Technology",
            "NASCAR and SeatGeek Enter Multi-Year Official Partnership",
            "USWNT gets ugly win in Mexico, plus why Tiger Woods' last go at St. Andrews is such a big deal",
            "元マンUのMFナニがメルボルン・V加入を発表！　「挑戦を楽しみにしている」",
            "レヴァンドフスキ、バイエルンの練習場に出現…バルサ行き希望は相変わらずか",
            "チェルシー、ニャブリ獲得を検討？　ハフィーニャ獲得失敗の次善の策か",
            "Concacaf W Championship: USA vs. Mexico - Lineup, Schedule & TV Channels - U.S. Soccer",
            "Former Super Bowl champion Osi Umenyiora is creating a new path for African talent to make it to the NFL",
            "Laura Freigang, parole libérée",
            "How Britan and Germany helped North Korea develop its national beer",
            "39 anticipated kids graphic novels for summer 2022",
            "HaBO: He Wants Her to Use a Pseudonym",
            "Coleen Rooney enjoys post-workout green choice as she leaves Cheshire gym",
            "Characteristics of total body and appendicular bone mineral content and density in Japanese collegiate Sumo wrestlers",
            "UEFA says rival football Super League plan is a ‘textbook cartel’",
            "Prince George's Suit Echoes 'Bizarre Outfits' Harry Laughed About Wearing",
            "MLB Unveils 2022 All-Star Game Jerseys",
            "Top football referee Igor Benevenuto comes out as gay ahead of World Cup",
            "Every League Two club's record signing from £3m dud to tragic Joey Beauchamp",
            "J1福岡、MF杉本太郎がJ2徳島に完全移籍！4年ぶりの復帰「とても楽しみにしています」（関連まとめ）",
            "東京V、7名が新型コロナ陽性で13日までトップチームの活動停止に…13日の天皇杯磐田戦は20日に延期",
            "横浜FM樺山諒乃介、J2山形に2度目の育成型期限付き移籍「今度こそ山形の力になり、J1昇格のために全力を尽くします」",
            "清水、加入確実のMFピカチュウが来週にも来日か？移籍金1.4億円、年俸2.3億円の見通し（関連まとめ）",
            "You Can Now Be A Shark In Nintendo Switch Sports",
            "The Rush: Morant claims he would ‘cook’ Jordan, beat Messi in soccer shootout",
            "Mewis scores late, US women top Mexico 1-0 at W Championship",
            "Max Norris: 5 Things About Chuck Norris’ 22-Year-Old Grandson On ‘Claim To Fame’",
            "News24.com | 'I wanted to go into the hole and save him' - Khayalethu Magadla's friend, 8, on last moments",
            "UFC midyear awards -- The best male and female fighter, finish, prospect and more",
            "How to Save College Football",
            "Juventus in tournée negli USA: dove e quando seguire le partite in tv",
            "Simone Biles’s inspires other stars to go public on mental health",
            "Bale hat sich in Los Angeles viel vorgenommen",
            "◆悲報◆ムバッペやネイマールは？PSG7選手の日本ツアー不参加が決定か",
            "ESPN host apologizes after airing fake Ja Morant quote about Michael Jordan",
            "Air defence for Qatar soccer stadiums a boon for Swiss arms exports",
            "Premundial Concacaf: Alex Morgan, la bicampeona mundial y casada con un mexicano",
            "Former Manchester United winger Nani joins Melbourne Victory on a two-year contract",
            "Man United fans fume at Paul Pogba after he declares himself 'back like I have never left'",
            "Hernández: Gareth Bale looks to rewrite his story with LAFC",
            "Analysis: USWNT keeps momentum going at CONCACAF W Championship but wasn't easy against Mexico",
            "Andonovski: USWNT 'Probably Not Ready' to Compete in World Cup Yet Despite Mexico Win",
            "How Britan and Germany helped North Korea develop its national beer",
            "Premier League transfer news live, today! Latest updates on the summer window",
            "Transfernieuws: Willem II haalt Kostas Lamprou terug",
            "Transfernieuws: Portugees Nani sluit voetballoopbaan af in Australië",
            "Perplexing goal-line play during CPL match in Winnipeg a buzz within soccer community",
            "See gorgeous photos from T.J. Watt and Dani Rhodes' wedding",
            "Ranbir: My wife makes me the happiest",
            "Former soccer player Dani Rhodes and T.J. Watt of Steelers get married in resort wedding in Mexico",
            "PSGの日本ツアー、メッシ･ネイマール・エムバペが揃って来ない可能性、ある？www",
            "【画像】ガンバ大阪さん、世界的BIGクラブの仲間入りwww",
            "20 Fun Boho Crafts to Make This Summer",
            "Bale sets sights on a long-term stay with LAFC",
            "Wayne Rooney making mistake in quest to one day become Man Utd manager",
            "How the Packers Offense Played In Each Game Without Davante Adams",
            "Estrella Damm Barcelona Trip Giveaway ~ 09/05/2022",
            "Neo Studios, Roma Press explore Diego Maradona’s legacy in four-part doc",
            "UEFA says rival Super League a textbook cartel",
            "A lot happening in women's soccer",
            "Iran fires coach Skocic 4 months out from soccer World Cup",
            "How the Islamic Soccer League became a field of opportunity for Muslim families in the GTA",
            "Spain midfielder Rodri signs 3-year extension with Man City",
            "Canada beats Costa Rica 2-0, tops group at W Championship",
            "Mewis scores late, US women top Mexico 1-0 at W Championship",
            "Fomer Man Utd forward Nani joins Melbourne Victory",
            "Why Being Famous Won't Sell Your Product",
            "The Ins and Outs of Building a Community w/ Adam22",
            "Taika Waititi would love to annoy fans with another Thor movie",
            "Gareth Bale targets Euro 2024 and beyond after MLS move",
            "A unique opportunity for Irish rugby; Cian Lynch’s timely return for Limerick",
            "Romeo Beckham splits from girlfriend Mia Regan after three years together",
            "Video: Montaigne shows off her soccer skills on Spicks and Specks return",
            "City SC signs a young and hungry teenage winger from Denmark",
            "ディ・マリア、ユヴェントス加入を決断した理由明かす「当然の帰結と言える」"
    );
}
