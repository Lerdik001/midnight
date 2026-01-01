# Простая синхронизация с Git
# Добавляет, коммитит и пушит все изменения

param(
    [string]$Message = "Update: $(Get-Date -Format 'yyyy-MM-dd HH:mm:ss')"
)

Write-Host "Синхронизация репозитория..." -ForegroundColor Green

try {
    # Проверяем статус
    $status = git status --porcelain
    
    if ($status) {
        Write-Host "Найдены изменения:" -ForegroundColor Cyan
        git status --short
        
        # Добавляем все файлы
        git add .
        Write-Host "Файлы добавлены в индекс" -ForegroundColor Yellow
        
        # Коммитим
        git commit -m $Message
        Write-Host "Создан коммит: $Message" -ForegroundColor Yellow
        
        # Пушим
        git push origin main
        Write-Host "Изменения отправлены в репозиторий!" -ForegroundColor Green
    } else {
        Write-Host "Изменений для синхронизации не найдено" -ForegroundColor Gray
    }
}
catch {
    Write-Host "Ошибка: $($_.Exception.Message)" -ForegroundColor Red
}