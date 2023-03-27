ALTER TABLE query DROP COLUMN used_tokens;

ALTER TABLE query ADD COLUMN context_weight INT;

ALTER TABLE query ADD COLUMN weight INT;

ALTER TABLE query ADD COLUMN total_spent INT;