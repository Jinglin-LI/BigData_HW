#!/bin/bash
# assignment0.sh

wget https://www.gutenberg.org/files/98/98-0.txt
cat 98-0.txt | tr -d '[:punct:]' | tr -cs "[a-z][A-Z]" "[\012*]"| tr 'A-Z' 'a-z' | tr -s ' ' | tr -s '\n'| tr ' ' '\n' | sort | uniq -c | sort -rn
cat 98-0.txt | tr -d '[:punct:]' | tr -cs "[a-z][A-Z]" "[\012*]"| tr 'A-Z' 'a-z' | tr -s ' ' | tr -s '\n'| tr ' ' '\n' | sort | uniq -c | sort -rn > jxl163530Part1.txt





