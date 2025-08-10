package ngordnet.proj2b_testing;

import ngordnet.browser.NgordnetQuery;
import ngordnet.browser.NgordnetQueryHandler;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;

/** Tests the most basic case for Hyponyms where the list of words is one word long, and k = 0.*/
public class TestOneWordK0Hyponyms {
    // this case doesn't use the NGrams dataset at all, so the choice of files is irrelevant

    //public static final String WORDS_FILE = "data/ngrams/very_short.csv";
    //public static final String TOTAL_COUNTS_FILE = "data/ngrams/total_counts.csv";
    //public static final String SMALL_SYNSET_FILE = "data/wordnet/synsets16.txt";
    //public static final String SMALL_HYPONYM_FILE = "data/wordnet/hyponyms16.txt";
    public static final String WORDS_FILE = "data/ngrams/top_14377_words.csv";
    public static final String TOTAL_COUNTS_FILE = "data/ngrams/total_counts.csv";
    public static final String SMALL_SYNSET_FILE = "data/wordnet/synsets.txt";
    public static final String SMALL_HYPONYM_FILE = "data/wordnet/hyponyms.txt";

    @Test
    public void testActK0() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                WORDS_FILE, TOTAL_COUNTS_FILE, SMALL_SYNSET_FILE, SMALL_HYPONYM_FILE);
        List<String> words = List.of("region");

        NgordnetQuery nq = new NgordnetQuery(words, 2012, 2014, 3);
        String actual = studentHandler.handle(nq);
        String expected = "['hood, 's_Gravenhage, A-horizon, AK, AL, AR, AS, AZ, A_horizon, Aachen, Aalborg, Aalst, Aarhus, Abadan, Aberdare, Aberdeen, Abidjan, Abilene, Abkhaz, Abkhazia, Abruzzi, Abruzzi_e_Molise, Abu_Dhabi, Abuja, Abydos, Abyssinia, Acadia, Acadia_National_Park, Acapulco, Acapulco_de_Juarez, Accho, Accra, Achaea, Acragas, Acre, Actium, Adalia, Adana, Addis_Ababa, Adelaide, Adelie_Coast, Adelie_Land, Aden, Adrianople, Adrianopolis, Adzhar, Adzharia, Afars_and_Issas, Afghanistan, African_country, African_nation, Agra, Agrigento, Aix-la-Chapelle, Akaba, Aken, Akka, Akko, Akmola, Akron, Al-Hudaydah, Al-Iraq, Al-Magrib, Al-Mukalla, Al_Aqabah, Al_Ladhiqiyah, Al_Madinah, Al_Qahira, Ala., Alabama, Alaska, Albania, Albanian_capital, Albany, Alberta, Albion, Alborg, Albuquerque, Alep, Aleppo, Alexandria, Algeria, Algerian_capital, Algerie, Algiers, Allentown, Alma-Ata, Almaty, Aloha_State, Alpena, Alsace, Alsatia, Altoona, Amarillo, America, American_Samoa, American_Virgin_Islands, American_capital, American_state, Amman, Amsterdam, An_Nafud, An_Nefud, Anaheim, Anchorage, Andalucia, Andalusia, Andhra_Pradesh, Andorra, Anglia, Angola, Angolan_capital, Angora, Anjou, Ankara, Ann_Arbor, Annaba, Annam, Annapolis, Antakiya, Antakya, Antalya, Antananarivo, Antarctic, Antarctic_Zone, Antigua_and_Barbuda, Antioch, Antofagasta, Antwerp, Antwerpen, Anvers, Anzio, Apeldoorn, Apia, Appalachia, Appleton, Appleton_layer, Apulia, Aqaba, Aquarius, Aquarius_the_Water_Bearer, Aquila, Aquila_degli_Abruzzi, Aquitaine, Aquitania, Ar_Rimsal, Arab_Republic_of_Egypt, Arabian_Desert, Aragon, Aram, Arcadia, Archer, Arches_National_Park, Arctic, Arctic_Zone, Arequipa, Argentina, Argentine_Republic, Argos, Arhus, Ariana, Aries, Aries_the_Ram, Arizona, Ark., Arkansas, Arlington, Armageddon, Armenia, Arnhem, Artois, Asahikawa, Asheville, Ashkhabad, Ashur, Asian_Russia, Asian_country, Asian_nation, Asmara, Asmera, Aspadana, Aspinwall, Assam, Assouan, Assuan, Assur, Assyria, Astana, Astrakhan, Asuncion, Asur, Aswan, Atacama_Desert, Athens, Athinai, Athos, Atlanta, Atlantic_City, Attica, Auckland, Augusta, Austerlitz, Austin, Australia, Australian_Desert, Australian_capital, Australian_state, Austria, Austria-Hungary, Austrian_capital, Auvergne, Avignon, Avon, Ayr, Az_Zarqa, Azerbaijan, Azerbaijani_Republic, Azerbajdzhan, Azerbajdzhan_Republic, B-horizon, B_horizon, Babylon, Babylonia, Bad_Lands, Badger_State, Badlands, Badlands_National_Park, Bagdad, Baghdad, Bahama_Islands, Bahamas, Bahia_Blanca, Bahrain, Bahrein, Bairiki, Bakersfield, Baku, Balance, Bale, [...(truncated)...]eastern_United_States, southern_hemisphere, southland, southwestern_United_States, spa, space, spear-point, spearhead, spearpoint, sphere, sphere_of_influence, spike, spike_heel, sprawl, square, squash_court, stacked_heel, stage, staging_area, stamping_ground, star_sign, state, state_boundary, state_capital, state_line, stiletto_heel, stockbroker_belt, storm_center, storm_centre, stratosphere, stratum, stratum_basale, stratum_corneum, stratum_germinativum, stratum_granulosum, stratum_lucidum, striate_area, striate_cortex, strike_zone, subdivision, substrate, substratum, subtopia, subtropics, suburb, suburban_area, suburbia, sultanate, summer_camp, sunken_garden, superstrate, superstratum, surface, surround, surroundings, suzerainty, swap_file, swap_space, tank_farm, tape, tax_haven, tea_garden, tee, teeing_ground, telomere, tenderloin, tenement_district, tennis_court, terminal, termination, terminus, terra_incognita, terrain, terreplein, territorial_division, territorial_dominion, territory, testing_ground, the_City, the_States, the_pits, theater, theater_of_operations, theater_of_war, theatre, theatre_of_operations, theatre_of_war, theme_park, thenar, thermosphere, thick, three-mile_limit, tidal_zone, tiltyard, time_zone, tip, tiptoe, tiptop, toll_plaza, tonsure, top, top_side, topiary, town, township, toxic_dumpsite, toxic_site, toxic_waste_area, toxic_waste_dump, toxic_waste_site, tract, transit_zone, trash_dump, trash_heap, trash_pile, treetop, tropical_zone, tropics, tropopause, troposphere, trouble_spot, trust_territory, trusteeship, tulip_bed, turf, turnip_bed, ultima_Thule, underbelly, underside, undersurface, unknown, unknown_region, upper_limit, upper_mantle, upper_side, upside, uptown, urban_area, urban_center, urban_sprawl, used-car_lot, vacant_lot, vacation_spot, vacuity, vacuum, vault_of_heaven, vegetable_garden, vegetable_patch, veld, veldt, venue, verge, vertex, viceroyalty, vicinity, victory_garden, view, village, village_green, viscounty, visual_area, visual_cortex, volleyball_court, voting_precinct, waist, waistline, wall, war_zone, ward, warren, wasp_waist, waste, waste-yard, wasteland, wasteyard, waterfront, watering_hole, watering_place, watershed, wave_front, wavefront, wayside, weald, wedge, wedge_heel, welkin, west_side, western_United_States, western_hemisphere, wheat_field, wheatfield, wild, wilderness, windward, wineglass_heel, winner's_circle, wire, wold, woodlet, workspace, yard, yardarm, yellow_spot, zodiac, zone, zone_of_interior]";
        assertThat(actual).isEqualTo(expected);
    }

    // TODO: Add more unit tests (including edge case tests) here.
}
