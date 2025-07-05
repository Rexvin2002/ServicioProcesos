#!/bin/bash

# Argumentos
folderPath="$1"
regex="$2"
newName="$3"

if [ ! -d "$folderPath" ]; then
  echo "Folder does not exist."
  exit 1
fi

counter=1

# Recorre los archivos en la carpeta recursivamente
find "$folderPath" -type f | while read -r file; do
  fileName=$(basename "$file")
  if [[ "$fileName" =~ $regex ]]; then
    extension="${fileName##*.}"
    newFile="$folderPath/$newName_$counter.$extension"
    
    # Evita sobrescrituras
    while [ -e "$newFile" ]; do
      counter=$((counter + 1))
      newFile="$folderPath/$newName_$counter.$extension"
    done

    mv "$file" "$newFile" && echo "Renamed $file -> $newFile"
    counter=$((counter + 1))
  fi
done

exit 0
