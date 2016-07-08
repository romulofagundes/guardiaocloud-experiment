$(function () {
    $.getJSON( "js/result_exec1.json", function( data ) {
      $('#container1').highcharts(defaults.sucesso(data,"1"));
      $('#container1-erro').highcharts(defaults.falha(data,"1"));
      $('#container1-count').highcharts(defaults.quantidade(data,"1"));
      var high = defaults.sucesso(data,"1 - Valor Alto");
      high.yAxis.max=30000;
      $('#container1-high').highcharts(high);
    });
});
