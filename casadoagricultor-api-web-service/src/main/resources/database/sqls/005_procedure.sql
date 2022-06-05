USE casa_do_agricultor;
DELIMITER ;;
DROP PROCEDURE IF EXISTS closest_cotation_day_of;;
CREATE PROCEDURE closest_cotation_day_of(IN desired_date DATE, OUT real_date DATE)
	SQL SECURITY INVOKER
BEGIN
	set real_date = 
    (select final_day from (
	SELECT from_day as final_day, abs(datediff(desired_date,from_day))  as distance_from_desired_day 
		from cotation_file where successfully_processed = 1
		order by distance_from_desired_day asc limit 1
	) AS AUXILIAR_TABLE
    );
END;;
DELIMITER ;