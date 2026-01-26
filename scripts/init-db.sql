-- Create the gameadmin database
-- Run this script as a PostgreSQL superuser (e.g., postgres)

-- Drop database if it exists (optional - uncomment if needed)
-- DROP DATABASE IF EXISTS gameadmin;

-- Create the database
CREATE DATABASE gameadmin
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.UTF-8'
    LC_CTYPE = 'en_US.UTF-8'
    TEMPLATE = template0
    CONNECTION LIMIT = -1;

-- Connect to the new database
\c gameadmin

-- =========================
-- USERS (ADMINS / MODERATORS / SUPPORT)
-- =========================
CREATE TABLE app_user (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(30) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

    CONSTRAINT chk_user_role
        CHECK (role IN ('ADMIN', 'MODERATOR', 'SUPPORT'))
);

-- =========================
-- PLAYERS (IN-GAME USERS)
-- =========================
CREATE TABLE player (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    country VARCHAR(2),
    mmr INTEGER NOT NULL DEFAULT 1000,
    is_banned BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- =========================
-- GAMES
-- =========================
CREATE TABLE game (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    genre VARCHAR(30) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

    CONSTRAINT chk_game_genre
        CHECK (genre IN ('FPS', 'MOBA', 'RPG', 'STRATEGY', 'SPORTS'))
);

-- =========================
-- MATCHES
-- =========================
CREATE TABLE game_match (
    id BIGSERIAL PRIMARY KEY,
    game_id BIGINT NOT NULL,
    region VARCHAR(30) NOT NULL,
    status VARCHAR(30) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

    CONSTRAINT fk_match_game
        FOREIGN KEY (game_id)
        REFERENCES game(id)
        ON DELETE CASCADE,

    CONSTRAINT chk_match_status
        CHECK (status IN ('CREATED', 'STARTED', 'FINISHED', 'CANCELLED'))
);

-- =========================
-- MATCH PLAYERS (JOIN TABLE)
-- =========================
CREATE TABLE match_player (
    id BIGSERIAL PRIMARY KEY,
    match_id BIGINT NOT NULL,
    player_id BIGINT NOT NULL,
    team VARCHAR(20),
    score INTEGER NOT NULL DEFAULT 0,

    CONSTRAINT fk_mp_match
        FOREIGN KEY (match_id)
        REFERENCES game_match(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_mp_player
        FOREIGN KEY (player_id)
        REFERENCES player(id)
        ON DELETE CASCADE,

    CONSTRAINT uq_match_player
        UNIQUE (match_id, player_id)
);

-- =========================
-- REPORTS (PLAYER REPORTING)
-- =========================
CREATE TABLE report (
    id BIGSERIAL PRIMARY KEY,
    reported_player_id BIGINT NOT NULL,
    reporter_player_id BIGINT NOT NULL,
    reason VARCHAR(255) NOT NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'PENDING',
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

    CONSTRAINT fk_reported_player
        FOREIGN KEY (reported_player_id)
        REFERENCES player(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_reporter_player
        FOREIGN KEY (reporter_player_id)
        REFERENCES player(id)
        ON DELETE CASCADE,

    CONSTRAINT chk_report_status
        CHECK (status IN ('PENDING', 'REVIEWED', 'REJECTED', 'BANNED'))
);

-- =========================
-- BANS
-- =========================
CREATE TABLE ban (
    id BIGSERIAL PRIMARY KEY,
    player_id BIGINT NOT NULL,
    reason VARCHAR(255) NOT NULL,
    banned_by_user_id BIGINT NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

    CONSTRAINT fk_ban_player
        FOREIGN KEY (player_id)
        REFERENCES player(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_ban_user
        FOREIGN KEY (banned_by_user_id)
        REFERENCES app_user(id)
        ON DELETE RESTRICT
);

-- =========================
-- INDEXES
-- =========================
CREATE INDEX idx_player_username ON player(username);
CREATE INDEX idx_player_is_banned ON player(is_banned);
CREATE INDEX idx_game_match_game_id ON game_match(game_id);
CREATE INDEX idx_game_match_status ON game_match(status);
CREATE INDEX idx_match_player_match_id ON match_player(match_id);
CREATE INDEX idx_match_player_player_id ON match_player(player_id);
CREATE INDEX idx_report_status ON report(status);
CREATE INDEX idx_report_reported_player ON report(reported_player_id);
CREATE INDEX idx_ban_player_id ON ban(player_id);
