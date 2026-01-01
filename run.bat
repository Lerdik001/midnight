@echo off
echo Компиляция Java программ...

javac TodoApp.java
if %errorlevel% neq 0 (
    echo Ошибка компиляции TodoApp.java
    pause
    exit /b 1
)

javac Calculator.java
if %errorlevel% neq 0 (
    echo Ошибка компиляции Calculator.java
    pause
    exit /b 1
)

echo Компиляция завершена успешно!
echo.
echo Выберите программу для запуска:
echo 1. Todo App (Менеджер задач)
echo 2. Calculator (Калькулятор)
echo 3. Выход
echo.

:menu
set /p choice="Введите номер (1-3): "

if "%choice%"=="1" (
    echo Запуск Todo App...
    java TodoApp
    goto menu
) else if "%choice%"=="2" (
    echo Запуск Calculator...
    java Calculator
    goto menu
) else if "%choice%"=="3" (
    echo Выход...
    exit /b 0
) else (
    echo Неверный выбор. Попробуйте снова.
    goto menu
)