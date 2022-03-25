#!/usr/bin/env bash

$(bash get_countries.bash);

echo 'INSERT INTO country(name) VALUES' > countries.sql;

while read line; do
    echo "($line)," | tr \" \' >> countries.sql;
done < countries.txt;

# Replace the last comma with a semicolon
sed -i '$s/,/;/' countries.sql;

mv countries.sql ../sql/common/dml/insert_countries.sql;

rm countries.txt;