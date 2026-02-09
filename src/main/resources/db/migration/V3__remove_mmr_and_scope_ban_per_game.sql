alter table player drop column mmr;
alter table player drop column is_banned;
alter table ban add column game_id bigint not null default 0;

alter table ban
    add constraint fk_ban_game
        foreign key (game_id)
            references game (id)
            on delete cascade;

-- game_id needs to be set now 
alter table ban alter column game_id drop default;

-- one active ban per player per game
alter table ban
    add constraint uq_ban_player_game
        unique (player_id, game_id);

create index idx_ban_game_id on ban (game_id);
