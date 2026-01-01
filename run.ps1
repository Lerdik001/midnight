# Скрипт для компиляции и запуска Java программ

Write-Host "Компиляция Java программ..." -ForegroundColor Green

# Компиляция TodoApp
Write-Host "Компилируем TodoApp.java..." -ForegroundColor Yellow
javac TodoApp.java
if ($LASTEXITCODE -ne 0) {
    Write-Host "Ошибка компиляции TodoApp.java" -ForegroundColor Red
    Read-Host "Нажмите Enter для выхода"
    exit 1
}

# Компиляция Calculator
Write-Host "Компилируем Calculator.java..." -ForegroundColor Yellow
javac Calculator.java
if ($LASTEXITCODE -ne 0) {
    Write-Host "Ошибка компиляции Calculator.java" -ForegroundColor Red
    Read-Host "Нажмите Enter для выхода"
    exit 1
}

Write-Host "Компиляция завершена успешно!" -ForegroundColor Green
Write-Host ""

do {
    Write-Host "Выберите программу для запуска:" -ForegroundColor Cyan
    Write-Host "1. Todo App (Менеджер задач)"
    Write-Host "2. Calculator (Калькулятор)"
    Write-Host "3. Выход"
    Write-Host ""
    
    $choice = Read-Host "Введите номер (1-3)"
    
    switch ($choice) {
        "1" {
            Write-Host "Запуск Todo App..." -ForegroundColor Green
            java TodoApp
        }
        "2" {
            Write-Host "Запуск Calculator..." -ForegroundColor Green
            java Calculator
        }
        "3" {
            Write-Host "Выход..." -ForegroundColor Yellow
            exit 0
        }
        default {
            Write-Host "Неверный выбор. Попробуйте снова." -ForegroundColor Red
        }
    }
    
    if ($choice -ne "3") {
        Write-Host ""
        Write-Host "Программа завершена. Возврат в меню..." -ForegroundColor Yellow
        Write-Host ""
    }
    
} while ($choice -ne "3")