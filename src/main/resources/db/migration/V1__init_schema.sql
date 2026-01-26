-- =========================
-- USERS (ADMINS / MODERATORS / SUPPORT)
-- =========================
create table app_user
(
    id            bigserial primary key,
    username      varchar(50)  not null unique,
    email         varchar(255) not null unique,
    password_hash varchar(255) not null,
    role          varchar(30)  not null,
    created_at    timestamptz  not null default now(),

    constraint chk_user_role
        check (role in ('ADMIN', 'MODERATOR', 'SUPPORT'))
);

-- =========================
-- PLAYERS (IN-GAME USERS)
-- =========================
create table player
(
    id         bigserial primary key,
    username   varchar(50) not null unique,
    country    varchar(2),
    mmr        integer     not null default 1000,
    is_banned  boolean     not null default false,
    created_at timestamptz not null default now()
);

-- =========================
-- GAMES
-- =========================
create table game
(
    id         bigserial primary key,
    name       varchar(100) not null unique,
    genre      varchar(30)  not null,
    created_at timestamptz  not null default now(),

    constraint chk_game_genre
        check (genre in ('FPS', 'MOBA', 'RPG', 'STRATEGY', 'SPORTS'))
);

-- =========================
-- MATCHES
-- =========================
create table game_match
(
    id         bigserial primary key,
    game_id    bigint      not null,
    region     varchar(30) not null,
    status     varchar(30) not null,
    created_at timestamptz not null default now(),

    constraint fk_match_game
        foreign key (game_id)
            references game (id)
            on delete cascade,

    constraint chk_match_status
        check (status in ('CREATED', 'STARTED', 'FINISHED', 'CANCELLED'))
);

-- =========================
-- MATCH PLAYERS (JOIN TABLE)
-- =========================
create table match_player
(
    id        bigserial primary key,
    match_id  bigint  not null,
    player_id bigint  not null,
    team      varchar(20),
    score     integer not null default 0,

    constraint fk_mp_match
        foreign key (match_id)
            references game_match (id)
            on delete cascade,

    constraint fk_mp_player
        foreign key (player_id)
            references player (id)
            on delete cascade,

    constraint uq_match_player
        unique (match_id, player_id)
);

-- =========================
-- REPORTS (PLAYER REPORTING)
-- =========================
create table report
(
    id                 bigserial primary key,
    reported_player_id bigint       not null,
    reporter_player_id bigint       not null,
    reason             varchar(255) not null,
    status             varchar(30)  not null default 'PENDING',
    created_at         timestamptz  not null default now(),

    constraint fk_reported_player
        foreign key (reported_player_id)
            references player (id)
            on delete cascade,

    constraint fk_reporter_player
        foreign key (reporter_player_id)
            references player (id)
            on delete cascade,

    constraint chk_report_status
        check (status in ('PENDING', 'REVIEWED', 'REJECTED', 'BANNED'))
);

-- =========================
-- BANS
-- =========================
create table ban
(
    id                bigserial primary key,
    player_id         bigint       not null,
    reason            varchar(255) not null,
    banned_by_user_id bigint       not null,
    created_at        timestamptz  not null default now(),

    constraint fk_ban_player
        foreign key (player_id)
            references player (id)
            on delete cascade,

    constraint fk_ban_user
        foreign key (banned_by_user_id)
            references app_user (id)
            on delete restrict
);
