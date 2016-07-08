var defaults = {
  sucesso: function(data,exec){
    return {
        chart: {
            type: 'line'
        },
        title: {
            text: 'Resultado do Experimento #'+exec
        },
        xAxis: {
            categories: data.data.round,
            title: {
                text: 'Tempo de execução do experimento (s)'
            },
            tickInterval: 1
        },
        yAxis: {
            title: {
                text: 'Tempo de Resposta (ms)'
            }
        },
        series: [{
            name: 'Min',
            data: data.data.min
        }, {
            name: 'Max',
            data: data.data.max
        },{
            name: 'Avg',
            data: data.data.avg
        }]
    }
  },
  falha: function(data,exec){
    return {
        chart: {
            type: 'line'
        },
        title: {
            text: 'Resultado de Erro do Experimento #'+exec+' - Erros'
        },
        xAxis: {
            categories: data.data.round
        },
        yAxis: {
            title: {
                text: 'Quantidade de Erro x Exec.'
            }
        },
        series: [{
            name: 'Erros',
            data: data.data.erros
        }]
    }
  },
  join: function(data1,data2,exec){
    return {
        chart: {
            type: 'line'
        },
        title: {
            text: 'Resultado dos Experimentos '+exec
        },
        xAxis: {
            categories: $.merge(data1.data.round,data2.data.round),
            title: {
                text: 'Tempo de execução do experimento (s)'
            }
        },
        yAxis: {
            title: {
                text: 'Tempo de Resposta (ms)'
            }
        },
        series: [{
            name: 'Avg - Exp1',
            data: data1.data.avg
        },{
            name: 'Avg - Exp2',
            data: data2.data.avg
        }]
    }
  },
  quantidade: function(data,exec){
    return {
        chart: {
            type: 'line'
        },
        title: {
            text: 'Quantidade de Execuções do Experimento #'+exec
        },
        xAxis: {
            categories: data.data.round
        },
        yAxis: {
            title: {
                text: "Quantidade de 'coisas' x Exec."
            }
        },
        series: [{
            name: 'Quantidade',
            data: data.data.threads
        }]
    }
  }
}
