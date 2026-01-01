# Автоматическая синхронизация с Git репозиторием
# Этот скрипт отслеживает изменения в папке и автоматически коммитит их

param(
    [int]$IntervalSeconds = 30,  # Интервал проверки в секундах
    [string]$CommitMessage = "Auto-sync: $(Get-Date -Format 'yyyy-MM-dd HH:mm:ss')"
)

Write-Host "Запуск автоматической синхронизации..." -ForegroundColor Green
Write-Host "Интервал проверки: $IntervalSeconds секунд" -ForegroundColor Yellow
Write-Host "Нажмите Ctrl+C для остановки" -ForegroundColor Yellow

# Функция для проверки и коммита изменений
function Sync-Repository {
    try {
        # Проверяем статус репозитория
        $status = git status --porcelain
        
        if ($status) {
            Write-Host "Обнаружены изменения:" -ForegroundColor Cyan
            Write-Host $status
            
            # Добавляем все изменения
            git add .
            
            # Коммитим с временной меткой
            $timestamp = Get-Date -Format 'yyyy-MM-dd HH:mm:ss'
            git commit -m "Auto-sync: $timestamp"
            
            # Пушим в удаленный репозиторий
            git push origin main
            
            Write-Host "Изменения синхронизированы!" -ForegroundColor Green
        } else {
            Write-Host "Изменений не обнаружено" -ForegroundColor Gray
        }
    }
    catch {
        Write-Host "Ошибка синхронизации: $($_.Exception.Message)" -ForegroundColor Red
    }
}

# Основной цикл мониторинга
try {
    while ($true) {
        Sync-Repository
        Start-Sleep -Seconds $IntervalSeconds
    }
}
catch {
    Write-Host "Синхронизация остановлена" -ForegroundColor Yellow
}