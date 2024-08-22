#!/bin/bash

# Скрипт для сравнения пакетов между двумя ветками

# Функция для вывода справки
usage() {
    echo "Использование: $0 <ветка1> <ветка2>"
    echo "Пример: $0 sisyphus p10"
    exit 1
}

# Проверка наличия обязательных аргументов
if [ "$#" -ne 2 ]; then
    echo "Ошибка: Некорректное количество аргументов."
    usage
fi

branch1=$1
branch2=$2

# Список допустимых веток
valid_branches=("sisyphus" "p9" "p10" "p11")

# Проверка на допустимость веток
if [[ ! " ${valid_branches[@]} " =~ " ${branch1} " ]] || [[ ! " ${valid_branches[@]} " =~ " ${branch2} " ]]; then
    echo "Ошибка: Неверные ветки."
    echo "Допустимые ветки: ${valid_branches[*]}"
    exit 1
fi

# Запуск Java программы
echo "Запуск сравнения пакетов между ветками $branch1 и $branch2..."
java -cp "bin:lib/java-json.jar" com.example.Main "$branch1" "$branch2"

# Проверяем код завершения последней команды
if [ $? -eq 0 ]; then
    echo "Сравнение завершено успешно."
else
    echo "Произошла ошибка при выполнении программы."
fi
