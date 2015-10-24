package barqsoft.footballscores;

import android.content.Context;
import android.content.res.Resources;

/**
 * Created by yehya khaled on 3/3/2015.
 */
public class Utilies {
    public static final int SERIE_A = 357;
    public static final int PREMIER_LEGAUE = 354;
    public static final int CHAMPIONS_LEAGUE = 362;
    public static final int CHAMPIONS_LEAGUE_NEW = 405;
    public static final int PRIMERA_DIVISION = 358;
    public static final int BUNDESLIGA = 351;


    public static String getLeague(final Context context, final int league_num) {
        switch (league_num) {
            case SERIE_A:
                return context.getString(R.string.league_seria_a);
            case PREMIER_LEGAUE:
                return context.getString(R.string.league_premier);
            case CHAMPIONS_LEAGUE_NEW:
            case CHAMPIONS_LEAGUE:
                return context.getString(R.string.league_champions);
            case PRIMERA_DIVISION:
                return context.getString(R.string.league_primera_division);
            case BUNDESLIGA:
                return context.getString(R.string.league_bundesliga);
            default:
                return context.getString(R.string.league_unknown);
        }
    }

    public static String getMatchDay(final Context context, int match_day, int league_num) {
        if (league_num == CHAMPIONS_LEAGUE) {
            if (match_day <= 6) {
                return context.getString(R.string.group_stage_text);
            } else if (match_day == 7 || match_day == 8) {
                return context.getString(R.string.first_knockout_round);
            } else if (match_day == 9 || match_day == 10) {
                return context.getString(R.string.quarter_final);
            } else if (match_day == 11 || match_day == 12) {
                return context.getString(R.string.semi_final);
            } else {
                return context.getString(R.string.final_text);
            }
        } else {
            return context.getString(R.string.matchday_text, match_day);
        }
    }

    public static String getScores(int home_goals, int awaygoals) {
        if (home_goals < 0 || awaygoals < 0) {
            return " - ";
        } else {
            return String.valueOf(home_goals) + " - " + String.valueOf(awaygoals);
        }
    }

    public static int getTeamCrestByTeamName(final Context context, final String teamname) {
        if (teamname == null) {
            return R.drawable.no_icon;
        }
        if (teamname.equals(context.getString(R.string.team_arsenal_london_fc))) {
            return R.drawable.arsenal;
        } else if (teamname.equals(context.getString(R.string.team_manchester_united_fc))) {
            return R.drawable.manchester_united;
        } else if (teamname.equals(context.getString(R.string.team_swansea_city))) {
            return R.drawable.swansea_city_afc;
        } else if (teamname.equals(context.getString(R.string.team_leicester_city))) {
            return R.drawable.leicester_city_fc_hd_logo;
        } else if (teamname.equals(context.getString(R.string.team_everton_fc))) {
            return R.drawable.everton_fc_logo1;
        } else if (teamname.equals(context.getString(R.string.team_west_ham_united_fc))) {
            return R.drawable.west_ham;
        } else if (teamname.equals(context.getString(R.string.team_tottenham_hotspur_fc))) {
            return R.drawable.tottenham_hotspur;
        } else if (teamname.equals(context.getString(R.string.team_west_bromwich_albion))) {
            return R.drawable.west_bromwich_albion_hd_logo;
        } else if (teamname.equals(context.getString(R.string.team_sunderland_afc))) {
            return R.drawable.sunderland;
        } else if (teamname.equals(context.getString(R.string.team_stoke_city_fc))) {
            return R.drawable.stoke_city;
        } else {
            return R.drawable.no_icon;
        }
    }
}
