# Acessando as ferramentas de monitoramento

## Iniciando os serviços
- Execute: `docker-compose up -d`

## Prometheus
- Acesse: http://localhost:9090
- Métricas disponíveis: [Ver detalhes das métricas](prometheus.yml)

## Grafana
- Acesse: http://localhost:3000
- Credenciais: admin/admin
- Adicione o datasource: http://prometheus:9090
- Importe o dashboard: /monitoring/grafana-dashboard.json

## Actuator
- Acesse: http://localhost:8080/actuator
