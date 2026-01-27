package org.battleplugins.arena.module.betterteams;

import com.booksaw.betterTeams.Team;
import org.battleplugins.arena.Arena;
import org.battleplugins.arena.ArenaPlayer;
import org.battleplugins.arena.BattleArena;
import org.battleplugins.arena.competition.LiveCompetition;
import org.battleplugins.arena.competition.team.TeamManager;
import org.battleplugins.arena.event.player.ArenaJoinEvent;
import org.battleplugins.arena.options.TeamSelection;
import org.battleplugins.arena.options.Teams;
import org.battleplugins.arena.team.ArenaTeam;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

final class BetterTeamsListener implements Listener {

    @EventHandler
    public void onJoin(ArenaJoinEvent event) {
        if (!event.getArena().isModuleEnabled(BetterTeamsIntegration.ID)) {
            return;
        }

        ArenaPlayer arenaPlayer = event.getArenaPlayer();
        LiveCompetition<?> competition = arenaPlayer.getCompetition();
        Teams teams = competition.getArena().getTeams();
        if (teams.isNonTeamGame() || teams.getTeamSelection() != TeamSelection.RANDOM) {
            return;
        }

        if (this.isInTournament(event.getArena(), event.getPlayer())) {
            return;
        }

        Team betterTeam = Team.getTeam(event.getPlayer());
        if (betterTeam == null) {
            return;
        }

        Set<Player> onlineMembers = new HashSet<>(betterTeam.getOnlineMembers());
        if (onlineMembers.isEmpty()) {
            return;
        }

        TeamManager teamManager = competition.getTeamManager();
        ArenaTeam currentTeam = arenaPlayer.getTeam();
        int currentCount = countMembersOnTeam(teamManager, currentTeam, onlineMembers);

        ArenaTeam bestTeam = currentTeam;
        int bestCount = currentCount;
        for (ArenaTeam team : teamManager.getTeams()) {
            int count = countMembersOnTeam(teamManager, team, onlineMembers);
            if (count == 0) {
                continue;
            }

            if (team != currentTeam && !teamManager.canJoinTeam(team)) {
                continue;
            }

            if (count > bestCount) {
                bestCount = count;
                bestTeam = team;
            }
        }

        if (bestTeam != null && bestTeam != currentTeam) {
            teamManager.joinTeam(arenaPlayer, bestTeam);
        }
    }

    private static int countMembersOnTeam(TeamManager teamManager, ArenaTeam team, Set<Player> onlineMembers) {
        if (team == null) {
            return 0;
        }

        int count = 0;
        for (ArenaPlayer teamPlayer : teamManager.getPlayersOnTeam(team)) {
            if (onlineMembers.contains(teamPlayer.getPlayer())) {
                count++;
            }
        }

        return count;
    }

    private boolean isInTournament(Arena arena, Player player) {
        return BattleArena.getInstance()
                .module("tournaments")
                .map(container -> {
                    Object initializer = container.mainClass();
                    try {
                        Method getTournament = initializer.getClass().getMethod("getTournament", Arena.class);
                        Object tournament = getTournament.invoke(initializer, arena);
                        if (tournament == null) {
                            return false;
                        }

                        Method isInTournament = tournament.getClass().getMethod("isInTournament", Player.class);
                        return Boolean.TRUE.equals(isInTournament.invoke(tournament, player));
                    } catch (ReflectiveOperationException e) {
                        return false;
                    }
                })
                .orElse(false);
    }
}
