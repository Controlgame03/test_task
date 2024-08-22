#!/bin/bash

# Скрипт для компиляции Java файлов в проекте

# Проверяем существование папки src
if [ ! -d "src" ]; then
    echo "Ошибка: Папка 'src' не найдена."
    exit 1
fi

# Компилируем файлы с добавлением lib/java-json.jar в classpath
javac -cp "lib/java-json.jar" -d bin src/com/example/*.java

# Проверяем код завершения последней команды
if [ $? -eq 0 ]; then
    echo "Компиляция завершена успешно."
else
    echo "Произошла ошибка при компиляции."
fi
