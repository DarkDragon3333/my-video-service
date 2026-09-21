CREATE EXTENSION IF NOT EXISTS btree_gist;

ALTER TABLE subscriptions ADD CONSTRAINT subscriptions_period_overlap_constraint
    EXCLUDE USING gist (user_id WITH =, tstzrange(date_begin, date_end) WITH &&);