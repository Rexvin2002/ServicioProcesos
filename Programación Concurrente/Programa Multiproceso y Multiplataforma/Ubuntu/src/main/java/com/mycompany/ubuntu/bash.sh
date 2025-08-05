#!/bin/bash

# Parámetros:
# $1 = directorio
# $2 = patrón (expresión regular)
# $3 = nuevo nombre

function rename_files() {
    local dir="$1"
    local pattern="$2"
    local new_name="$3"
    local counter=1
    local log_file="rename_log.txt"

    # Limpiar el archivo de log
    > "$log_file"

    # Función para verificar si el nombre ya existe
    check_name() {
        local base_name="$1"
        local extension="$2"
        local num="$3"
        local test_name="${base_name}${num}${extension}"
        while [[ -e "$dir/$test_name" ]]; do
            num=$((num + 1))
            test_name="${base_name}${num}${extension}"
        done
        echo "$test_name"
    }

    # Encontrar archivos recursivamente y procesarlos
    find "$dir" -type f | while read -r file; do
        filename=$(basename "$file")
        if [[ $filename =~ $pattern ]]; then
            directory=$(dirname "$file")
            extension="${filename##*.}"
            
            # Generar nuevo nombre único
            new_filename=$(check_name "$new_name" ".$extension" "$counter")
            
            # Renombrar y registrar
            mv "$file" "$directory/$new_filename"
            echo "RENAMED:$file:$directory/$new_filename" >> "$log_file"
            counter=$((counter + 1))
        fi
    done

    # Guardar el contador final
    echo "TOTAL:$((counter - 1))" >> "$log_file"
}

# Verificar argumentos
if [ $# -ne 3 ]; then
    echo "Uso: $0 <directorio> <patrón> <nuevo_nombre>"
    exit 1
fi

rename_files "$1" "$2" "$3"