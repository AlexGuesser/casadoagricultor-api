SELECT * FROM `casa-do-agricultor.casa_do_agricultor.cotations_history` LIMIT 1000;

select ch.*,
case
	when type = 'Convenci' then 'Convencional'
    when type = 'Organico' then 'Orgânico'
    else type
end
as type_adjusted
from `casa-do-agricultor.casa_do_agricultor.cotations_history` ch;

select * from `casa-do-agricultor.casa_do_agricultor.cotations_history_adjusted` ;

select ch.*,
IF(type_adjusted = 'Orgânico',CONCAT(name,IF(classification IS NOT NULL, CONCAT(" ", classification), "" ),IF(type_adjusted IS NOT NULL, CONCAT(" ",type_adjusted), "" ), IF(origin = "Importado", CONCAT(" ", origin), "" ) ), CONCAT(name, IF(classification IS NOT NULL, CONCAT(" ", classification), "" ), IF(origin = "Importado", CONCAT(" ", origin), "" ) ) ) as specific_product
from `casa-do-agricultor.casa_do_agricultor.cotations_history_adjusted` ch;