#!/usr/bin/env bash

# call countries API
#$(bash get_countries.bash);

# call branches API
$(bash get_company_branches.bash);

QUERY_COUNTRY='INSERT INTO country(name) VALUES'
QUERY_BRANCHES='INSERT INTO business_stream(name) VALUES'

DML_COUNTRY=countries.sql
DML_BUSINESS_STREAM=business_stream.sql

# Set default values
CURRENT_DML=$DML_BUSINESS_STREAM
CURRENT_QUERY=$QUERY_BRANCHES

echo $CURRENT_QUERY > $CURRENT_DML

while read line; do
    echo "($line)," | tr \" \' >> $CURRENT_DML
done < data.txt

# Replace the last comma with a semicolon
sed -i '$s/,/;/' $CURRENT_DML

mv $CURRENT_DML ../sql/common/dml/$CURRENT_DML

rm data.txt