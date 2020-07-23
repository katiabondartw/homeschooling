if [ -z "$1" ]
then
    echo "Hello Dear Teacher,"
    echo "Please give us an assignment in the format \"A:1,B:1,D:2,C:2.\""
    echo "Re-run this script with arguments:"
    echo "./go \"A:1,B:1,D:2,C:2\""
else
    ./gradlew run --args=$1
fi
