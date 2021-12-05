do language plpgsql $$
declare
    counter integer := 0;
begin
    while counter < 1000
    loop
        counter := counter + 1;
        insert into produced_entity (name, date_time)
            values (format('producer%s', counter), current_timestamp - counter * interval '1 second');
    end loop;
end;
$$;

