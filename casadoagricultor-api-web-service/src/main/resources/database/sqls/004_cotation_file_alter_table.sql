ALTER TABLE cotation_file
ADD COLUMN from_day date 
AFTER `filename`;

UPDATE cotation_file
set from_day = substring(filename,1,10);