-- =========================
-- INDEXES
-- =========================

create index idx_game_match_game_id on game_match (game_id);
create index idx_game_match_status on game_match (status);
create index idx_match_player_match_id on match_player (match_id);
create index idx_match_player_player_id on match_player (player_id);
create index idx_report_reported_player on report (reported_player_id);
create index idx_report_status on report (status);
create index idx_ban_player_id on ban (player_id);
