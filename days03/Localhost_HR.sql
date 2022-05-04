SELECT REPLACE(job_id, 'RE', '[RE]') job_id
        , REGEXP_REPLACE(job_title, '(RE|Re|rE|re)', '[\1]') job_title
FROM jobs
WHERE REGEXP_LIKE(job_id, 're', 'i') OR REGEXP_LIKE(job_title, 're', 'i');


select regexp_replace ('Kim Loves Cho','(.*) (.*) (.*)', '\3 \2 \1')   
from dual;
-- Cho Loves Kim

-------
select REGEXP_REPLACE(phone_number,
        '([[:digit:]]{3})\.([[:digit:]]{3})\.([[:digit:]]{4})',
        '(\1) \2-\3') "REGEXP_REPLACE"
from employees
order by "REGEXP_REPLACE";

-------
select REGEXP_REPLACE(country_name, '(.)','\1_') "REGEXP_REPLACE"
from countries;