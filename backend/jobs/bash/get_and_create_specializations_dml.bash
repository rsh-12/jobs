#!/usr/bin/env bash

# Get IT specializations (first element in array: .[0].specializations[].name)
API=https://api.hh.ru/specializations?locale=EN
CURRENT_DML=specializations.sql

echo 'INSERT INTO specialization(name) VALUES' > $CURRENT_DML

curl $API | jq .[0].specializations[].name > data.txt

while read line; do
    echo "($line)," | tr \" \' >> $CURRENT_DML
done < data.txt

# Replace the last comma with a semicolon
sed -i '$s/,/;/' $CURRENT_DML

mv $CURRENT_DML ../sql/common/dml/data/$CURRENT_DML

rm data.txt